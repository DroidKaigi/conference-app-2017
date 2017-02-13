package io.github.droidkaigi.confsched2017.util;

import android.Manifest;
import android.content.Context;
import android.os.Build;
import android.provider.Settings;

/**
 * @author KeithYokoma
 */
public final class SettingsUtil {
    private SettingsUtil() {
        throw new AssertionError("no instance");
    }

    public static boolean canDrawOverlays(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
            return Settings.canDrawOverlays(context);
        return PermissionUtil.isPermissionGranted(context, Manifest.permission.SYSTEM_ALERT_WINDOW);
    }
}
