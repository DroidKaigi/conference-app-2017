package io.github.droidkaigi.confsched2017.repository.sessions;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.github.droidkaigi.confsched2017.model.MySession;
import io.github.droidkaigi.confsched2017.model.Session;
import io.reactivex.Completable;
import io.reactivex.Single;

@Singleton
public class MySessionsRepository implements MySessionsDataSource {

    private final MySessionsDataSource localDataSource;

    private Map<Integer, MySession> cachedMySessions;

    @Inject
    public MySessionsRepository(MySessionsLocalDataSource localDataSource) {
        this.localDataSource = localDataSource;
        this.cachedMySessions = new LinkedHashMap<>();
    }

    @Override
    public Single<List<MySession>> findAll() {
        if (cachedMySessions != null && !cachedMySessions.isEmpty()) {
            return Single.create(emitter -> {
                emitter.onSuccess(new ArrayList<>(cachedMySessions.values()));
            });
        }

        return localDataSource.findAll().doOnSuccess(this::refreshCache);
    }

    @Override
    public Completable save(@NonNull Session session) {
        cachedMySessions.put(session.id, new MySession(session));
        return localDataSource.save(session);
    }

    @Override
    public Single<Integer> delete(@NonNull Session session) {
        cachedMySessions.remove(session.id);
        return localDataSource.delete(session);
    }

    @Override
    public boolean isExist(int sessionId) {
        return localDataSource.isExist(sessionId);
    }

    private void refreshCache(List<MySession> mySessions) {
        if (cachedMySessions == null) {
            cachedMySessions = new LinkedHashMap<>();
        }
        cachedMySessions.clear();
        for (MySession mySession : mySessions) {
            cachedMySessions.put(mySession.session.id, mySession);
        }
    }
}
