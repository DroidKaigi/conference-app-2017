package io.github.droidkaigi.confsched2017.receiver;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import io.github.droidkaigi.confsched2017.R;
import io.github.droidkaigi.confsched2017.pref.DefaultPrefs;
import io.github.droidkaigi.confsched2017.view.activity.MainActivity;

public class NotificationReceiver extends BroadcastReceiver {

    private static final String TAG = NotificationReceiver.class.getSimpleName();

    private static final String KEY_SESSION_ID = "session_id";
    private static final String KEY_TITLE = "title";
    private static final String KEY_TEXT = "text";

    private static final int NOTIFICATION_ID = 1;

    public static Intent createIntent(Context context, int sessionId, String title, String text) {
        Intent intent = new Intent(context, NotificationReceiver.class);
        intent.putExtra(NotificationReceiver.KEY_SESSION_ID, sessionId);
        intent.putExtra(NotificationReceiver.KEY_TITLE, title);
        intent.putExtra(NotificationReceiver.KEY_TEXT, text);
        return intent;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if (!DefaultPrefs.get(context).getNotificationFlag()) {
            Log.v(TAG, "Notification is disabled.");
            return;
        }
        int sessionId = intent.getIntExtra(KEY_SESSION_ID, 0);
        String title = intent.getStringExtra(KEY_TITLE);
        String text = intent.getStringExtra(KEY_TEXT);
        Intent openIntent = new Intent(context, MainActivity.class);
        openIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, openIntent, 0);
        Notification notification = new NotificationCompat.Builder(context)
                .setTicker(title)
                .setContentTitle(title)
                .setContentText(text)
                .setSmallIcon(R.mipmap.ic_launcher) // TODO Please replace this icon
                .setColor(ContextCompat.getColor(context, R.color.theme))
                .setAutoCancel(true)
                .setContentIntent(pendingIntent)
                .build();
        NotificationManagerCompat.from(context).notify(NOTIFICATION_ID, notification);
    }
}
