package io.github.droidkaigi.confsched2017.repository.sessions;

import java.util.List;

import javax.inject.Inject;

import io.github.droidkaigi.confsched2017.model.OrmaDatabase;
import io.github.droidkaigi.confsched2017.model.Room;
import io.github.droidkaigi.confsched2017.model.Room_Relation;
import io.github.droidkaigi.confsched2017.model.Session;
import io.github.droidkaigi.confsched2017.model.Session_Relation;
import io.github.droidkaigi.confsched2017.model.Speaker;
import io.github.droidkaigi.confsched2017.model.Speaker_Relation;
import io.github.droidkaigi.confsched2017.model.Topic;
import io.github.droidkaigi.confsched2017.model.Topic_Relation;
import io.reactivex.Maybe;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
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
    public Single<List<Session>> findAll(String languageId) {
        return sessionRelation().selector().executeAsObservable().toList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Maybe<Session> find(int sessionId, String languageId) {
        return sessionRelation().selector().idEq(sessionId).executeAsObservable().firstElement();
    }

    void deleteAll() {
        sessionRelation().deleter().execute();
        speakerRelation().deleter().execute();
        topicRelation().deleter().execute();
        placeRelation().deleter().execute();
    }

    private void insertSpeaker(Speaker speaker) {
        if (speaker != null && speakerRelation().selector().idEq(speaker.id).isEmpty()) {
            speakerRelation().inserter().execute(speaker);
        }
    }

    private void insertRoom(Room room) {
        if (room != null && placeRelation().selector().idEq(room.id).isEmpty()) {
            placeRelation().inserter().execute(room);
        }
    }

    private void insertCategory(Topic topic) {
        if (topic != null && topicRelation().selector().idEq(topic.id).isEmpty()) {
            topicRelation().inserter().execute(topic);
        }
    }

    private void updateAllSync(List<Session> sessions) {
        speakerRelation().deleter().execute();
        topicRelation().deleter().execute();
        placeRelation().deleter().execute();
        sessionRelation().deleter().execute();

        for (Session session : sessions) {
            insertSpeaker(session.speaker);
            insertCategory(session.topic);
            insertRoom(session.room);
            sessionRelation().inserter().execute(session);
        }
    }

    @Override
    public void updateAllAsync(List<Session> sessions) {
        orma.transactionAsCompletable(() -> updateAllSync(sessions))
                .subscribeOn(Schedulers.io())
                .subscribe();
    }

}
