package io.github.droidkaigi.confsched2017.service;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.Nullable;

import javax.inject.Inject;

import io.github.droidkaigi.confsched2017.service.helper.OverlayViewManager;
import io.github.droidkaigi.confsched2017.util.SettingsUtil;
import io.github.droidkaigi.confsched2017.view.activity.MainActivity;
import timber.log.Timber;

/**
 * Created by KeishinYokomaku on 2017/02/12.
 */

public class DebugOverlayService extends BaseService {
    private final BroadcastReceiver configurationChangeReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            handler.post(() -> {
                Timber.d("onConfigurationChange");
                manager.changeConfiguration();
            });
        }
    };
    @Inject
    Handler handler;
    @Inject
    OverlayViewManager manager;

    @Override
    public void onCreate() {
        super.onCreate();
        if (!SettingsUtil.canDrawOverlays(this)) {
            return;
        }
        getComponent().inject(this);
        Timber.d("onCreate");

        IntentFilter filter = new IntentFilter(Intent.ACTION_CONFIGURATION_CHANGED);
        registerReceiver(configurationChangeReceiver, filter);
        manager.create();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, @ServiceFlags int flags, int startId) {
        Timber.d("onStartCommand");
        return START_NOT_STICKY; // no need to restart a process
    }

    @Override
    public void onTaskRemoved(Intent rootIntent) {
        super.onTaskRemoved(rootIntent);
        Timber.d("onTaskRemoved");
        ComponentName component = rootIntent != null ? rootIntent.getComponent() : null;
        if (component == null)
            return;
        if (!MainActivity.class.getName().equals(component.getClassName()))
            return;
        stopSelf();
    }

    @Override
    public void onDestroy() {
        if (!SettingsUtil.canDrawOverlays(this)) {
            super.onDestroy();
            return;
        }
        Timber.d("onDestroy");
        manager.destroy();
        unregisterReceiver(configurationChangeReceiver);
        super.onDestroy();
    }
}
