package io.github.droidkaigi.confsched2017.view.helper;

import android.support.annotation.NonNull;

import io.github.droidkaigi.confsched2017.model.Session;

/**
 * Created by shihochan on 2017/02/15.
 */

public interface Navigator {

    void navigateToSessionDetail(@NonNull Session session);

    void navigateToFeedbackPage(@NonNull Session session);

    void navigateToSponsorsPage();

    void navigateToContributorsPage();

    void navigateToLicensePage();

    void navigateToWebPage(@NonNull String url);

}
