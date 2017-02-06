package io.github.droidkaigi.confsched2017.viewmodel;

import android.databinding.BaseObservable;
import android.support.annotation.NonNull;

import io.github.droidkaigi.confsched2017.model.Contributor;

public class ContributorViewModel extends BaseObservable implements ViewModel {

    private Callback callback;

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

    public void setCallback(Callback callback) {
        this.callback = callback;
    }

    @Override
    public void destroy() {
        this.callback = null;
    }

    public interface Callback {

        void onClickContributor();
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
