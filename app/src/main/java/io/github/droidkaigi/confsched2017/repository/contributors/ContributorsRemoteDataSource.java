package io.github.droidkaigi.confsched2017.repository.contributors;

import java.util.List;

import javax.inject.Inject;

import io.github.droidkaigi.confsched2017.api.DroidKaigiClient;
import io.github.droidkaigi.confsched2017.model.Contributor;
import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;

public class ContributorsRemoteDataSource implements ContributorsReadDataSource {

    private final DroidKaigiClient client;

    @Inject
    ContributorsRemoteDataSource(DroidKaigiClient client) {
        this.client = client;
    }

    @Override
    public Single<List<Contributor>> findAll() {
        return client.getContributors().subscribeOn(Schedulers.io());
    }

}
