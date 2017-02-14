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
import io.github.droidkaigi.confsched2017.R;
import io.github.droidkaigi.confsched2017.di.scope.FragmentScope;
import io.github.droidkaigi.confsched2017.repository.contributors.ContributorsRepository;
import io.github.droidkaigi.confsched2017.view.helper.ResourceResolver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

@FragmentScope
public final class ContributorsViewModel extends BaseObservable implements ViewModel, ContributorViewModel.Callback {

    public static final String TAG = ContributorsViewModel.class.getSimpleName();

    private final ResourceResolver resourceResolver;

    private final ToolbarViewModel toolbarViewModel;

    private final ContributorsRepository contributorsRepository;

    private final CompositeDisposable compositeDisposable;

    private ObservableList<ContributorViewModel> viewModels;

    private int loadingVisibility;

    private boolean refreshing;

    @Nullable
    private Callback callback;

    @Inject
    ContributorsViewModel(ResourceResolver resourceResolver, ToolbarViewModel toolbarViewModel,
            ContributorsRepository contributorsRepository, CompositeDisposable compositeDisposable) {
        this.resourceResolver = resourceResolver;
        this.toolbarViewModel = toolbarViewModel;
        this.contributorsRepository = contributorsRepository;
        this.compositeDisposable = compositeDisposable;
        this.viewModels = new ObservableArrayList<>();
    }

    public void setCallback(@NonNull Callback callback) {
        this.callback = callback;
    }

    public void start() {
        loadContributors(false);
    }

    @Override
    public void destroy() {
        compositeDisposable.clear();
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
        loadContributors(true);
    }

    public ObservableList<ContributorViewModel> getContributorViewModels() {
        return this.viewModels;
    }

    private void loadContributors(boolean refresh) {
        if (refresh) {
            contributorsRepository.setDirty(true);
        }

        Disposable disposable = contributorsRepository.findAll()
                .map(contributors -> Stream.of(contributors).map(contributor -> {
                    ContributorViewModel viewModel = new ContributorViewModel(contributor);
                    viewModel.setCallback(this);
                    return viewModel;
                }).collect(Collectors.toList()))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        this::renderContributors,
                        throwable -> Timber.tag(TAG).e(throwable, "Failed to show contributors.")
                );
        compositeDisposable.add(disposable);
    }

    private void renderContributors(List<ContributorViewModel> contributorViewModels) {
        viewModels.clear();
        viewModels.addAll(contributorViewModels);

        String title = resourceResolver.getString(R.string.contributors) + " "
                + resourceResolver.getString(R.string.contributors_people, contributorViewModels.size());
        toolbarViewModel.setToolbarTitle(title);

        setLoadingVisibility(View.GONE);
        setRefreshing(false);
    }

    public interface Callback {

        void onClickContributor(String htmlUrl);
    }
}
