package io.github.droidkaigi.confsched2017.view.customview;

import android.animation.Animator;
import android.content.Context;
import android.databinding.BindingMethod;
import android.databinding.BindingMethods;
import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.widget.NestedScrollView;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;

import io.github.droidkaigi.confsched2017.R;


@BindingMethods({
        @BindingMethod(type = OverScrollLayout.class, attribute = "onOverScroll", method = "setOverScrollListener")
})
public class OverScrollLayout extends CoordinatorLayout {

    private static final float OVER_SCROLL_THRESHOLD_RATIO = 0.20f;

    private static final int RESTORE_ANIM_DURATION = 100;

    private static final float MINIMUM_OVER_SCROLL_SCALE = 0.99f;

    private int overScrollThreshold;

    private Rect originalRect = new Rect();

    private OnOverScrollListener overScrollListener;

    public OverScrollLayout(Context context) {
        super(context);
    }

    public OverScrollLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public OverScrollLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        originalRect.set(left, top, right, bottom);
        overScrollThreshold = (int) (originalRect.height() * OVER_SCROLL_THRESHOLD_RATIO);
    }

    @Override
    public void onNestedPreScroll(View target, int dx, int dy, int[] consumed) {
        if (getY() != originalRect.top) {
            float scale =
                    Math.max(MINIMUM_OVER_SCROLL_SCALE,
                            1 - (Math.abs(getY()) / overScrollThreshold) * (1 - MINIMUM_OVER_SCROLL_SCALE));
            setScaleX(scale);
            setScaleY(scale);
            translationY(-dy);
            consumed[1] = dy;
        } else {
            super.onNestedPreScroll(target, dx, dy, consumed);
        }
    }

    @Override
    public void onNestedScroll(View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed) {
        super.onNestedScroll(target, dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed);
        AppBarLayout appBarLayout = null;
        boolean scrollTop = false;
        boolean scrollEnd = false;
        for (int i = 0; i < getChildCount(); i++) {
            View view = getChildAt(i);
            if (view instanceof AppBarLayout) {
                appBarLayout = (AppBarLayout) view;
                continue;
            }
            if (view instanceof NestedScrollView) {
                scrollTop = !view.canScrollVertically(-1);
                scrollEnd = !view.canScrollVertically(1);
            }
        }

        if (appBarLayout == null
                || (scrollTop && dyUnconsumed < 0 && isAppBarExpanded(appBarLayout))
                || (scrollEnd && dyUnconsumed > 0 && isAppBarCollapsed(appBarLayout))) {
            translationY(-dyUnconsumed);
        }
    }

    @Override
    public boolean onNestedPreFling(View target, float velocityX, float velocityY) {
        return getY() != originalRect.top || super.onNestedPreFling(target, velocityX, velocityY);
    }

    @Override
    public void onStopNestedScroll(View target) {
        super.onStopNestedScroll(target);
        if (Math.abs(getY()) > overScrollThreshold) {
            float yTranslation;
            yTranslation = originalRect.top + getY() > 0 ? originalRect.height() : - originalRect.height();
            animate()
                    .setDuration(getContext().getResources().getInteger(R.integer.activity_transition_mills))
                    .setInterpolator(new AccelerateDecelerateInterpolator())
                    .alpha(0)
                    .translationY(yTranslation)
                    .setListener(new Animator.AnimatorListener() {
                        @Override
                        public void onAnimationStart(Animator animation) {
                        }

                        @Override
                        public void onAnimationEnd(Animator animation) {
                            if (overScrollListener != null) {
                                overScrollListener.onOverScroll();
                            }
                        }

                        @Override
                        public void onAnimationCancel(Animator animation) {
                        }

                        @Override
                        public void onAnimationRepeat(Animator animation) {
                        }
                    });
            return;
        }
        if (getY() != originalRect.top) {
            animate()
                    .setDuration(RESTORE_ANIM_DURATION)
                    .setInterpolator(new AccelerateDecelerateInterpolator())
                    .translationY(originalRect.top)
                    .scaleX(1)
                    .scaleY(1);
        }
    }

    private void translationY(float dy) {
        setY(getY() + dy * 0.5f);
    }

    private boolean isAppBarExpanded(@NonNull AppBarLayout appBarLayout) {
        return appBarLayout.getTop() == 0;
    }

    private boolean isAppBarCollapsed(@NonNull AppBarLayout appBarLayout) {
        return appBarLayout.getY() == -appBarLayout.getTotalScrollRange();
    }

    public void setOverScrollListener(@Nullable OnOverScrollListener listener) {
        this.overScrollListener = listener;
    }

    public interface OnOverScrollListener {

        void onOverScroll();
    }
}
