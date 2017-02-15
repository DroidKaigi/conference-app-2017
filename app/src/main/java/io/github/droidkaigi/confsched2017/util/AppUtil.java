package io.github.droidkaigi.confsched2017.util;

import android.app.Activity;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.customtabs.CustomTabsIntent;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.webkit.URLUtil;

import io.github.droidkaigi.confsched2017.R;

/**
 * Created by shihochan on 2017/02/15.
 */

public class AppUtil {

    public static void openCustomTab(Activity activity, @NonNull String url) {
        if (TextUtils.isEmpty(url) || !URLUtil.isNetworkUrl(url)) {
            return;
        }

        CustomTabsIntent intent = new CustomTabsIntent.Builder()
                .setShowTitle(true)
                .setToolbarColor(ContextCompat.getColor(activity, R.color.theme))
                .build();

        intent.launchUrl(activity, Uri.parse(url));
    }
}
