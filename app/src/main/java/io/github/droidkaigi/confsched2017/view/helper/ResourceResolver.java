package io.github.droidkaigi.confsched2017.view.helper;

import android.content.Context;
import android.support.annotation.StringRes;

import java.io.IOException;
import java.io.InputStream;

import javax.inject.Inject;
import javax.inject.Singleton;

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
        InputStream is = null;
        try {
            is = context.getAssets().open("json/" + jsonFileName);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            json = new String(buffer, "UTF-8");
        } catch (IOException e) {
            Timber.e(TAG, e.getMessage(), e);
        } finally {
            try {
                if (is != null) {
                    is.close();
                }
            } catch (IOException e) {
                Timber.e(TAG, e.getMessage(), e);
            }
        }
        return json;
    }
}
