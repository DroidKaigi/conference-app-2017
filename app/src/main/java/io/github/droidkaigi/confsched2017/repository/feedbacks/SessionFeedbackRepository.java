package io.github.droidkaigi.confsched2017.repository.feedbacks;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.github.droidkaigi.confsched2017.model.SessionFeedback;
import io.reactivex.Single;
import retrofit2.Response;

@Singleton
public class SessionFeedbackRepository {

    private final SessionFeedbackRemoteDataSource remoteDataSource;

    private final SessionFeedbackLocalDataSource localDataSource;

    @Inject
    public SessionFeedbackRepository(SessionFeedbackRemoteDataSource remoteDataSource,
            SessionFeedbackLocalDataSource localDataSource) {
        this.remoteDataSource = remoteDataSource;
        this.localDataSource = localDataSource;
    }

    public Single<Response<Void>> submit(SessionFeedback sessionFeedback) {
        return remoteDataSource.submit(sessionFeedback);
    }

    @Nullable
    public SessionFeedback findFromCache(int sessionId) {
        return localDataSource.find(sessionId);
    }

    public void saveToCache(@NonNull SessionFeedback sessionFeedback) {
        localDataSource.save(sessionFeedback);
    }

}
