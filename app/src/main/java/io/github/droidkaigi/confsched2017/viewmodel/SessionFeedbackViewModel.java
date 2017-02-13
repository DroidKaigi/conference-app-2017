package io.github.droidkaigi.confsched2017.viewmodel;

import android.content.Context;
import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.support.annotation.NonNull;
import android.view.View;

import com.android.databinding.library.baseAdapters.BR;

import java.util.Locale;

import javax.inject.Inject;

import io.github.droidkaigi.confsched2017.model.Session;
import io.github.droidkaigi.confsched2017.model.SessionFeedback;
import io.github.droidkaigi.confsched2017.repository.feedbacks.SessionFeedbackRepository;
import io.github.droidkaigi.confsched2017.repository.sessions.SessionsRepository;
import io.reactivex.Maybe;
import io.reactivex.Single;
import retrofit2.Response;


public final class SessionFeedbackViewModel extends BaseObservable implements ViewModel {

    private final Context context;

    private final SessionsRepository sessionsRepository;

    private final SessionFeedbackRepository sessionFeedbackRepository;

    public Session session;

    private String sessionTitle;

    private Callback callback;

    private int ranking1;

    @Inject
    SessionFeedbackViewModel(Context context, SessionsRepository sessionsRepository,
            SessionFeedbackRepository sessionFeedbackRepository) {
        this.context = context;
        this.sessionsRepository = sessionsRepository;
        this.sessionFeedbackRepository = sessionFeedbackRepository;
    }

    public Maybe<Session> findSession(int sessionId) {
        return sessionsRepository.find(sessionId, Locale.getDefault())
                .doOnSuccess(this::setSession);
    }

    private void setSession(@NonNull Session session) {
        this.session = session;
        this.sessionTitle = session.title;
        notifyPropertyChanged(BR.sessionTitle);
    }

    @Override
    public void destroy() {
        this.callback = null;
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
        if (callback != null) {
            callback.onClickSubmitFeedback();
        }
    }

    public Single<Response<Void>> submitSessionFeedback(SessionFeedback sessionFeedback) {
        return sessionFeedbackRepository.submit(sessionFeedback);
    }

    @Bindable
    public int getRanking1() {
        return ranking1;
    }

    public void setRanking1(int ranking1) {
        this.ranking1 = ranking1;
    }

    public void setCallback(@NonNull Callback callback) {
        this.callback = callback;
    }

    public interface Callback {

        void onClickSubmitFeedback();

    }
}
