package io.github.droidkaigi.confsched2017.view.helper;

import com.squareup.picasso.Picasso;

import org.lucasr.twowayview.widget.SpannableGridLayoutManager;

import android.databinding.BindingAdapter;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.res.ResourcesCompat;
import android.text.TextUtils;
import android.view.View;
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


    //--------------------------------------------------------------
    // Sessions
    //--------------------------------------------------------------
    @BindingAdapter("twowayview_rowSpan")
    public static void setTwowayViewRowSpan(View view, int rowSpan) {
        final SpannableGridLayoutManager.LayoutParams lp = (SpannableGridLayoutManager.LayoutParams) view.getLayoutParams();
        lp.rowSpan = rowSpan;
        view.setLayoutParams(lp);
    }

    @BindingAdapter("twowayview_colSpan")
    public static void setTwowayViewColSpan(View view, int colSpan) {
        final SpannableGridLayoutManager.LayoutParams lp = (SpannableGridLayoutManager.LayoutParams) view.getLayoutParams();
        lp.colSpan = colSpan;
        view.setLayoutParams(lp);
    }

    @BindingAdapter("sessionCellBackground")
    public static void setSessionCellBackground(View view, @DrawableRes int backgroundResId) {
        view.setBackgroundResource(backgroundResId);
    }

    @BindingAdapter("sessionCategoryColor")
    public static void setSessionCategoryColor(View view, @ColorRes int colorResId) {
        if (colorResId > 0) {
            view.setBackgroundColor(ResourcesCompat.getColor(view.getResources(), colorResId, null));
        }
    }

}
