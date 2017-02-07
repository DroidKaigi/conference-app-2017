package io.github.droidkaigi.confsched2017.repository.contributors;

import com.github.gfx.android.orma.annotation.OnConflict;

import java.util.List;

import javax.inject.Inject;

import io.github.droidkaigi.confsched2017.model.Contributor;
import io.github.droidkaigi.confsched2017.model.OrmaDatabase;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class ContributorsLocalDataSource {

    private final OrmaDatabase orma;

    @Inject
    ContributorsLocalDataSource(OrmaDatabase orma) {
        this.orma = orma;
    }

    public Single<List<Contributor>> findAll() {
        return orma.selectFromContributor()
                .executeAsObservable()
                .toList()
                .subscribeOn(Schedulers.io());
    }

    private void updateAllSync(List<Contributor> contributors) {
        orma.prepareInsertIntoContributor(OnConflict.REPLACE).executeAll(contributors);
    }

    void updateAllAsync(List<Contributor> contributors) {
        orma.transactionAsCompletable(() -> updateAllSync(contributors))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe();
    }
}
