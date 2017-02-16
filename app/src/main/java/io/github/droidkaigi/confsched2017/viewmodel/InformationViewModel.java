package io.github.droidkaigi.confsched2017.viewmodel;

import android.support.annotation.NonNull;
import android.view.View;

import javax.inject.Inject;

import io.github.droidkaigi.confsched2017.BuildConfig;
import io.github.droidkaigi.confsched2017.view.helper.WebNavigator;

public final class InformationViewModel implements ViewModel {

    private final WebNavigator webNavigator;

    private Callback callback;

    private String versionName;

    @Inject
    public InformationViewModel(WebNavigator webNavigator) {
        this.webNavigator = webNavigator;
        this.versionName = "V" + BuildConfig.VERSION_NAME;
    }

    public void setCallback(@NonNull Callback callback) {
        this.callback = callback;
    }

    public String getVersionName() {
        return versionName + " " + BuildConfig.GIT_SHA;
    }

    public void onClickSponsors(@SuppressWarnings("unused") View view) {
        if (callback != null) {
            callback.showSponsorsPage();
        }
    }

    public void onClickQuestionnaire(@SuppressWarnings("unused") View view) {
        if (callback != null) {
            callback.showQuestionnairePage();
        }
    }

    public void onClickContributors(@SuppressWarnings("unused") View view) {
        if (callback != null) {
            callback.showContributorsPage();
        }
    }

    public void onClickHelpTranslate(@SuppressWarnings("unused") View view) {
        webNavigator.navigateTo("https://droidkaigi2017.oneskyapp.com/collaboration");
    }

    public void onClickLicense(@SuppressWarnings("unused") View view) {
        if (callback != null) {
            callback.showLicensePage();
        }
    }

    public void onClickDevInfo(@SuppressWarnings("unused") View view) {
        if (callback != null) {
            callback.showDevInfoPage();
        }
    }

    public void onClickTwitter(@SuppressWarnings("unused") View view) {
        webNavigator.navigateTo("https://twitter.com/DroidKaigi");
    }

    public void onClickFacebook(@SuppressWarnings("unused") View view) {
        webNavigator.navigateTo("https://www.facebook.com/DroidKaigi/");
    }

    public void onClickGitHub(@SuppressWarnings("unused") View view) {
        webNavigator.navigateTo("https://github.com/DroidKaigi/conference-app-2017/");
    }

    public void onClickDroidKaigiWeb(@SuppressWarnings("unused") View view) {
        webNavigator.navigateTo("https://droidkaigi.github.io/2017/");
    }

    public void onClickYouTube(@SuppressWarnings("unused") View view) {
        webNavigator.navigateTo("https://www.youtube.com/droidkaigi");
    }

    @Override
    public void destroy() {
        this.callback = null;
    }

    public interface Callback {

        void showSponsorsPage();

        void showQuestionnairePage();

        void showContributorsPage();

        void showLicensePage();

        void showDevInfoPage();
    }
}
