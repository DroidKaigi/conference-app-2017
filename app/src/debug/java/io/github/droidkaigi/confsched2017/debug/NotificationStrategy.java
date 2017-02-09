package io.github.droidkaigi.confsched2017.debug;

import com.tomoima.debot.strategy.DebotStrategy;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.widget.Toast;

import javax.inject.Inject;

import io.github.droidkaigi.confsched2017.MainApplication;
import io.github.droidkaigi.confsched2017.pref.DefaultPrefs;

public class NotificationStrategy extends DebotStrategy {

    @Inject
    NotificationStrategy() {

    }

    @Override
    public void startAction(@NonNull Activity activity) {
        DefaultPrefs prefs = DefaultPrefs.get(activity);
        if (prefs.getNotificationTestFlag()) {
            prefs.setNotificationTestFlag(false);
            Toast.makeText(activity, "Notification will be displayed 10 minutes before sessions", Toast.LENGTH_SHORT).show();
        } else {
            prefs.setNotificationTestFlag(true);
            Toast.makeText(activity, "Notification will be displayed after 5 seconds", Toast.LENGTH_SHORT).show();
        }

        MainApplication application = (MainApplication) activity.getApplication();
        application.initDebot();
    }
}
