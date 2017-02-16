package io.github.droidkaigi.confsched2017.viewmodel;

import android.databinding.BaseObservable;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableList;

import com.annimon.stream.Stream;

import java.util.List;

import io.github.droidkaigi.confsched2017.model.Sponsorship;
import io.github.droidkaigi.confsched2017.view.helper.Navigator;

public final class SponsorshipViewModel extends BaseObservable implements ViewModel {

    private final Navigator navigator;

    private Sponsorship sponsorship;

    private String category;

    private ObservableList<SponsorViewModel> sponsorViewModels;

    SponsorshipViewModel(Navigator navigator, Sponsorship sponsorship) {
        this.navigator = navigator;
        this.sponsorship = sponsorship;
        this.category = sponsorship.category;
        this.sponsorViewModels = new ObservableArrayList<>();
        this.sponsorViewModels.addAll(convertSponsor(sponsorship));
    }

    @Override
    public void destroy() {
        // No-op
    }

    public Sponsorship getSponsorship() {
        return sponsorship;
    }

    public String getCategory() {
        return category;
    }

    public ObservableList<SponsorViewModel> getSponsorViewModels() {
        return sponsorViewModels;
    }

    private List<SponsorViewModel> convertSponsor(Sponsorship sponsorship) {
        return Stream.of(sponsorship.sponsors)
                .map(sponsor -> new SponsorViewModel(navigator, sponsor))
                .toList();
    }
}
