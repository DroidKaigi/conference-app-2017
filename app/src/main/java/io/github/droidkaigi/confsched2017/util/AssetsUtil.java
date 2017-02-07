package io.github.droidkaigi.confsched2017.util;


import android.content.Context;

import java.io.IOException;
import java.io.InputStream;

import timber.log.Timber;

public final class AssetsUtil {

    private AssetsUtil() {
        // No-op
    }

    /**
     * Loads json string from the assets folder
     *
     * @param context      The calling context
     * @param jsonFileName The file name of the json string
     * @return The JSON string
     */
    public static String loadJSONFromAsset(Context context, final String jsonFileName) {
        String json = null;
        try {
            InputStream is = context.getAssets().open("json/" + jsonFileName);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException e) {
            Timber.e(AssetsUtil.class.getSimpleName(), e.getMessage(), e);
        }
        return json;
    }
}
