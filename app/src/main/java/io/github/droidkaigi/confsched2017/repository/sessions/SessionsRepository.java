package io.github.droidkaigi.confsched2017.repository.sessions;

import android.support.annotation.VisibleForTesting;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
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
    public Single<List<Session>> findAll(Locale locale) {
        if (hasCacheSessions()) {
            return Single.create(emitter -> {
                emitter.onSuccess(new ArrayList<>(cachedSessions.values()));
            });
        }

        if (isDirty) {
            return findAllFromRemote(locale);
        } else {
            return findAllFromLocal(locale);
        }
    }

    @Override
    public Maybe<Session> find(int sessionId, Locale locale) {
        if (hasCacheSession(sessionId)) {
            return Maybe.create(emitter -> {
                emitter.onSuccess(cachedSessions.get(sessionId));
            });
        }

        if (isDirty) {
            return remoteDataSource.find(sessionId, locale);
        } else {
            return localDataSource.find(sessionId, locale);
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

    private Single<List<Session>> findAllFromLocal(Locale locale) {
        return localDataSource.findAll(locale)
                .flatMap(sessions -> {
                    if (sessions.isEmpty()) {
                        return findAllFromRemote(locale);
                    } else {
                        refreshCache(sessions);
                        return Single.create(emitter -> emitter.onSuccess(sessions));
                    }
                });
    }

    private Single<List<Session>> findAllFromRemote(Locale locale) {
        return remoteDataSource.findAll(locale)
                .doOnSuccess(sessions -> {
                    refreshCache(sessions);
                    updateAllAsync(sessions);
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
