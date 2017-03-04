package io.github.droidkaigi.confsched2017.viewmodel;

import android.databinding.BaseObservable;
import android.view.View;

import javax.inject.Inject;

import io.github.droidkaigi.confsched2017.api.DroidKaigiClient;
import io.github.droidkaigi.confsched2017.model.Questionnaire;
import io.github.droidkaigi.confsched2017.repository.feedbacks.QuestionnaireRepository;
import io.github.droidkaigi.confsched2017.view.helper.Navigator;
import io.github.droidkaigi.confsched2017.view.helper.ResourceResolver;
import io.reactivex.Single;
import retrofit2.Response;
import timber.log.Timber;

public class QuestionnaireViewModel extends BaseObservable implements ViewModel {

    private QuestionnaireRepository questionnaireRepository;

    private DroidKaigiClient droidKaigiClient;

    public Questionnaire questionnaire;

    private final ResourceResolver resourceResolver;

    private final Navigator navigator;

    @Inject
    QuestionnaireViewModel(QuestionnaireRepository questionnaireRepository, DroidKaigiClient droidKaigiClient,
            Navigator navigator, ResourceResolver resourceResolver) {
        this.questionnaireRepository = questionnaireRepository;
        this.droidKaigiClient = droidKaigiClient;
        this.resourceResolver = resourceResolver;
        this.navigator = navigator;
        questionnaire = new Questionnaire();
    }

    @Override
    public void destroy() {
    }

    public void onClickSubmitQuestionnaireButton(@SuppressWarnings("unused") View view) {
        // TODO: replace real data
        // TODO: 連続クリック時の制御
//        droidKaigiClient.submitQuestionnaire(Questionnaire.createTestData())
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe((response) -> Timber.d("submit success\n%s", response), Timber::e);
        // TODO: delete this (only in developing)
        Timber.d("test questionnaire=%s", questionnaire);
    }

    public Single<Response<Void>> submitQuestionnaire(Questionnaire questionnaire) {
        return questionnaireRepository.submit(questionnaire);
    }

}
