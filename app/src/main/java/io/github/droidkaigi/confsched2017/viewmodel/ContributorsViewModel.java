package io.github.droidkaigi.confsched2017.viewmodel;

import android.databinding.BaseObservable;
import android.support.annotation.NonNull;

import java.util.List;

import javax.inject.Inject;

import io.github.droidkaigi.confsched2017.model.Contributor;
import io.github.droidkaigi.confsched2017.repository.sessions.ContributorsRepository;
import io.reactivex.Single;

public final class ContributorsViewModel extends BaseObservable implements ViewModel {

    private final ContributorsRepository contributorsRepository;

    private Callback callback;

    @Inject
    ContributorsViewModel(ContributorsRepository contributorsRepository) {
        this.contributorsRepository = contributorsRepository;
    }

    public void setCallback(@NonNull Callback callback) {
        this.callback = callback;
    }

    @Override
    public void destroy() {
        this.callback = null;
    }

    public interface Callback {

        void onClickContributor(Contributor contributor);
    }

    public Single<List<Contributor>> getContributors() {
        return contributorsRepository.findAll();
    }
}
