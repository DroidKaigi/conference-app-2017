package io.github.droidkaigi.confsched2017.receiver;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v4.content.ContextCompat;

import io.github.droidkaigi.confsched2017.R;
import io.github.droidkaigi.confsched2017.pref.DefaultPrefs;
import io.github.droidkaigi.confsched2017.view.activity.SessionDetailActivity;
import timber.log.Timber;

public class NotificationReceiver extends BroadcastReceiver {

    private static final String TAG = NotificationReceiver.class.getSimpleName();

    private static final String KEY_SESSION_ID = "session_id";

    private static final String KEY_TITLE = "title";

    private static final String KEY_TEXT = "text";

    private static final String GROUP_NAME = "droidkaigi";
    private static final int GROUP_NOTIFICATION_ID = 0;

    public static Intent createIntent(Context context, int sessionId, String title, String text) {
        Intent intent = new Intent(context, NotificationReceiver.class);
        intent.putExtra(NotificationReceiver.KEY_SESSION_ID, sessionId);
        intent.putExtra(NotificationReceiver.KEY_TITLE, title);
        intent.putExtra(NotificationReceiver.KEY_TEXT, text);
        return intent;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        DefaultPrefs prefs = DefaultPrefs.get(context);
        if (!prefs.getNotificationFlag()) {
            Timber.tag(TAG).v("Notification is disabled.");
            return;
        }

        showGroupNotification(context);

        showChildNotification(context, intent, prefs.getHeadsUpFlag());
    }

    /**
     * Show group notification
     * @param context Context
     */
    private void showGroupNotification(Context context) {
        // Group notification is supported on Android N and Android Wear.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Notification groupNotification = new NotificationCompat.Builder(context)
                    .setSmallIcon(R.drawable.ic_notification)
                    .setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.mipmap.ic_launcher))
                    .setColor(ContextCompat.getColor(context, R.color.theme))
                    .setGroup(GROUP_NAME)
                    .setGroupSummary(true)
                    .build();
            NotificationManagerCompat.from(context).notify(GROUP_NOTIFICATION_ID, groupNotification);
        }
    }

    /**
     * Show child notification
     * @param context Context
     * @param intent Intent
     */
    private void showChildNotification(Context context, Intent intent, boolean headsUp) {
        int sessionId = intent.getIntExtra(KEY_SESSION_ID, 0);
        String title = intent.getStringExtra(KEY_TITLE);
        String text = intent.getStringExtra(KEY_TEXT);
        int priority = headsUp ? NotificationCompat.PRIORITY_HIGH : NotificationCompat.PRIORITY_DEFAULT;
        Intent openIntent = SessionDetailActivity.createIntent(context, sessionId);
        openIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, openIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context)
                .setTicker(title)
                .setContentTitle(title)
                .setContentText(text)
                .setSmallIcon(R.drawable.ic_notification)
                .setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.mipmap.ic_launcher))
                .setColor(ContextCompat.getColor(context, R.color.theme))
                .setAutoCancel(true)
                .setContentIntent(pendingIntent)
                .setDefaults(NotificationCompat.DEFAULT_ALL)
                .setPriority(priority);
        // Group notification is supported on Android N and Android Wear.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            builder.setGroup(GROUP_NAME).setGroupSummary(false);
        }
        NotificationManagerCompat.from(context).notify(sessionId, builder.build());
    }
}
