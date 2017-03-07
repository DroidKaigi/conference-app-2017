package io.github.droidkaigi.confsched2017.view.transition;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Rect;
import android.os.Build;
import android.transition.Transition;
import android.transition.TransitionValues;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

/**
 * SharedElementTransition cannot Visibility transition like fade
 * This transition can animate visibility on shared element transition.
 */
@TargetApi(Build.VERSION_CODES.KITKAT)
public class FadeOutTransition extends Transition {
    private static final String PROP_BOUNDS = "mdap:fabTransform:bounds";

    private static final String[] transitionProperties = {
            PROP_BOUNDS
    };

    public FadeOutTransition() {
        super();
    }

    public FadeOutTransition(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public String[] getTransitionProperties() {
        return transitionProperties;
    }

    @Override
    public void captureStartValues(TransitionValues transitionValues) {
        captureValues(transitionValues);
    }

    @Override
    public void captureEndValues(TransitionValues transitionValues) {
        captureValues(transitionValues);
    }


    private void captureValues(TransitionValues transitionValues) {
        final View view = transitionValues.view;
        if (view == null || view.getWidth() <= 0 || view.getHeight() <= 0) return;

        transitionValues.values.put(PROP_BOUNDS, new Rect(view.getLeft(), view.getTop(),
                view.getRight(), view.getBottom()));
    }


    @Override
    public Animator createAnimator(ViewGroup sceneRoot, TransitionValues startValues,
                                   TransitionValues endValues) {
        if (startValues == null || endValues == null) return null;

        final View view = endValues.view;
        view.setAlpha(1f);
        ValueAnimator alphaAnim = ObjectAnimator.ofFloat(view, "alpha", 1f, 0f);
        alphaAnim.setDuration(getDuration());
        alphaAnim.setInterpolator(getInterpolator());
        return alphaAnim;
    }

}
