package io.github.droidkaigi.confsched2017.view.helper;

import android.app.Activity;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.customtabs.CustomTabsIntent;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.webkit.URLUtil;

import javax.inject.Inject;

import io.github.droidkaigi.confsched2017.R;
import io.github.droidkaigi.confsched2017.di.scope.ActivityScope;

/**
 * Created by shihochan on 2017/02/15.
 */

@ActivityScope
public class Navigator {

    private final Activity activity;

    @Inject
    public Navigator(AppCompatActivity activity) {
        this.activity = activity;
    }

    public void navigateToWebPage(@NonNull String url) {
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
