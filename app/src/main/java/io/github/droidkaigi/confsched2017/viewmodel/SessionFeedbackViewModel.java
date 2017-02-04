package io.github.droidkaigi.confsched2017.viewmodel;

import com.android.databinding.library.baseAdapters.BR;

import android.content.Context;
import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.support.annotation.NonNull;

import java.util.Locale;

import javax.inject.Inject;

import io.github.droidkaigi.confsched2017.model.Session;
import io.github.droidkaigi.confsched2017.repository.sessions.SessionsRepository;
import io.reactivex.Maybe;

public final class SessionFeedbackViewModel extends BaseObservable implements ViewModel {

    private final Context context;

    private final SessionsRepository sessionsRepository;

    public Session session;

    private String sessionTitle;

    @Inject
    SessionFeedbackViewModel(Context context, SessionsRepository sessionsRepository) {
        this.context = context;
        this.sessionsRepository = sessionsRepository;
    }

    public Maybe<Session> findSession(int sessionId) {
        final String languageId = Locale.getDefault().getLanguage().toLowerCase();
        return sessionsRepository.find(sessionId, languageId)
                .map(session -> {
                    setSession(session);
                    return session;
                });
    }

    private void setSession(@NonNull Session session) {
        this.session = session;
        this.sessionTitle = session.title;
        notifyPropertyChanged(BR.sessionTitle);
    }

    @Override
    public void destroy() {
        //
    }

    @Bindable
    public String getSessionTitle() {
        return sessionTitle;
    }

    public void setSessionTitle(String sessionTitle) {
        this.sessionTitle = sessionTitle;
        notifyPropertyChanged(BR.sessionTitle);
    }
}
