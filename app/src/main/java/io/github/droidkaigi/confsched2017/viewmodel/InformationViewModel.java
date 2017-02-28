package io.github.droidkaigi.confsched2017.viewmodel;

import android.view.View;

import javax.inject.Inject;

import io.github.droidkaigi.confsched2017.BuildConfig;
import io.github.droidkaigi.confsched2017.view.helper.Navigator;

public final class InformationViewModel implements ViewModel {

    private final Navigator navigator;

    private String versionName;

    @Inject
    public InformationViewModel(Navigator navigator) {
        this.navigator = navigator;
        this.versionName = "V" + BuildConfig.VERSION_NAME;
    }

    public String getVersionName() {
        return versionName + " " + BuildConfig.GIT_SHA;
    }

    public void onClickSponsors(@SuppressWarnings("unused") View view) {
        navigator.navigateToSponsorsPage();
    }

    public void onClickQuestionnaire(@SuppressWarnings("unused") View view) {
        navigator.navigateToQuestionnairePage();
    }

    public void onClickContributors(@SuppressWarnings("unused") View view) {
        navigator.navigateToContributorsPage();
    }

    public void onClickHelpTranslate(@SuppressWarnings("unused") View view) {
        navigator.navigateToWebPage("https://droidkaigi2017.oneskyapp.com/collaboration");
    }

    public void onClickLicense(@SuppressWarnings("unused") View view) {
        navigator.navigateToLicensePage();
    }

    public void onClickDevInfo(@SuppressWarnings("unused") View view) {
        // TODO
    }

    public void onClickTwitter(@SuppressWarnings("unused") View view) {
        navigator.navigateToWebPage("https://twitter.com/DroidKaigi");
    }

    public void onClickFacebook(@SuppressWarnings("unused") View view) {
        navigator.navigateToWebPage("https://www.facebook.com/DroidKaigi/");
    }

    public void onClickGitHub(@SuppressWarnings("unused") View view) {
        navigator.navigateToWebPage("https://github.com/DroidKaigi/conference-app-2017/");
    }

    public void onClickDroidKaigiWeb(@SuppressWarnings("unused") View view) {
        navigator.navigateToWebPage("https://droidkaigi.github.io/2017/");
    }

    public void onClickYouTube(@SuppressWarnings("unused") View view) {
        navigator.navigateToWebPage("https://www.youtube.com/droidkaigi");
    }

    @Override
    public void destroy() {
        // Nothing to do
    }
}
