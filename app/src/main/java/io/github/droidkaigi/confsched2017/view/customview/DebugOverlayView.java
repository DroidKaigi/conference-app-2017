package io.github.droidkaigi.confsched2017.view.customview;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

import io.github.droidkaigi.confsched2017.R;

/**
 * @author KeithYokoma
 */
public class DebugOverlayView extends RelativeLayout {

    public DebugOverlayView(Context context) {
        this(context, null);
    }

    public DebugOverlayView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DebugOverlayView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        inflate(context, R.layout.view_overlay_debug, this);
    }
}
