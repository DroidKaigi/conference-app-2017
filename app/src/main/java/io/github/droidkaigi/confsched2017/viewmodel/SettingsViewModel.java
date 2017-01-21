package io.github.droidkaigi.confsched2017.viewmodel;

import android.databinding.BaseObservable;
import android.os.Build;
import android.support.annotation.NonNull;
import android.view.View;

import javax.inject.Inject;

import io.github.droidkaigi.confsched2017.pref.DefaultPrefs;

public final class SettingsViewModel extends BaseObservable implements ViewModel {

    private Callback callback;

    private final DefaultPrefs defaultPrefs;

    @Inject
    SettingsViewModel(DefaultPrefs defaultPrefs) {
        this.defaultPrefs = defaultPrefs;
    }

    public void setCallback(@NonNull Callback callback) {
        this.callback = callback;
    }

    public boolean shouldNotify() {
        return defaultPrefs.getNotificationFlag();
    }

    public boolean isHeadsUp() {
        return defaultPrefs.getHeadsUpFlag();
    }

    public boolean shouldShowLocalTime() {
        return defaultPrefs.getShowLocalTimeFlag();
    }

    public int getShowHeadsUpSettingVisibility() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            return View.VISIBLE;
        } else {
            return View.GONE;
        }
    }

    public void onCheckedHeadsUpSetting(boolean isChecked) {
        defaultPrefs.putHeadsUpFlag(isChecked);
    }

    public void onCheckedShowLocalTimeSetting(boolean isChecked) {
        defaultPrefs.putShowLocalTimeFlag(isChecked);
    }

    public void onCheckedNotificationSetting(boolean isChecked) {
        defaultPrefs.putNotificationFlag(isChecked);
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
