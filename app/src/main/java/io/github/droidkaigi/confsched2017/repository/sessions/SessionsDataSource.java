package io.github.droidkaigi.confsched2017.repository.sessions;

import java.util.List;
import java.util.Locale;

import io.github.droidkaigi.confsched2017.model.Session;
import io.reactivex.Maybe;
import io.reactivex.Single;

public interface SessionsDataSource {

    Single<List<Session>> findAll(Locale locale);

    public Maybe<Session> find(int sessionId, Locale locale);

    public void updateAllAsync(List<Session> sessions);

    void deleteAll();
}
