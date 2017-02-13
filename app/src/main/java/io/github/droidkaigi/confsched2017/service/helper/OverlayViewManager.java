package io.github.droidkaigi.confsched2017.service.helper;

import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.PixelFormat;
import android.support.v4.view.GravityCompat;
import android.view.ContextThemeWrapper;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;

import javax.inject.Inject;

import io.github.droidkaigi.confsched2017.R;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

/**
 * @author KeithYokoma
 */
public class OverlayViewManager {
    private final Context context;
    private final WindowManager windowManager;
    private final WindowManager.LayoutParams params;
    private Context themedContext;
    private View root;

    @Inject
    public OverlayViewManager(Context context, WindowManager windowManager) {
        this.context = context;
        this.windowManager = windowManager;
        this.params = new WindowManager.LayoutParams(
                WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT, 0, 0,
                WindowManager.LayoutParams.TYPE_SYSTEM_ALERT,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                PixelFormat.TRANSLUCENT);
        params.gravity = GravityCompat.START | Gravity.TOP;
    }

    public void create() {
        themedContext = new OverlayViewContext(context);
        LayoutInflater inflater = (LayoutInflater) themedContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        root = inflater.inflate(R.layout.view_root_overlay, null, false);
        windowManager.addView(root, params);
    }

    public void changeConfiguration() {
        if (root == null)
            return;
        windowManager.updateViewLayout(root, params);
    }

    public void destroy() {
        if (root == null)
            return;
        windowManager.removeViewImmediate(root);
        themedContext = null;
    }

    /* package */ static class OverlayViewContext extends ContextWrapper {
        private LayoutInflater inflater;

        public OverlayViewContext(Context base) {
            super(base);
        }

        @Override
        protected void attachBaseContext(Context base) {
            super.attachBaseContext(CalligraphyContextWrapper.wrap(base));
        }

        @Override
        public Object getSystemService(String name) {
            if (LAYOUT_INFLATER_SERVICE.equals(name)) {
                if (inflater == null) {
                    inflater = LayoutInflater.from(getBaseContext())
                            .cloneInContext(new ContextThemeWrapper(this, R.style.AppTheme));
                }
                return inflater;
            }
            return super.getSystemService(name);
        }
    }
}
