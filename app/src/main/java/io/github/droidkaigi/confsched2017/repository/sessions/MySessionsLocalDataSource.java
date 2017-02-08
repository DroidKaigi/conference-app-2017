package io.github.droidkaigi.confsched2017.repository.sessions;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.github.droidkaigi.confsched2017.model.MySession;
import io.github.droidkaigi.confsched2017.model.MySession_Relation;
import io.github.droidkaigi.confsched2017.model.OrmaDatabase;
import io.github.droidkaigi.confsched2017.model.Session;
import io.reactivex.Completable;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class MySessionsLocalDataSource implements MySessionsDataSource {

    private final OrmaDatabase orma;

    @Inject
    public MySessionsLocalDataSource(OrmaDatabase orma) {
        this.orma = orma;
    }

    private MySession_Relation mySessionRelation() {
        return orma.relationOfMySession();
    }

    @Override
    public Single<List<MySession>> findAll() {
        // TODO
        if (mySessionRelation().isEmpty()) {
            return Single.create(emitter -> {
                emitter.onSuccess(new ArrayList<>());
            });
        } else {
            return mySessionRelation().selector().executeAsObservable().toList()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread());
        }
    }

    @Override
    public Completable save(@NonNull Session session) {
        return orma.transactionAsCompletable(() -> {
            mySessionRelation().deleter().sessionEq(session.id).execute();
            mySessionRelation().inserter().execute(new MySession(session));
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Single<Integer> delete(@NonNull Session session) {
        return mySessionRelation().deleter().sessionEq(session.id).executeAsSingle()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public boolean isExist(int sessionId) {
        return !mySessionRelation().selector().sessionEq(sessionId).isEmpty();
    }
}
