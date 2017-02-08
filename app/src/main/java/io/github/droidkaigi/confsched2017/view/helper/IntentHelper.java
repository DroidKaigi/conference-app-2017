package io.github.droidkaigi.confsched2017.view.helper;

import com.annimon.stream.Optional;

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
    public static Optional<Intent> buildActionViewIntent(@NonNull Context context, @NonNull String htmlUrl) {
        if (TextUtils.isEmpty(htmlUrl)) {
            Timber.i("buildActionViewIntent: url is null");
            return Optional.empty();
        }

        Timber.i("buildActionViewIntent: url: %s", htmlUrl);

        if (!URLUtil.isNetworkUrl(htmlUrl)) {
            return Optional.empty();
        }

        Uri uri = Uri.parse(htmlUrl);
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);

        return intent.resolveActivity(context.getPackageManager()) != null ? Optional.of(intent) : Optional.empty();
    }
}
