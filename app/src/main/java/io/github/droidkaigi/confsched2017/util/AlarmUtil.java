package io.github.droidkaigi.confsched2017.util;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import io.github.droidkaigi.confsched2017.R;
import io.github.droidkaigi.confsched2017.model.Session;
import io.github.droidkaigi.confsched2017.pref.DefaultPrefs;
import io.github.droidkaigi.confsched2017.receiver.NotificationReceiver;

public class AlarmUtil {

    private static final long REMIND_DURATION_MINUTES_FOR_START = TimeUnit.MINUTES.toMillis(10);

    public static void registerAlarm(@NonNull Context context, @NonNull Session session) {
        long time = session.stime.getTime() - REMIND_DURATION_MINUTES_FOR_START;

        DefaultPrefs prefs = DefaultPrefs.get(context);
        if (prefs.getNotificationTestFlag()) {
            time = System.currentTimeMillis() + TimeUnit.SECONDS.toMillis(5);
        }

        if (System.currentTimeMillis() < time) {
            AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                alarmManager.setAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, time, createAlarmIntent(context, session));
            } else {
                alarmManager.set(AlarmManager.RTC_WAKEUP, time, createAlarmIntent(context, session));
            }
        }
    }

    public static void unregisterAlarm(@NonNull Context context, @NonNull Session session) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.cancel(createAlarmIntent(context, session));
    }

    private static PendingIntent createAlarmIntent(@NonNull Context context, @NonNull Session session) {
        String title = context.getString(R.string.notification_title, session.title);
        Date displaySTime = LocaleUtil.getDisplayDate(session.stime, context);
        Date displayETime = LocaleUtil.getDisplayDate(session.etime, context);
        String room = "";
        if (session.room != null) {
            room = session.room.name;
        }
        String text = context.getString(R.string.notification_message,
                DateUtil.getHourMinute(displaySTime),
                DateUtil.getHourMinute(displayETime),
                room);
        Intent intent = NotificationReceiver.createIntent(context, session.id, title, text);
        return PendingIntent.getBroadcast(context, session.id,
                intent, PendingIntent.FLAG_UPDATE_CURRENT);
    }
}
