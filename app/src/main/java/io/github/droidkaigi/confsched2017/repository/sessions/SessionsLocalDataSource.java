package io.github.droidkaigi.confsched2017.repository.sessions;

import java.util.List;
import java.util.Locale;

import javax.inject.Inject;

import io.github.droidkaigi.confsched2017.model.OrmaDatabase;
import io.github.droidkaigi.confsched2017.model.Room_Relation;
import io.github.droidkaigi.confsched2017.model.Session;
import io.github.droidkaigi.confsched2017.model.Session_Relation;
import io.github.droidkaigi.confsched2017.model.Speaker_Relation;
import io.github.droidkaigi.confsched2017.model.Topic_Relation;
import io.reactivex.Maybe;
import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;

public final class SessionsLocalDataSource implements SessionsDataSource {

    private final OrmaDatabase orma;

    @Inject
    public SessionsLocalDataSource(OrmaDatabase orma) {
        this.orma = orma;
    }

    private Session_Relation sessionRelation() {
        return orma.relationOfSession();
    }

    private Speaker_Relation speakerRelation() {
        return orma.relationOfSpeaker();
    }

    private Room_Relation placeRelation() {
        return orma.relationOfRoom();
    }

    private Topic_Relation topicRelation() {
        return orma.relationOfTopic();
    }

    @Override
    public Single<List<Session>> findAll(Locale locale) {
        return sessionRelation().selector().executeAsObservable().toList();
    }

    @Override
    public Maybe<Session> find(int sessionId, Locale locale) {
        return sessionRelation().selector().idEq(sessionId).executeAsObservable().firstElement();
    }

    @Override
    public void deleteAll() {
        sessionRelation().deleter().execute();
        speakerRelation().deleter().execute();
        topicRelation().deleter().execute();
        placeRelation().deleter().execute();

    }

    private void updateAllSync(List<Session> sessions) {
        Session_Relation relation = sessionRelation();

        for (Session session : sessions) {
            relation.upsert(session);
        }
    }

    @Override
    public void updateAllAsync(List<Session> sessions) {
        orma.transactionAsCompletable(() -> updateAllSync(sessions))
                .subscribeOn(Schedulers.io())
                .subscribe();
    }

}
