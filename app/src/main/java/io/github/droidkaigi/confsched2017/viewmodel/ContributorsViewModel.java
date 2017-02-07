package io.github.droidkaigi.confsched2017.viewmodel;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import com.annimon.stream.Collectors;
import com.annimon.stream.Stream;

import java.util.List;

import javax.inject.Inject;

import io.github.droidkaigi.confsched2017.BR;
import io.github.droidkaigi.confsched2017.model.Contributor;
import io.github.droidkaigi.confsched2017.repository.contributors.ContributorsRepository;
import io.reactivex.Single;

public final class ContributorsViewModel extends BaseObservable implements ViewModel, ContributorViewModel.Callback {

    private final ContributorsRepository contributorsRepository;

    @Nullable
    private List<Contributor> contributors;

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
        return contributorsRepository.findAll().map(contributors -> {
            this.contributors = contributors;
            notifyPropertyChanged(BR.loadingVisibility);

            return Stream.of(contributors).map(contributor -> {
                ContributorViewModel viewModel = new ContributorViewModel(contributor);
                viewModel.setCallback(this);
                return viewModel;
            }).collect(Collectors.toList());
        });
    }

    @Override
    public void onClickContributor(String htmlUrl) {
        if (callback != null) {
            callback.onClickContributor(htmlUrl);
        }
    }

    @Bindable
    public int getLoadingVisibility() {
        if (this.contributors == null) {
            return View.VISIBLE;
        } else {
            return View.GONE;
        }
    }

    public interface Callback {

        void onClickContributor(String htmlUrl);
    }
}
