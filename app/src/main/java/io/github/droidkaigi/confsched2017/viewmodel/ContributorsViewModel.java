package io.github.droidkaigi.confsched2017.viewmodel;

import com.annimon.stream.Collectors;
import com.annimon.stream.Stream;

import android.databinding.BaseObservable;

import java.util.List;

import javax.inject.Inject;

import io.github.droidkaigi.confsched2017.repository.contributors.ContributorsRepository;
import io.reactivex.Single;

public final class ContributorsViewModel extends BaseObservable implements ViewModel {

    private final ContributorsRepository contributorsRepository;

    @Inject
    ContributorsViewModel(ContributorsRepository contributorsRepository) {
        this.contributorsRepository = contributorsRepository;
    }

    @Override
    public void destroy() {
    }

    public Single<List<ContributorViewModel>> getContributors() {
        return contributorsRepository.findAll().map(contributors ->
                Stream.of(contributors).map(ContributorViewModel::new)
                        .collect(Collectors.toList()));
    }
}
