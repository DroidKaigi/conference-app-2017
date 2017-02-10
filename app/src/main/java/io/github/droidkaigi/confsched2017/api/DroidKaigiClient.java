package io.github.droidkaigi.confsched2017.api;

import android.support.annotation.NonNull;

import java.util.List;
import java.util.Locale;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.github.droidkaigi.confsched2017.api.service.DroidKaigiService;
import io.github.droidkaigi.confsched2017.api.service.GithubService;
import io.github.droidkaigi.confsched2017.api.service.GoogleFormService;
import io.github.droidkaigi.confsched2017.model.Contributor;
import io.github.droidkaigi.confsched2017.model.Session;
import io.github.droidkaigi.confsched2017.model.SessionFeedback;
import io.reactivex.Single;
import retrofit2.Response;

@Singleton
public class DroidKaigiClient {

    private final DroidKaigiService droidKaigiService;

    private final GithubService githubService;

    private final GoogleFormService googleFormService;

    private static final int INCLUDE_ANONYMOUS = 1;

    private static final int MAX_PER_PAGE = 100;

    @Inject
    public DroidKaigiClient(DroidKaigiService droidKaigiService, GithubService githubService, GoogleFormService googleFormService) {
        this.droidKaigiService = droidKaigiService;
        this.githubService = githubService;
        this.googleFormService = googleFormService;
    }

    public Single<List<Session>> getSessions(@NonNull Locale locale) {
        if (locale == Locale.JAPANESE) {
            return droidKaigiService.getSessionsJa();
        } else {
            return droidKaigiService.getSessionsEn();
        }
    }

    public Single<List<Contributor>> getContributors() {
        return githubService.getContributors("DroidKaigi", "conference-app-2017", INCLUDE_ANONYMOUS, MAX_PER_PAGE);
    }

    public Single<Response<Void>>submitSessionFeedback(SessionFeedback sessionFeedback){
        return googleFormService.submitSessionFeedback(sessionFeedback.sessionId);
    }
}
