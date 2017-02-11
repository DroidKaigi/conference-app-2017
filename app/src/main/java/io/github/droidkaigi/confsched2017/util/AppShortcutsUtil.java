package io.github.droidkaigi.confsched2017.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ShortcutInfo;
import android.content.pm.ShortcutManager;
import android.graphics.drawable.Icon;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;

import java.util.Collections;

import io.github.droidkaigi.confsched2017.R;
import io.github.droidkaigi.confsched2017.view.activity.MainActivity;
import io.github.droidkaigi.confsched2017.view.activity.SearchActivity;

public class AppShortcutsUtil {

    private static final String APP_SHORTCUTS_SEARCH_ID = "search";

    @RequiresApi(api = Build.VERSION_CODES.N_MR1)
    public static void addShortcuts(@NonNull Context context) {

        @SuppressLint({"WrongConstant"})
        ShortcutManager shortcutManager = (ShortcutManager) context.getSystemService(Context.SHORTCUT_SERVICE);

        if (shortcutManager != null) {
            Intent[] intents = {
                    new Intent(context.getApplicationContext(), MainActivity.class).setAction(Intent.ACTION_DEFAULT),
                    new Intent(context.getApplicationContext(), SearchActivity.class).setAction(Intent.ACTION_DEFAULT)
            };

            ShortcutInfo shortcutInfo = new ShortcutInfo.Builder(context, APP_SHORTCUTS_SEARCH_ID)
                    .setShortLabel(context.getString(R.string.shortcut_search_title))
                    .setIcon(Icon.createWithResource(context, R.drawable.ic_shortcut_search_24_vector))
                    .setIntents(intents)
                    .build();

            shortcutManager.addDynamicShortcuts(Collections.singletonList(shortcutInfo));
        }
    }
}
