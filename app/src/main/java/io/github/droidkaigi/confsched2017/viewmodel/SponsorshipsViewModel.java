package io.github.droidkaigi.confsched2017.viewmodel;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import com.annimon.stream.Collectors;
import com.annimon.stream.Stream;

import android.databinding.BaseObservable;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableList;
import android.support.annotation.Nullable;

import java.lang.reflect.Type;
import java.util.List;

import javax.inject.Inject;

import io.github.droidkaigi.confsched2017.R;
import io.github.droidkaigi.confsched2017.model.Sponsorship;
import io.github.droidkaigi.confsched2017.view.helper.ResourceResolver;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import timber.log.Timber;

public final class SponsorshipsViewModel extends BaseObservable implements ViewModel {

    private static final String TAG = SponsorshipsViewModel.class.getSimpleName();

    private ResourceResolver resourceResolver;

    private final ObservableList<SponsorshipViewModel> sponsorshipViewModels;

    private final CompositeDisposable compositeDisposable;

    @Inject
    SponsorshipsViewModel(ResourceResolver resourceResolver, CompositeDisposable compositeDisposable) {
        this.resourceResolver = resourceResolver;
        this.compositeDisposable = compositeDisposable;
        this.sponsorshipViewModels = new ObservableArrayList<>();
    }

    public void start() {
        Disposable disposable = loadSponsors()
                .map(this::convertToViewModel)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        this::renderSponsorships,
                        throwable -> Timber.tag(TAG).e(throwable, "Failed to show sponsors.")
                );
        compositeDisposable.add(disposable);
    }

    @Override
    public void destroy() {
        compositeDisposable.clear();
    }

    public ObservableList<SponsorshipViewModel> getSponsorShipViewModels() {
        return sponsorshipViewModels;
    }

    private Single<List<Sponsorship>> loadSponsors() {
        return Single.create(emitter -> {
            final String json = resourceResolver.loadJSONFromAsset(resourceResolver.getString(R.string.sponsors_file));
            emitter.onSuccess(transformSponsorships(json));
        });
    }

    /**
     * Transforms from a valid json string to a List of {@link Sponsorship}.
     *
     * @param json A json representing a list of sponsors.
     * @return List of {@link Sponsorship}.
     */
    @Nullable
    private List<Sponsorship> transformSponsorships(String json) {
        final Gson gson = new Gson();
        final Type listType = new TypeToken<List<Sponsorship>>() {
        }.getType();
        return gson.fromJson(json, listType);
    }

    private List<SponsorshipViewModel> convertToViewModel(List<Sponsorship> sponsorships) {
        return Stream.of(sponsorships).map(SponsorshipViewModel::new).collect(Collectors.toList());
    }

    private void renderSponsorships(List<SponsorshipViewModel> sponsorships) {
        sponsorshipViewModels.clear();
        sponsorshipViewModels.addAll(sponsorships);
    }
}
