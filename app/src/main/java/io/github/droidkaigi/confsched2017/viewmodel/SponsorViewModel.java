package io.github.droidkaigi.confsched2017.viewmodel;

import android.databinding.BaseObservable;
import android.view.View;

import io.github.droidkaigi.confsched2017.model.Sponsor;
import io.github.droidkaigi.confsched2017.view.helper.WebNavigator;

public final class SponsorViewModel extends BaseObservable implements ViewModel {

    private final WebNavigator webNavigator;

    private Sponsor sponsor;

    SponsorViewModel(WebNavigator webNavigator, Sponsor sponsor) {
        this.webNavigator = webNavigator;
        this.sponsor = sponsor;
    }

    @Override
    public void destroy() {
        // Nothing to do
    }

    public void onClickSponsor(@SuppressWarnings("UnusedParameters") View view) {
        webNavigator.navigateTo(sponsor.url);
    }

    public Sponsor getSponsor() {
        return sponsor;
    }
}
