package io.github.droidkaigi.confsched2017.repository.feedbacks;

import com.github.gfx.android.orma.annotation.OnConflict;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import javax.inject.Inject;

import io.github.droidkaigi.confsched2017.model.OrmaDatabase;
import io.github.droidkaigi.confsched2017.model.SessionFeedback;
import io.reactivex.schedulers.Schedulers;

public class SessionFeedbackLocalDataSource {

    private final OrmaDatabase orma;

    @Inject
    public SessionFeedbackLocalDataSource(OrmaDatabase orma) {
        this.orma = orma;
    }

    public void save(@NonNull SessionFeedback sessionFeedback) {
        orma.transactionAsCompletable(() -> orma.prepareInsertIntoSessionFeedback(OnConflict.REPLACE).execute(sessionFeedback))
                .subscribeOn(Schedulers.io())
                .subscribe();
    }

    @Nullable
    public SessionFeedback find(int sessionId) {
        return orma.relationOfSessionFeedback().selector().sessionIdEq(sessionId).getOrNull(0);
    }
}
