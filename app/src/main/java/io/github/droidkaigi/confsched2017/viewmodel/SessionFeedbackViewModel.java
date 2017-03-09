package io.github.droidkaigi.confsched2017.viewmodel;

import com.android.databinding.library.baseAdapters.BR;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.support.annotation.NonNull;
import android.view.View;

import java.util.Locale;

import javax.inject.Inject;

import io.github.droidkaigi.confsched2017.R;
import io.github.droidkaigi.confsched2017.model.Session;
import io.github.droidkaigi.confsched2017.model.SessionFeedback;
import io.github.droidkaigi.confsched2017.repository.feedbacks.SessionFeedbackRepository;
import io.github.droidkaigi.confsched2017.repository.sessions.SessionsRepository;
import io.github.droidkaigi.confsched2017.view.helper.Navigator;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;


public final class SessionFeedbackViewModel extends BaseObservable implements ViewModel {

    private static final String TAG = SessionFeedbackViewModel.class.getSimpleName();

    private final SessionsRepository sessionsRepository;

    private final SessionFeedbackRepository sessionFeedbackRepository;

    private final Navigator navigator;

    private final CompositeDisposable compositeDisposable;

    public Session session;

    private String sessionTitle;

    private int relevancy;

    private int asExpected;

    private int difficulty;

    private int knowledgeable;

    private String comment;

    private int loadingVisibility = View.GONE;

    private boolean submitButtonEnabled = true;

    private Callback callback;

    private SessionFeedback sessionFeedback;

    @Inject
    SessionFeedbackViewModel(SessionsRepository sessionsRepository,
            SessionFeedbackRepository sessionFeedbackRepository,
            Navigator navigator,
            CompositeDisposable compositeDisposable) {
        this.sessionsRepository = sessionsRepository;
        this.sessionFeedbackRepository = sessionFeedbackRepository;
        this.navigator = navigator;
        this.compositeDisposable = compositeDisposable;
    }

    public void findSession(int sessionId) {
        Disposable disposable = sessionsRepository.find(sessionId, Locale.getDefault())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        this::initSessionFeedback,
                        throwable -> Timber.tag(TAG).e(throwable, "Failed to find session.")
                );
        compositeDisposable.add(disposable);
    }

    private void initSessionFeedback(Session session) {
        this.session = session;
        this.sessionTitle = session.title;
        notifyPropertyChanged(BR.sessionTitle);

        this.sessionFeedback = sessionFeedbackRepository.findFromCache(session.id);
        if (sessionFeedback == null) {
            sessionFeedback = new SessionFeedback(session, relevancy, asExpected, difficulty, knowledgeable, comment);
        }

        setRelevancy(sessionFeedback.relevancy);
        setAsExpected(sessionFeedback.asExpected);
        setDifficulty(sessionFeedback.difficulty);
        setKnowledgeable(sessionFeedback.knowledgeable);
        setComment(sessionFeedback.comment);

        setSubmitButtonEnabled(!sessionFeedback.isSubmitted);

        if (callback != null) {
            callback.onSessionFeedbackInitialized(sessionFeedback);
        }
    }

    @Override
    public void destroy() {
        sessionFeedbackRepository.saveToCache(sessionFeedback);
        compositeDisposable.clear();
        callback = null;
    }

    @Bindable
    public String getSessionTitle() {
        return sessionTitle;
    }

    public void setSessionTitle(String sessionTitle) {
        this.sessionTitle = sessionTitle;
        notifyPropertyChanged(BR.sessionTitle);
    }

    public void onClickSubmitFeedbackButton(@SuppressWarnings("unused") View view) {
        if (sessionFeedback.isAllFilled()) {
            navigator.showConfirmDialog(R.string.session_feedback_confirm_title,
                    R.string.session_feedback_confirm_message,
                    new Navigator.ConfirmDialogListener() {
                        @Override
                        public void onClickPositiveButton() {
                            submit(sessionFeedback);
                        }

                        @Override
                        public void onClickNegativeButton() {
                            // Do nothing
                        }
                    });
        } else {
            if (callback != null) {
                callback.onErrorUnFilled();
            }
        }
    }

    private void submit(SessionFeedback sessionFeedback) {
        setLoadingVisibility(View.VISIBLE);
        setSubmitButtonEnabled(false);

        compositeDisposable.add(sessionFeedbackRepository.submit(sessionFeedback)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(success -> {
                    setLoadingVisibility(View.GONE);
                    sessionFeedback.isSubmitted = true;
                    if (callback != null) {
                        callback.onSuccessSubmit();
                    }
                }, failure -> {
                    setLoadingVisibility(View.GONE);
                    setSubmitButtonEnabled(true);
                    if (callback != null) {
                        callback.onErrorSubmit();
                    }
                }));
    }

    @Bindable
    public int getRelevancy() {
        return relevancy;
    }

    public void setRelevancy(int relevancy) {
        this.relevancy = relevancy;
        if (sessionFeedback.relevancy != relevancy) {
            sessionFeedback.relevancy = relevancy;
        }
        notifyPropertyChanged(BR.relevancy);
    }

    @Bindable
    public int getAsExpected() {
        return asExpected;
    }

    public void setAsExpected(int asExpected) {
        this.asExpected = asExpected;
        if (sessionFeedback.asExpected != asExpected) {
            sessionFeedback.asExpected = asExpected;
        }
        notifyPropertyChanged(BR.asExpected);
    }

    @Bindable
    public int getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
        if (sessionFeedback.difficulty != difficulty) {
            sessionFeedback.difficulty = difficulty;
        }
        notifyPropertyChanged(BR.difficulty);
    }

    @Bindable
    public int getKnowledgeable() {
        return knowledgeable;
    }

    public void setKnowledgeable(int knowledgeable) {
        this.knowledgeable = knowledgeable;
        if (sessionFeedback.knowledgeable != knowledgeable) {
            sessionFeedback.knowledgeable = knowledgeable;
        }
        notifyPropertyChanged(BR.knowledgeable);
    }

    @Bindable
    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
        if (comment != null && !comment.equals(sessionFeedback.comment)) {
            sessionFeedback.comment = comment;
        }
        notifyPropertyChanged(BR.comment);
    }

    @Bindable
    public int getLoadingVisibility() {
        return loadingVisibility;
    }

    public void setLoadingVisibility(int loadingVisibility) {
        this.loadingVisibility = loadingVisibility;
        notifyPropertyChanged(BR.loadingVisibility);
    }

    @Bindable
    public boolean isSubmitButtonEnabled() {
        return submitButtonEnabled;
    }

    public void setSubmitButtonEnabled(boolean submitButtonEnabled) {
        this.submitButtonEnabled = submitButtonEnabled;
        notifyPropertyChanged(BR.submitButtonEnabled);
    }

    public void setCallback(Callback callback) {
        this.callback = callback;
    }

    public interface Callback {

        void onSuccessSubmit();

        void onErrorSubmit();

        void onErrorUnFilled();

        void onSessionFeedbackInitialized(SessionFeedback sessionFeedback);
    }
}
