package io.github.droidkaigi.confsched2017.viewmodel;

import android.databinding.BaseObservable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import javax.inject.Inject;

import io.github.droidkaigi.confsched2017.model.Questionnaire;
import io.github.droidkaigi.confsched2017.repository.feedbacks.QuestionnaireRepository;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

public class QuestionnaireViewModel extends BaseObservable implements ViewModel {

    private static final String TAG = QuestionnaireViewModel.class.getSimpleName();

    private final QuestionnaireRepository questionnaireRepository;

    public final Questionnaire questionnaire;

    private final CompositeDisposable compositeDisposable;

    @Nullable
    private Callback callback;

    @Inject
    QuestionnaireViewModel(QuestionnaireRepository questionnaireRepository,
            CompositeDisposable compositeDisposable) {
        this.questionnaireRepository = questionnaireRepository;
        this.compositeDisposable = compositeDisposable;
        questionnaire = new Questionnaire();
    }

    @Override
    public void destroy() {
        compositeDisposable.clear();
    }

    public void onClickSubmitQuestionnaireButton(@SuppressWarnings("unused") View view) {
        compositeDisposable.add(questionnaireRepository.submit(questionnaire)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(success -> {
                    if (callback != null) {
                        callback.showSuccess();
                    }
                }, failure -> {
                    Timber.tag(TAG).e(failure, "questionnaire=%s", questionnaire);
                    if (callback != null) {
                        callback.showError();
                    }
                }));
    }

    public void setCallback(@NonNull Callback callback) {
        this.callback = callback;
    }

    public interface Callback {

        void showSuccess();

        void showError();
    }
}
