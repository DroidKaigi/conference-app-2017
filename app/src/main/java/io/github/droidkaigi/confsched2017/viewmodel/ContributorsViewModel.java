package io.github.droidkaigi.confsched2017.viewmodel;

import com.annimon.stream.Collectors;
import com.annimon.stream.Stream;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import java.util.List;

import javax.inject.Inject;

import io.github.droidkaigi.confsched2017.BR;
import io.github.droidkaigi.confsched2017.model.Contributor;
import io.github.droidkaigi.confsched2017.repository.contributors.ContributorsRepository;
import io.reactivex.Single;

public final class ContributorsViewModel extends BaseObservable implements ViewModel, ContributorViewModel.Callback {

    private final ContributorsRepository contributorsRepository;

    private int loadingVisibility;

    private boolean refreshing;

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

    public Single<List<ContributorViewModel>> getContributors(boolean refresh) {
        if (refresh) {
            contributorsRepository.setDirty(true);
        }
        return contributorsRepository.findAll().map(contributors -> {
            setLoadingVisibility(View.GONE);
            setRefreshing(false);
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

    public interface Callback {

        void onClickContributor(String htmlUrl);

        void onSwipeRefresh();
    }
}
