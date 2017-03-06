package io.github.droidkaigi.confsched2017.repository.feedbacks;

import javax.inject.Inject;

import io.github.droidkaigi.confsched2017.api.DroidKaigiClient;
import io.github.droidkaigi.confsched2017.model.Questionnaire;
import io.reactivex.Single;
import retrofit2.Response;

public class QuestionnaireRemoteDataSource {

    private final DroidKaigiClient client;

    @Inject
    public QuestionnaireRemoteDataSource(DroidKaigiClient client) {
        this.client = client;
    }

    public Single<Response<Void>> submit(Questionnaire questionnaire) {
        return client.submitQuestionnaire(questionnaire);
    }
}
