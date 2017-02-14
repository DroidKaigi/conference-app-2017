package io.github.droidkaigi.confsched2017.viewmodel;

import android.databinding.BaseObservable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import io.github.droidkaigi.confsched2017.model.Contributor;
import io.reactivex.functions.Consumer;
import timber.log.Timber;

public class ContributorViewModel extends BaseObservable implements ViewModel {

    @Nullable
    private Consumer<String> contributorClicked;

    private Contributor contributor;

    private String name;

    private String avatarUrl;

    private String htmlUrl;

    private int contributions;

    public ContributorViewModel(@NonNull Contributor contributor) {
        this.contributor = contributor;
        this.avatarUrl = contributor.avatarUrl;
        this.name = contributor.name;
        this.htmlUrl = contributor.htmlUrl;
        this.contributions = contributor.contributions;
    }

    public void setCallback(@Nullable Consumer<String> contributorClicked) {
        this.contributorClicked = contributorClicked;
    }
    @Override
    public void destroy() {
    }

    public void onClickContributor(@SuppressWarnings("UnusedParameters") View view) {
        try {
            contributorClicked.accept(contributor.htmlUrl);
        } catch (Exception e) {
            Timber.e(e.getMessage());
        }
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
