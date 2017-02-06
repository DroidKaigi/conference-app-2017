package io.github.droidkaigi.confsched2017.view.customview;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;

import org.lucasr.twowayview.widget.TwoWayView;

public class TouchlessTwoWayView extends TwoWayView {
    public TouchlessTwoWayView(Context context) {
        this(context, null);
    }

    public TouchlessTwoWayView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TouchlessTwoWayView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return false;
    }

    public boolean forceToDispatchTouchEvent(MotionEvent ev) {
        return super.dispatchTouchEvent(ev);
    }
}
