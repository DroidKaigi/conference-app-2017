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
        return versionName;
    }

    public void onClickSponsors(@SuppressWarnings("unused") View view) {
        if (callback != null) {
            callback.showSponsorsPage();
        }
    }

    public void onClickQuestionnaire(@SuppressWarnings("unused") View view) {
        if (callback != null) {
            callback.showQuesionnairePage();
        }
    }

    public void onClickContributors(@SuppressWarnings("unused") View view) {
        if (callback != null) {
            callback.showContributorsPage();
        }
    }

    public void onClickLicence(@SuppressWarnings("unused") View view) {
        if (callback != null) {
            callback.showLicencePage();
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

        void showQuesionnairePage();

        void showContributorsPage();

        void showLicencePage();

        void showDevInfoPage();

        void showTwitter();

        void showFacebook();

        void showGitHubRepository();

        void showDroidKaigiWeb();

        void showYouTube();
    }
}
