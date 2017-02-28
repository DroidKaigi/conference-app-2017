package io.github.droidkaigi.confsched2017.repository.feedbacks;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.github.droidkaigi.confsched2017.model.Questionnaire;
import io.github.droidkaigi.confsched2017.model.SessionFeedback;
import io.reactivex.Single;
import retrofit2.Response;

@Singleton
public class QuestionnaireRepository {

    private final QuestionnaireRemoteDataSource remoteDataSource;

    @Inject
    public QuestionnaireRepository(QuestionnaireRemoteDataSource remoteDataSource) {
        this.remoteDataSource = remoteDataSource;
    }

    public Single<Response<Void>> submit(Questionnaire sessionFeedback) {
        return remoteDataSource.submit(sessionFeedback);
    }

}
