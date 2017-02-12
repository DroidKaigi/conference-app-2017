package io.github.droidkaigi.confsched2017.view.customview;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.RelativeLayout;

import javax.inject.Inject;

import io.github.droidkaigi.confsched2017.R;
import io.github.droidkaigi.confsched2017.databinding.ViewOverlayDebugBinding;
import io.github.droidkaigi.confsched2017.di.OverlayViewModule;
import io.github.droidkaigi.confsched2017.log.LogEmitter;
import io.github.droidkaigi.confsched2017.service.DebugOverlayService;
import io.github.droidkaigi.confsched2017.util.ContextUtil;
import io.reactivex.disposables.CompositeDisposable;

/**
 * This view renders logcat stream.
 * @author KeithYokoma
 */
public class DebugOverlayView extends RelativeLayout {
    @Inject
    CompositeDisposable disposables;
    @Inject
    LogEmitter emitter;

    private ViewOverlayDebugBinding binding;

    public DebugOverlayView(Context context) {
        this(context, null);
    }

    public DebugOverlayView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DebugOverlayView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        binding = ViewOverlayDebugBinding.inflate(
                (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE), this, true);
        // XXX
        ((DebugOverlayService) ContextUtil.getBaseContext(ContextUtil.getBaseContext(context)))
                .getComponent().plus(new OverlayViewModule()).inject(this);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        disposables.add(emitter.listen().subscribe(log -> {
            // prepend a log
            binding.debugMessages.setText(log.getMessage() + "\n" + binding.debugMessages.getText());
        }));
    }

    @Override
    protected void onDetachedFromWindow() {
        disposables.clear();
        super.onDetachedFromWindow();
    }
}
