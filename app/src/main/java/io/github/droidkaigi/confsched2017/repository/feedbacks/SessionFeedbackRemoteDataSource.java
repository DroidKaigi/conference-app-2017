package io.github.droidkaigi.confsched2017.repository.feedbacks;

import javax.inject.Inject;

import io.github.droidkaigi.confsched2017.api.DroidKaigiClient;
import io.github.droidkaigi.confsched2017.model.SessionFeedback;
import io.reactivex.Single;
import retrofit2.Response;

public class SessionFeedbackRemoteDataSource {

    private final DroidKaigiClient client;

    @Inject
    public SessionFeedbackRemoteDataSource(DroidKaigiClient client) {
        this.client = client;
    }

    public Single<Response<Void>> submit(SessionFeedback sessionFeedback) {
       return client.submitSessionFeedback(sessionFeedback);
    }
}
