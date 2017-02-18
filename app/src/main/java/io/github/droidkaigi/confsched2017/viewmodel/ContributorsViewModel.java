package io.github.droidkaigi.confsched2017.viewmodel;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableList;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.view.View;

import com.annimon.stream.Stream;

import java.util.List;

import javax.inject.Inject;

import io.github.droidkaigi.confsched2017.BR;
import io.github.droidkaigi.confsched2017.R;
import io.github.droidkaigi.confsched2017.di.scope.FragmentScope;
import io.github.droidkaigi.confsched2017.repository.contributors.ContributorsRepository;
import io.github.droidkaigi.confsched2017.view.helper.ResourceResolver;
import io.github.droidkaigi.confsched2017.view.helper.Navigator;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

@FragmentScope
public final class ContributorsViewModel extends BaseObservable implements ViewModel {

    public static final String TAG = ContributorsViewModel.class.getSimpleName();

    private final ResourceResolver resourceResolver;

    private final Navigator navigator;

    private final ToolbarViewModel toolbarViewModel;

    private final ContributorsRepository contributorsRepository;

    private final CompositeDisposable compositeDisposable;

    private ObservableList<ContributorViewModel> viewModels;

    private int loadingVisibility;

    private boolean refreshing;

    @Nullable
    private Callback callback;

    @Inject
    ContributorsViewModel(
            ResourceResolver resourceResolver,
            Navigator navigator,
            ToolbarViewModel toolbarViewModel,
            ContributorsRepository contributorsRepository,
            CompositeDisposable compositeDisposable) {
        this.resourceResolver = resourceResolver;
        this.navigator = navigator;
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

    public void retry() {
        loadContributors(false);
    }

    public ObservableList<ContributorViewModel> getContributorViewModels() {
        return this.viewModels;
    }

    public void onClickRepositoryMenu(){
        navigator.navigateToWebPage("https://github.com/DroidKaigi/conference-app-2017");
    }

    private void loadContributors(boolean refresh) {
        if (refresh) {
            contributorsRepository.setDirty(true);
        } else {
            setLoadingVisibility(View.VISIBLE);
        }

        Disposable disposable = contributorsRepository.findAll()
                .map(contributors -> Stream.of(contributors)
                        .map(contributor -> new ContributorViewModel(navigator, contributor))
                        .toList())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        this::renderContributors,
                        throwable -> {
                            setLoadingVisibility(View.GONE);
                            if (callback != null) {
                                callback.showError(R.string.contributors_load_failed);
                            }
                            Timber.tag(TAG).e(throwable, "Failed to show contributors.");
                        });
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

        void showError(@StringRes int textRes);
    }
}
