package io.github.droidkaigi.confsched2017.repository.sessions;

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

    private final SessionsDataSource localDataSourse;

    private final SessionsDataSource remoteDataSourse;

    private Map<Integer, Session> cachedSessions;

    private boolean isDirty;

    @Inject
    public SessionsRepository(SessionsLocalDataSource localDataSource, SessionsRemoteDataSource remoteDataSource) {
        this.localDataSourse = localDataSource;
        this.remoteDataSourse = remoteDataSource;
        this.cachedSessions = new LinkedHashMap<>();
        this.isDirty = true;
    }

    @Override
    public Single<List<Session>> findAll(String languageId) {
        if (cachedSessions != null && !cachedSessions.isEmpty() && !isDirty) {
            return Single.create(emitter -> {
                try {
                    emitter.onSuccess(new ArrayList<>(cachedSessions.values()));
                } catch (Exception e) {
                    emitter.onError(e);
                }
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
        if (cachedSessions != null && cachedSessions.containsKey(sessionId) && !isDirty) {
            return Maybe.create(emitter -> {
                try {
                    emitter.onSuccess(cachedSessions.get(sessionId));
                } catch (Exception e) {
                    emitter.onError(e);
                }
            });
        }

        if (isDirty) {
            return remoteDataSourse.find(sessionId, languageId);
        } else {
            return localDataSourse.find(sessionId, languageId);
        }
    }

    @Override
    public void updateAllAsync(List<Session> sessions) {
        localDataSourse.updateAllAsync(sessions);
    }

    private Single<List<Session>> findAllFromLocal(String languageId) {
        return localDataSourse.findAll(languageId)
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
        return remoteDataSourse.findAll(languageId)
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
}
