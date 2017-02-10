package io.github.droidkaigi.confsched2017.view.helper;

import com.squareup.picasso.Picasso;

import net.opacapp.multilinecollapsingtoolbar.CollapsingToolbarLayout;

import org.lucasr.twowayview.widget.SpannableGridLayoutManager;

import android.content.Context;
import android.content.res.ColorStateList;
import android.databinding.BindingAdapter;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.res.ResourcesCompat;
import android.text.TextUtils;
import android.text.util.Linkify;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import io.github.droidkaigi.confsched2017.R;
import io.github.droidkaigi.confsched2017.model.Topic;
import io.github.droidkaigi.confsched2017.view.customview.InfoRowView;
import io.github.droidkaigi.confsched2017.view.customview.SettingSwitchRowView;
import io.github.droidkaigi.confsched2017.view.customview.transformation.CropCircleTransformation;

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

    @BindingAdapter({"speakerImageUrl", "speakerImageSize"})
    public static void setSpeakerImageUrlWithSize(ImageView imageView, @Nullable String imageUrl, float sizeInDimen) {
        setImageUrlWithSize(imageView, imageUrl, sizeInDimen, R.drawable.ic_speaker_placeholder);
    }

    private static void setImageUrlWithSize(ImageView imageView, @Nullable String imageUrl, float sizeInDimen,
            int placeholderResId) {
        if (TextUtils.isEmpty(imageUrl)) {
            imageView.setImageDrawable(ContextCompat.getDrawable(imageView.getContext(), placeholderResId));
        } else {
            final int size = Math.round(sizeInDimen);
            imageView.setBackground(ContextCompat.getDrawable(imageView.getContext(), R.drawable.circle_border_grey200));
            Picasso.with(imageView.getContext())
                    .load(imageUrl)
                    .resize(size, size)
                    .centerInside()
                    .placeholder(placeholderResId)
                    .error(placeholderResId)
                    .transform(new CropCircleTransformation())
                    .into(imageView);
        }
    }

    @BindingAdapter("textLinkify")
    public static void setTextLinkify(TextView textView, boolean isLinkify) {
        if (isLinkify) {
            Linkify.addLinks(textView, Linkify.ALL);
        }
    }

    @BindingAdapter("webViewUrl")
    public static void loadUrl(WebView webView, String url) {
        if (TextUtils.isEmpty(url)) {
            return;
        }
        webView.loadUrl(url);
    }

    @BindingAdapter("webViewClient")
    public static void setWebViewClient(WebView webView, WebViewClient client) {
        webView.setWebViewClient(client);
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

    @BindingAdapter("sessionTopicColor")
    public static void setSessionTopicColor(View view, @ColorRes int colorResId) {
        if (colorResId > 0) {
            view.setBackgroundColor(ResourcesCompat.getColor(view.getResources(), colorResId, null));
        }
    }

    @BindingAdapter("topicVividColor")
    public static void setTopicVividColor(CollapsingToolbarLayout view, @ColorRes int colorResId) {
        view.setContentScrimColor(ContextCompat.getColor(view.getContext(), colorResId));
    }

    @BindingAdapter("topicVividColor")
    public static void setTopicVividColor(TextView view, @ColorRes int colorResId) {
        view.setTextColor(ContextCompat.getColor(view.getContext(), colorResId));
    }

    @BindingAdapter("topicVividColor")
    public static void setTopicVividColor(FloatingActionButton view, @ColorRes int colorResId) {
        view.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(view.getContext(), colorResId)));
    }

    @BindingAdapter("coverFadeBackground")
    public static void setCoverFadeBackground(View view, @ColorRes int colorResId) {
        view.setBackgroundResource(colorResId);
    }

    @BindingAdapter("sessionStatus")
    public static void setSessionStatus(FloatingActionButton view, boolean isMySession) {
        if (isMySession) {
            view.setImageResource(R.drawable.avd_check_to_add_24dp);
            view.setSelected(true);
        } else {
            view.setImageResource(R.drawable.avd_add_to_check_24dp);
            view.setSelected(false);
        }
    }

    @SuppressWarnings("unused")
    @BindingAdapter("topic")
    public static void setTopic(TextView textView, @Nullable Topic topic) {
        if (topic != null) {
            textView.setBackgroundResource(R.drawable.tag_language);
            textView.setText(topic.name);
        } else {
            textView.setVisibility(View.INVISIBLE);
        }
    }


    //--------------------------------------------------------------
    // Information
    //--------------------------------------------------------------
    @BindingAdapter("infoRowDescription")
    public static void setInfoRowDescription(InfoRowView view, String description) {
        view.setDescription(description);
    }


    //--------------------------------------------------------------
    // SearchResult
    //--------------------------------------------------------------
    @BindingAdapter({"searchResultIcon", "mySession"})
    public static void setSessionIcon(TextView textView, @DrawableRes int iconResId, boolean isMySession) {
        Context context = textView.getContext();
        Drawable icon = ContextCompat.getDrawable(context, iconResId);
        Drawable checkMark = ContextCompat.getDrawable(context, R.drawable.ic_check_circle_24_vector);
        int size = context.getResources().getDimensionPixelSize(R.dimen.text_drawable_12dp);
        checkMark.setBounds(0, 0, size, size);
        icon.setBounds(0, 0, size, size);
        textView.setCompoundDrawables(icon, null, isMySession ? checkMark : null, null);
    }
}
