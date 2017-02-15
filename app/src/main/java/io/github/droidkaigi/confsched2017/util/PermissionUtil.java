package io.github.droidkaigi.confsched2017.util;

import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.content.ContextCompat;

/**
 * @author KeithYokoma
 */
public final class PermissionUtil {
    private PermissionUtil() {
        throw new AssertionError("no instance");
    }

    public static boolean isPermissionGranted(Context context, String permission) {
        try {
            return ContextCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED;
        } catch (Throwable t) {
            return false;
        }
    }
}
