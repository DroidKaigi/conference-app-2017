package io.github.droidkaigi.confsched2017.repository.contributors;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.github.droidkaigi.confsched2017.model.Contributor;
import io.reactivex.Single;

@Singleton
public class ContributorsRepository {

    private final ContributorsLocalDataSource localDataSource;

    private final ContributorsRemoteDataSource remoteDataSource;

    private Map<String, Contributor> cachedContributors;

    private boolean isDirty;

    @Inject
    ContributorsRepository(ContributorsLocalDataSource localDataSource, ContributorsRemoteDataSource remoteDataSource) {
        this.localDataSource = localDataSource;
        this.remoteDataSource = remoteDataSource;
        this.cachedContributors = new LinkedHashMap<>();
        this.isDirty = true;
    }

    public void setDirty(boolean isDirty) {
        this.isDirty = isDirty;
    }

    public Single<List<Contributor>> findAll() {
        if (cachedContributors != null && !cachedContributors.isEmpty() && !isDirty) {
            return Single.create(emitter -> {
                emitter.onSuccess(new ArrayList<>(cachedContributors.values()));
            });
        }

        if (isDirty) {
            return findAllFromRemote();
        } else {
            return findAllFromLocal();
        }
    }


    private Single<List<Contributor>> findAllFromRemote() {
        return remoteDataSource.findAll()
                .doOnSuccess(contributors -> {
                    refreshCache(contributors);
                    localDataSource.updateAllAsync(contributors);
                });
    }

    private Single<List<Contributor>> findAllFromLocal() {
        return localDataSource.findAll().flatMap(contributors -> {
            if (contributors.isEmpty()) {
                return findAllFromRemote();
            } else {
                refreshCache(contributors);
                return Single.create(emitter -> emitter.onSuccess(contributors));
            }
        });
    }

    private void refreshCache(List<Contributor> contributors) {
        if (cachedContributors == null) {
            cachedContributors = new LinkedHashMap<>();
        }
        cachedContributors.clear();
        for (Contributor contributor : contributors) {
            cachedContributors.put(contributor.name, contributor);
        }
        isDirty = false;
    }

}
