package io.github.droidkaigi.confsched2017.viewmodel;

import android.content.Context;
import android.databinding.BaseObservable;
import android.support.annotation.NonNull;
import android.view.View;

import javax.inject.Inject;

import io.github.droidkaigi.confsched2017.api.DroidKaigiClient;
import io.github.droidkaigi.confsched2017.model.Questionnaire;
import io.github.droidkaigi.confsched2017.repository.feedbacks.QuestionnaireRepository;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;
import timber.log.Timber;

public class QuestionnaireViewModel extends BaseObservable implements ViewModel {

    private final Context context;

    private QuestionnaireViewModel.Callback callback;

    private QuestionnaireRepository questionnaireRepository;
    private DroidKaigiClient droidKaigiClient;

    @Inject
    QuestionnaireViewModel(Context context, QuestionnaireRepository questionnaireRepository, DroidKaigiClient droidKaigiClient) {
        this.context = context;
        this.questionnaireRepository = questionnaireRepository;
        this.droidKaigiClient = droidKaigiClient;
    }

    @Override
    public void destroy() {
        this.callback = null;
    }

    public void onClickSubmitQuestionnaireButton(@SuppressWarnings("unused") View view) {
//        if (callback != null) {
//            callback.onClickSubmitQuestionnaire();
//        }
        // TODO: replace real data
        // TODO: 連続クリック時の制御
        droidKaigiClient.submitQuestionnaire(Questionnaire.createTestData())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe((response) -> Timber.d("submit success\n%s", response), Timber::e);
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
