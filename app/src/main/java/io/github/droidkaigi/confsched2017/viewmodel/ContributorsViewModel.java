package io.github.droidkaigi.confsched2017.viewmodel;

import com.annimon.stream.Collectors;
import com.annimon.stream.Stream;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableList;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import java.util.List;

import javax.inject.Inject;

import io.github.droidkaigi.confsched2017.BR;
import io.github.droidkaigi.confsched2017.repository.contributors.ContributorsRepository;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;

public final class ContributorsViewModel extends BaseObservable implements ViewModel, ContributorViewModel.Callback {

    private final ContributorsRepository contributorsRepository;

    private ObservableList<ContributorViewModel> viewModels;

    private int loadingVisibility;

    private boolean refreshing;

    @Nullable
    private Callback callback;

    @Inject
    ContributorsViewModel(ContributorsRepository contributorsRepository) {
        this.contributorsRepository = contributorsRepository;
        this.viewModels = new ObservableArrayList<>();
    }

    public void setCallback(@NonNull Callback callback) {
        this.callback = callback;
    }

    @Override
    public void destroy() {
    }

    public Single<List<ContributorViewModel>> getContributors(boolean refresh) {
        if (refresh) {
            contributorsRepository.setDirty(true);
        }
        return contributorsRepository.findAll()
                .map(contributors -> Stream.of(contributors).map(contributor -> {
                    ContributorViewModel viewModel = new ContributorViewModel(contributor);
                    viewModel.setCallback(this);
                    return viewModel;
                }).collect(Collectors.toList()))
                .observeOn(AndroidSchedulers.mainThread())
                .map(contributorViewModels -> {
                    this.viewModels.clear();
                    this.viewModels.addAll(contributorViewModels);
                    setLoadingVisibility(View.GONE);
                    setRefreshing(false);
                    return this.viewModels;
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
        return loadingVisibility;
    }

    private void setLoadingVisibility(int visibility) {
        this.loadingVisibility = visibility;
        notifyPropertyChanged(BR.loadingVisibility);
    }

    @Bindable
    public boolean getRefreshing() {
        return refreshing;
    }

    private void setRefreshing(boolean refreshing) {
        this.refreshing = refreshing;
        notifyPropertyChanged(BR.refreshing);
    }

    public void onSwipeRefresh() {
        if (callback != null) {
            callback.onSwipeRefresh();
        }
    }

    public ObservableList<ContributorViewModel> getContributorViewModels() {
        return this.viewModels;
    }

    public interface Callback {

        void onClickContributor(String htmlUrl);

        void onSwipeRefresh();
    }
}
