package io.github.droidkaigi.confsched2017.view.helper;

import com.squareup.picasso.Picasso;

import android.databinding.BindingAdapter;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.widget.CompoundButton;
import android.widget.ImageView;

import io.github.droidkaigi.confsched2017.R;
import io.github.droidkaigi.confsched2017.view.customview.SettingSwitchRowView;

public class DataBindingHelper {

    //--------------------------------------------------------------
    // Common
    //--------------------------------------------------------------
    @BindingAdapter("photoImageUrl")
    public static void setPhotoImageUrl(ImageView imageView, @Nullable String imageUrl) {
        setImageUrl(imageView, imageUrl, R.color.grey200);
    }

    private static void setImageUrl(ImageView imageView, @Nullable String imageUrl,
            @DrawableRes int placeholderResId) {
        if (TextUtils.isEmpty(imageUrl)) {
            imageView.setImageDrawable(
                    ContextCompat.getDrawable(imageView.getContext(), placeholderResId));
        } else {
            Picasso.with(imageView.getContext())
                    .load(imageUrl)
                    .placeholder(placeholderResId)
                    .error(placeholderResId)
                    .into(imageView);
        }
    }


    //--------------------------------------------------------------
    // Settings
    //--------------------------------------------------------------
    @BindingAdapter("settingEnabled")
    public static void setSettingEnabled(SettingSwitchRowView view, boolean enabled) {
        view.setEnabled(enabled);
    }

    @BindingAdapter("settingDefaultValue")
    public static void setSettingDefaultValue(SettingSwitchRowView view, boolean defaultValue) {
        view.setDefault(defaultValue);
    }

    @BindingAdapter("settingOnCheckedChanged")
    public static void setSettingOnCheckedChanged(SettingSwitchRowView view, CompoundButton.OnCheckedChangeListener listener) {
        view.setOnCheckedChangeListener(listener);
    }

}
