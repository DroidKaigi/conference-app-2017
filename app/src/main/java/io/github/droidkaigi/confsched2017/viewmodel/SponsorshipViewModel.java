package io.github.droidkaigi.confsched2017.viewmodel;

import com.annimon.stream.Collectors;
import com.annimon.stream.Stream;

import android.databinding.BaseObservable;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableList;

import java.util.List;

import javax.inject.Inject;

import io.github.droidkaigi.confsched2017.model.Sponsorship;

public final class SponsorshipViewModel extends BaseObservable implements ViewModel {

    private Sponsorship sponsorship;

    private String category;

    private ObservableList<SponsorViewModel> sponsorViewModels;

    @Inject
    SponsorshipViewModel(Sponsorship sponsorship) {
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
                .map(SponsorViewModel::new)
                .collect(Collectors.toList());
    }
}
