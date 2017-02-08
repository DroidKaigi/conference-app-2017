package io.github.droidkaigi.confsched2017.repository.feedbacks;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.github.droidkaigi.confsched2017.model.SessionFeedback;
import io.reactivex.Single;
import retrofit2.Response;

@Singleton
public class SessionFeedbackRepository {

    private final SessionFeedbackRemoteDataSource remoteDataSource;

    @Inject
    public SessionFeedbackRepository(SessionFeedbackRemoteDataSource remoteDataSource) {
        this.remoteDataSource = remoteDataSource;
    }

    public Single<Response<Void>> submit(SessionFeedback sessionFeedback) {
        return remoteDataSource.submit(sessionFeedback);
    }

}
