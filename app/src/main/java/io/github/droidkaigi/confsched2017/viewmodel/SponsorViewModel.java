package io.github.droidkaigi.confsched2017.viewmodel;

import android.databinding.BaseObservable;
import android.support.annotation.NonNull;
import android.view.View;

import javax.inject.Inject;

import io.github.droidkaigi.confsched2017.model.Sponsor;

public final class SponsorViewModel extends BaseObservable implements ViewModel {

    private Callback callback;

    private Sponsor sponsor;

    @Inject
    SponsorViewModel(Sponsor sponsor) {
        this.sponsor = sponsor;
    }

    public void setCallback(@NonNull Callback callback) {
        this.callback = callback;
    }

    @Override
    public void destroy() {
        this.callback = null;
    }

    public void onClickSponsor(@SuppressWarnings("UnusedParameters") View view) {
        if (callback != null) {
            callback.onClickSponsor(sponsor.url);
        }
    }

    public Sponsor getSponsor() {
        return sponsor;
    }

    public interface Callback {

        void onClickSponsor(String url);
    }
}
