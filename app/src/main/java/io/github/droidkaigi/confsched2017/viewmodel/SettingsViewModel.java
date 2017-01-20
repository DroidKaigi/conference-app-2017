package io.github.droidkaigi.confsched2017.viewmodel;

import android.content.Context;
import android.databinding.BaseObservable;
import android.os.Build;
import android.support.annotation.NonNull;
import android.view.View;

import javax.inject.Inject;

import io.github.droidkaigi.confsched2017.pref.DefaultPrefs;

public final class SettingsViewModel extends BaseObservable implements ViewModel {

    private Callback callback;

    @Inject
    SettingsViewModel() {
    }

    public void setCallback(@NonNull Callback callback) {
        this.callback = callback;
    }

    public boolean shouldNotify(Context context) {
        return DefaultPrefs.get(context).getNotificationFlag();
    }

    public boolean isHeadsUp(Context context) {
        return DefaultPrefs.get(context).getHeadsUpFlag();
    }

    public boolean shouldShowLocalTime(Context context) {
        return DefaultPrefs.get(context).getShowLocalTimeFlag();
    }

    public int getShowHeadsUpSettingVisibility() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            return View.VISIBLE;
        } else {
            return View.GONE;
        }
    }

    public void onCheckedHeadsUpSetting(Context context, boolean isChecked) {
        DefaultPrefs.get(context).putHeadsUpFlag(isChecked);
    }

    public void onCheckedShowLocalTimeSetting(Context context, boolean isChecked) {
        DefaultPrefs.get(context).putShowLocalTimeFlag(isChecked);
    }

    public void onCheckedNotificationSetting(Context context, boolean isChecked) {
        DefaultPrefs.get(context).putNotificationFlag(isChecked);
        if (callback != null) {
            callback.changeHeadsUpEnabled(isChecked);
        }
    }

    @Override
    public void destroy() {
        this.callback = null;
    }

    public interface Callback {

        void changeHeadsUpEnabled(boolean enabled);
    }
}
