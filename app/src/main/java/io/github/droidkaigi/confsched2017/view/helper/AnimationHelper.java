package io.github.droidkaigi.confsched2017.view.helper;

import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.DrawableRes;
import android.support.v4.content.ContextCompat;
import android.widget.ImageView;

public class AnimationHelper {

    public static void startVDAnimation(ImageView imageView,
            @DrawableRes int inactiveResId,
            @DrawableRes int activeResId,
            int duration) {
        int drawableResId = imageView.isSelected() ? activeResId : inactiveResId;
        Drawable drawable = ContextCompat.getDrawable(imageView.getContext(), drawableResId);
        imageView.setImageDrawable(drawable);

        if (drawable instanceof Animatable) {
            Animatable animatable = (Animatable) drawable;
            if (animatable.isRunning()) {
                animatable.stop();
            }

            animatable.start();

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                imageView.setSelected(!imageView.isSelected());
            } else {
                imageView.postDelayed(() -> {
                    imageView.setSelected(!imageView.isSelected());
                    int nextDrawableResId = imageView.isSelected() ? activeResId : inactiveResId;
                    Drawable nextDrawable = ContextCompat.getDrawable(imageView.getContext(), nextDrawableResId);
                    imageView.setImageDrawable(nextDrawable);
                }, duration);
            }
        }
    }

}
