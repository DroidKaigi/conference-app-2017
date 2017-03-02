package io.github.droidkaigi.confsched2017.view.helper;

import android.app.Activity;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.customtabs.CustomTabsIntent;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.webkit.URLUtil;

import javax.inject.Inject;

import io.github.droidkaigi.confsched2017.R;
import io.github.droidkaigi.confsched2017.di.scope.ActivityScope;
import io.github.droidkaigi.confsched2017.model.Session;
import io.github.droidkaigi.confsched2017.view.activity.ContributorsActivity;
import io.github.droidkaigi.confsched2017.view.activity.LicensesActivity;
import io.github.droidkaigi.confsched2017.view.activity.QuestionnaireActivity;
import io.github.droidkaigi.confsched2017.view.activity.SessionDetailActivity;
import io.github.droidkaigi.confsched2017.view.activity.SessionFeedbackActivity;
import io.github.droidkaigi.confsched2017.view.activity.SponsorsActivity;

/**
 * Created by shihochan on 2017/02/15.
 */

@ActivityScope
public class Navigator {

    private final Activity activity;

    @Inject
    public Navigator(AppCompatActivity activity) {
        this.activity = activity;
    }

    public void navigateToSessionDetail(@NonNull Session session) {
        activity.startActivity(SessionDetailActivity.createIntent(activity, session.id));
    }

    public void navigateToFeedbackPage(@NonNull Session session) {
        activity.startActivity(SessionFeedbackActivity.createIntent(activity, session.id));
    }

    public void navigateToSponsorsPage() {
        activity.startActivity(SponsorsActivity.createIntent(activity));
    }

    public void navigateToContributorsPage() {
        activity.startActivity(ContributorsActivity.createIntent(activity));
    }

    public void navigateToLicensePage() {
        activity.startActivity(LicensesActivity.createIntent(activity));
    }

    public void navigateToQuestionnairePage() {
        activity.startActivity(QuestionnaireActivity.createIntent(activity));
    }

    public void navigateToWebPage(@NonNull String url) {
        if (TextUtils.isEmpty(url) || !URLUtil.isNetworkUrl(url)) {
            return;
        }

        CustomTabsIntent intent = new CustomTabsIntent.Builder()
                .setShowTitle(true)
                .setToolbarColor(ContextCompat.getColor(activity, R.color.theme))
                .build();

        intent.launchUrl(activity, Uri.parse(url));
    }

}
