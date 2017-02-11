package io.github.droidkaigi.confsched2017.viewmodel;

import android.support.annotation.NonNull;
import android.view.View;

import javax.inject.Inject;

import io.github.droidkaigi.confsched2017.BuildConfig;

public final class InformationViewModel implements ViewModel {

    private Callback callback;

    private String versionName;

    @Inject
    public InformationViewModel() {
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
        if (callback != null) {
            callback.showTranslationsPage();
        }
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
        if (callback != null) {
            callback.showTwitter();
        }
    }

    public void onClickFacebook(@SuppressWarnings("unused") View view) {
        if (callback != null) {
            callback.showFacebook();
        }
    }

    public void onClickGitHub(@SuppressWarnings("unused") View view) {
        if (callback != null) {
            callback.showGitHubRepository();
        }
    }

    public void onClickDroidKaigiWeb(@SuppressWarnings("unused") View view) {
        if (callback != null) {
            callback.showDroidKaigiWeb();
        }
    }

    public void onClickYouTube(@SuppressWarnings("unused") View view) {
        if (callback != null) {
            callback.showYouTube();
        }
    }

    @Override
    public void destroy() {
        this.callback = null;
    }

    public interface Callback {

        void showSponsorsPage();

        void showQuestionnairePage();

        void showContributorsPage();

        void showTranslationsPage();

        void showLicensePage();

        void showDevInfoPage();

        void showTwitter();

        void showFacebook();

        void showGitHubRepository();

        void showDroidKaigiWeb();

        void showYouTube();
    }
}
