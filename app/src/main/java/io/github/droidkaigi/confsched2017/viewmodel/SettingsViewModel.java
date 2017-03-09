package io.github.droidkaigi.confsched2017.viewmodel;

import android.content.Context;
import android.databinding.BaseObservable;
import android.os.Build;
import android.support.annotation.NonNull;
import android.view.View;

import javax.inject.Inject;

import io.github.droidkaigi.confsched2017.pref.DefaultPrefs;
import io.github.droidkaigi.confsched2017.util.LocaleUtil;

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

    public boolean useDebugOverlayView() {
        return defaultPrefs.getShowDebugOverlayView();
    }

    public void onClickLanguage(@SuppressWarnings("UnusedParameters") View view) {
        if (callback != null) {
            callback.showLanguagesDialog();
        }
    }

    public String getCurrentLanguage(Context context) {
        return LocaleUtil.getCurrentLanguage(context);
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

    public void onCheckedDebugOverlayView(boolean isChecked) {
        defaultPrefs.putShowDebugOverlayView(isChecked);
        if (callback == null)
            return;
        callback.debugOverlayViewEnabled(isChecked);
    }

    @Override
    public void destroy() {
        this.callback = null;
    }

    public interface Callback {

        void changeHeadsUpEnabled(boolean enabled);

        void showLanguagesDialog();

        void debugOverlayViewEnabled(boolean enabled);
    }
}
