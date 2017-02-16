package io.github.droidkaigi.confsched2017.viewmodel;

import android.databinding.BaseObservable;
import android.support.annotation.NonNull;
import android.view.View;

import io.github.droidkaigi.confsched2017.model.Contributor;
import io.github.droidkaigi.confsched2017.view.helper.WebNavigator;

public class ContributorViewModel extends BaseObservable implements ViewModel {

    private final WebNavigator webNavigator;

    private Contributor contributor;

    private String name;

    private String avatarUrl;

    private String htmlUrl;

    private int contributions;

    public ContributorViewModel(WebNavigator webNavigator, @NonNull Contributor contributor) {
        this.webNavigator = webNavigator;
        this.contributor = contributor;
        this.avatarUrl = contributor.avatarUrl;
        this.name = contributor.name;
        this.htmlUrl = contributor.htmlUrl;
        this.contributions = contributor.contributions;
    }

    @Override
    public void destroy() {
        // Nothing to do
    }

    public void onClickContributor(@SuppressWarnings("UnusedParameters") View view) {
        webNavigator.navigateTo(htmlUrl);
    }

    public int getContributions() {
        return contributions;
    }

    public Contributor getContributor() {
        return contributor;
    }

    public String getName() {
        return name;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public String getHtmlUrl() {
        return htmlUrl;
    }
}
