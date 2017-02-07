package io.github.droidkaigi.confsched2017.viewmodel;

import com.annimon.stream.Collectors;
import com.annimon.stream.Stream;

import android.databinding.BaseObservable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.List;

import javax.inject.Inject;

import io.github.droidkaigi.confsched2017.repository.contributors.ContributorsRepository;
import io.reactivex.Single;

public final class ContributorsViewModel extends BaseObservable implements ViewModel, ContributorViewModel.Callback {

    private final ContributorsRepository contributorsRepository;

    @Nullable
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
    }

    public Single<List<ContributorViewModel>> getContributors() {
        return contributorsRepository.findAll().map(contributors ->
                Stream.of(contributors).map(contributor -> {
                    ContributorViewModel viewModel = new ContributorViewModel(contributor);
                    viewModel.setCallback(this);
                    return viewModel;
                }).collect(Collectors.toList()));
    }

    @Override
    public void onClickContributor(String htmlUrl) {
        if (callback != null) {
            callback.onClickContributor(htmlUrl);
        }
    }

    public interface Callback {

        void onClickContributor(String htmlUrl);
    }
}
