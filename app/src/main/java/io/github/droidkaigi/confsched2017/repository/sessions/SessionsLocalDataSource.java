package io.github.droidkaigi.confsched2017.repository.sessions;

import com.github.gfx.android.orma.annotation.OnConflict;

import android.database.sqlite.SQLiteConstraintException;

import java.util.List;
import java.util.Locale;

import javax.inject.Inject;

import io.github.droidkaigi.confsched2017.model.OrmaDatabase;
import io.github.droidkaigi.confsched2017.model.Room;
import io.github.droidkaigi.confsched2017.model.Room_Relation;
import io.github.droidkaigi.confsched2017.model.Session;
import io.github.droidkaigi.confsched2017.model.Session_Relation;
import io.github.droidkaigi.confsched2017.model.Session_Updater;
import io.github.droidkaigi.confsched2017.model.Speaker;
import io.github.droidkaigi.confsched2017.model.Speaker_Relation;
import io.github.droidkaigi.confsched2017.model.Topic;
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

    private void upsertSpeaker(Speaker speaker) {
        if (speaker != null) {
            speakerRelation().upsert(speaker);
        }
    }

    private void upsertRoom(Room room) {
        if (room != null) {
            placeRelation().upsert(room);
        }
    }

    private void upsertCategory(Topic topic) {
        if (topic != null) {
            topicRelation().upsert(topic);
        }
    }

    private void updateAllSync(List<Session> sessions) {
        for (Session session : sessions) {
            upsertSpeaker(session.speaker);
            upsertCategory(session.topic);
            upsertRoom(session.room);

            try {
                sessionRelation().inserter(OnConflict.FAIL).execute(session);
            } catch (SQLiteConstraintException e) {
                // failed by conflict, so try update one.
                Session_Updater sessionUpdater = sessionRelation().updater()
                        .id(session.id)
                        .title(session.title)
                        .desc(session.desc)
                        .stime(session.stime)
                        .etime(session.etime)
                        .durationMin(session.durationMin)
                        .type(session.type)
                        .lang(session.lang)
                        .slideUrl(session.slideUrl)
                        .movieUrl(session.movieUrl)
                        .movieDashUrl(session.movieDashUrl)
                        .shareUrl(session.shareUrl);
                // nullable association fields
                if (session.speaker != null) {
                    sessionUpdater.speaker(session.speaker);
                }
                if (session.topic != null) {
                    sessionUpdater.topic(session.topic);
                }
                if (session.room != null) {
                    sessionUpdater.room(session.room);
                }
                sessionUpdater.idEq(session.id).execute();
            }
        }
    }

    @Override
    public void updateAllAsync(List<Session> sessions) {
        orma.transactionAsCompletable(() -> updateAllSync(sessions))
                .subscribeOn(Schedulers.io())
                .subscribe();
    }

}
