package io.github.droidkaigi.confsched2017.view.helper;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.webkit.URLUtil;

import timber.log.Timber;

public class IntentHelper {

    /**
     * Builds an intent of type Intent.ACTION_VIEW from the passed htmlUrl.
     * But, When intent.resolveActivity(context.getPackageManager()) == null is true, it return null.
     */
    public static Intent buildActionViewIntent(@NonNull Context context, @NonNull String htmlUrl) {
        if (TextUtils.isEmpty(htmlUrl)) {
            Timber.i("buildActionViewIntent: url is null");
            return null;
        }

        Timber.i("buildActionViewIntent: url: %s", htmlUrl);

        if (!URLUtil.isNetworkUrl(htmlUrl)) {
            return null;
        }

        Uri uri = Uri.parse(htmlUrl);
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);

        if (intent.resolveActivity(context.getPackageManager()) == null) {
            return null;
        }

        return intent;
    }
}
