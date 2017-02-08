package io.github.droidkaigi.confsched2017.repository.sessions;

import android.support.annotation.VisibleForTesting;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.github.droidkaigi.confsched2017.model.Session;
import io.reactivex.Maybe;
import io.reactivex.Single;

@Singleton
public class SessionsRepository implements SessionsDataSource {

    private final SessionsLocalDataSource localDataSource;

    private final SessionsRemoteDataSource remoteDataSource;

    @VisibleForTesting Map<Integer, Session> cachedSessions;

    private boolean isDirty;

    @Inject
    public SessionsRepository(SessionsLocalDataSource localDataSource, SessionsRemoteDataSource remoteDataSource) {
        this.localDataSource = localDataSource;
        this.remoteDataSource = remoteDataSource;
        this.cachedSessions = new LinkedHashMap<>();
        this.isDirty = true;
    }

    @Override
    public Single<List<Session>> findAll(String languageId) {
        if (hasCacheSessions()) {
            return Single.create(emitter -> {
                emitter.onSuccess(new ArrayList<>(cachedSessions.values()));
            });
        }

        if (isDirty) {
            return findAllFromRemote(languageId);
        } else {
            return findAllFromLocal(languageId);
        }
    }

    @Override
    public Maybe<Session> find(int sessionId, String languageId) {
        if (hasCacheSession(sessionId)) {
            return Maybe.create(emitter -> {
                emitter.onSuccess(cachedSessions.get(sessionId));
            });
        }

        if (isDirty) {
            return remoteDataSource.find(sessionId, languageId);
        } else {
            return localDataSource.find(sessionId, languageId);
        }
    }

    @Override
    public void updateAllAsync(List<Session> sessions) {
        localDataSource.updateAllAsync(sessions);
    }

    /**
     * Clear all caches. only for debug purposes
     */
    @Override
    public void deleteAll() {
        cachedSessions.clear();
        localDataSource.deleteAll();
        isDirty = true;
    }

    private Single<List<Session>> findAllFromLocal(String languageId) {
        return localDataSource.findAll(languageId)
                .flatMap(sessions -> {
                    if (sessions.isEmpty()) {
                        return findAllFromRemote(languageId);
                    } else {
                        refreshCache(sessions);
                        return Single.create(emitter -> emitter.onSuccess(sessions));
                    }
                });
    }

    private Single<List<Session>> findAllFromRemote(String languageId) {
        return remoteDataSource.findAll(languageId)
                .map(sessions -> {
                    refreshCache(sessions);
                    updateAllAsync(sessions);
                    return sessions;
                });
    }

    private void refreshCache(List<Session> sessions) {
        if (cachedSessions == null) {
            cachedSessions = new LinkedHashMap<>();
        }
        cachedSessions.clear();
        for (Session session : sessions) {
            cachedSessions.put(session.id, session);
        }
        isDirty = false;
    }

    public void setIdDirty(boolean isDirty) {
        this.isDirty = isDirty;
    }

    boolean hasCacheSessions() {
        return cachedSessions != null && !cachedSessions.isEmpty() && !isDirty;
    }

    boolean hasCacheSession(int sessionId) {
        return cachedSessions != null && cachedSessions.containsKey(sessionId) && !isDirty;
    }

}
