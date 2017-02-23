package io.github.droidkaigi.confsched2017.view.helper;

import android.content.Context;
import android.support.annotation.StringRes;

import java.io.IOException;
import java.io.InputStream;

import javax.inject.Inject;
import javax.inject.Singleton;

import okio.BufferedSource;
import okio.Okio;
import timber.log.Timber;

@Singleton
public class ResourceResolver {

    private static final String TAG = ResourceResolver.class.getSimpleName();

    private final Context context;

    @Inject
    public ResourceResolver(Context context) {
        this.context = context;
    }

    public String getString(@StringRes int resId) {
        return context.getString(resId);
    }

    public String getString(@StringRes int resId, Object... formatArgs) {
        return context.getString(resId, formatArgs);
    }

    public String loadJSONFromAsset(final String jsonFileName) {
        String json = null;
        BufferedSource bufferedSource = null;
        try {
            InputStream is = context.getAssets().open("json/" + jsonFileName);
            bufferedSource = Okio.buffer(Okio.source(is));
            json = bufferedSource.readUtf8();
        } catch (IOException e) {
            Timber.tag(TAG).e(e, "assets/json/%s: read failed", jsonFileName);
        } finally {
            try {
                if (bufferedSource != null) {
                    bufferedSource.close();
                }
            } catch (IOException e) {
                // ignore
            }
        }
        return json;
    }
}
