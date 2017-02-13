package io.github.droidkaigi.confsched2017.repository.sessions;

import android.text.TextUtils;

import java.util.List;
import java.util.Locale;

import javax.inject.Inject;

import io.github.droidkaigi.confsched2017.api.DroidKaigiClient;
import io.github.droidkaigi.confsched2017.model.Session;
import io.reactivex.Maybe;
import io.reactivex.Observable;
import io.reactivex.Single;

public final class SessionsRemoteDataSource implements SessionsDataSource {

    private final DroidKaigiClient client;

    @Inject
    public SessionsRemoteDataSource(DroidKaigiClient client) {
        this.client = client;
    }

    @Override
    public Single<List<Session>> findAll(Locale locale) {
        return client.getSessions(locale)
                .doOnSuccess(sessions -> {
                    // API returns some sessions which have empty room info.
                    for (Session session : sessions) {
                        if (session.room != null && TextUtils.isEmpty(session.room.name)) {
                            session.room = null;
                        }
                    }
                });
    }

    @Override
    public Maybe<Session> find(int sessionId, Locale locale) {
        return findAll(locale)
                .toObservable()
                .flatMap(Observable::fromIterable)
                .filter(session -> session.id == sessionId)
                .singleElement();
    }

    @Override
    public void updateAllAsync(List<Session> sessions) {
        // Do nothing
    }

    @Override
    public void deleteAll() {
        // Do nothing
    }
}
