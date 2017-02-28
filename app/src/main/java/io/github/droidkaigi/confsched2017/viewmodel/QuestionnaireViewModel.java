package io.github.droidkaigi.confsched2017.viewmodel;

import android.content.Context;
import android.databinding.BaseObservable;
import android.support.annotation.NonNull;
import android.view.View;

import javax.inject.Inject;

import io.github.droidkaigi.confsched2017.model.Questionnaire;
import io.github.droidkaigi.confsched2017.repository.feedbacks.QuestionnaireRepository;
import io.reactivex.Single;
import retrofit2.Response;

public class QuestionnaireViewModel extends BaseObservable implements ViewModel {

    private final Context context;

    private QuestionnaireViewModel.Callback callback;

    private QuestionnaireRepository questionnaireRepository;

    @Inject
    QuestionnaireViewModel(Context context, QuestionnaireRepository questionnaireRepository) {
        this.context = context;
        this.questionnaireRepository = questionnaireRepository;
    }

    @Override
    public void destroy() {
        this.callback = null;
    }

    public void onClickSubmitQuestionnaireButton(@SuppressWarnings("unused") View view) {
        if (callback != null) {
            callback.onClickSubmitQuestionnaire();
        }
    }

    public Single<Response<Void>> submitQuestionnaire(Questionnaire questionnaire) {
        return questionnaireRepository.submit(questionnaire);
    }

    public void setCallback(@NonNull QuestionnaireViewModel.Callback callback) {
        this.callback = callback;
    }

    public interface Callback {

        void onClickSubmitQuestionnaire();

    }

}
