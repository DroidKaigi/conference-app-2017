package io.github.droidkaigi.confsched2017.view.helper;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;

import timber.log.Timber;

public class IntentHelper {

    public static Intent buildActionViewIntent(@NonNull Context context, @NonNull String htmlUrl) {
        Timber.i("buildActionViewIntent: url: %s", htmlUrl);

        Uri uri = Uri.parse(htmlUrl);
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);

        if (intent.resolveActivity(context.getPackageManager()) == null) {
            return null;
        }

        return intent;
    }
}
