package io.github.droidkaigi.confsched2017.repository.contributors;

import java.util.List;

import io.github.droidkaigi.confsched2017.model.Contributor;
import io.reactivex.Single;

public interface ContributorsDataSource {
    Single<List<Contributor>> findAll();
}
