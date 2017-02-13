package io.github.droidkaigi.confsched2017.log;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;
import io.reactivex.subjects.Subject;

/**
 * @author KeithYokoma
 */
@Singleton
public class LogEmitter {
    private final Subject<OverlayLog> subject = PublishSubject.create();

    @Inject
    public LogEmitter() {
    }

    public void log(int priority, String tag, String message) {
        subject.onNext(new OverlayLog(priority, tag, message));
    }

    public Observable<OverlayLog> listen() {
        return subject;
    }
}
