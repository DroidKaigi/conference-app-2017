package io.github.droidkaigi.confsched2017.util;

import android.content.Context;
import android.content.ContextWrapper;

/**
 * @author KeithYokoma
 */
public final class ContextUtil {

    private ContextUtil() {
        throw new AssertionError("no instance");
    }

    public static Context getBaseContext(Context context) {
        if (context instanceof ContextWrapper)
            return ((ContextWrapper) context).getBaseContext();
        return context;
    }
}
