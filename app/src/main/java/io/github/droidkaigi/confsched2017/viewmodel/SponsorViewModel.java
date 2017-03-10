package io.github.droidkaigi.confsched2017.viewmodel;

import android.databinding.BaseObservable;
import android.view.View;

import io.github.droidkaigi.confsched2017.model.Sponsor;
import io.github.droidkaigi.confsched2017.view.helper.Navigator;

public final class SponsorViewModel extends BaseObservable implements ViewModel {

    private final Navigator navigator;

    private Sponsor sponsor;

    SponsorViewModel(Navigator navigator, Sponsor sponsor) {
        this.navigator = navigator;
        this.sponsor = sponsor;
    }

    @Override
    public void destroy() {
        // Nothing to do
    }

    public void onClickSponsor(@SuppressWarnings("UnusedParameters") View view) {
        navigator.navigateToWebPage(sponsor.url);
    }

    public Sponsor getSponsor() {
        return sponsor;
    }
}
