package io.github.droidkaigi.confsched2017.repository.sessions;

import java.util.List;

import io.github.droidkaigi.confsched2017.model.Session;
import io.reactivex.Maybe;
import io.reactivex.Single;

public interface SessionsDataSource {

    Single<List<Session>> findAll(String languageId);

    public Maybe<Session> find(int sessionId, String languageId);

    public void updateAllAsync(List<Session> sessions);

    void deleteAll();
}
