package io.github.droidkaigi.confsched2017.viewmodel;

import com.annimon.stream.Collectors;
import com.annimon.stream.Stream;

import android.databinding.BaseObservable;

import java.util.List;

import javax.inject.Inject;

import io.github.droidkaigi.confsched2017.model.Sponsorship;

public final class SponsorshipViewModel extends BaseObservable implements ViewModel {

    private Sponsorship sponsorship;

    private String category;

    private List<SponsorViewModel> sponsors;

    @Inject
    SponsorshipViewModel(Sponsorship sponsorship) {
        this.sponsorship = sponsorship;
        this.category = sponsorship.category;
        this.sponsors = setSponsorship(sponsorship);
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

    public List<SponsorViewModel> getSponsors() {
        return sponsors;
    }

    private List<SponsorViewModel> setSponsorship(Sponsorship sponsorship) {
        return Stream.of(sponsorship.sponsors)
                .map(sponsor -> {
                    return new SponsorViewModel(sponsor);
                })
                .collect(Collectors.toList());
    }
}
