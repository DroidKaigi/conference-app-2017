package io.github.droidkaigi.confsched2017.api;

import android.support.annotation.NonNull;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.github.droidkaigi.confsched2017.api.service.DroidKaigiService;
import io.github.droidkaigi.confsched2017.api.service.GithubService;
import io.github.droidkaigi.confsched2017.model.Contributor;
import io.github.droidkaigi.confsched2017.model.Session;
import io.reactivex.Single;

@Singleton
public class DroidKaigiClient {

    private final DroidKaigiService droidKaigiService;

    private final GithubService githubService;

    private static final int INCLUDE_ANONYMOUS = 1;

    private static final int MAX_PER_PAGE = 100;

    @Inject
    public DroidKaigiClient(DroidKaigiService droidKaigiService, GithubService githubService) {
        this.droidKaigiService = droidKaigiService;
        this.githubService = githubService;
    }

    public Single<List<Session>> getSessions(@NonNull String languageId) {
        // TODO
        switch (languageId) {
            default:
                return droidKaigiService.getSessionsJa();
        }
    }

    public Single<List<Contributor>> getContributors() {
        return githubService.getContributors("DroidKaigi", "conference-app-2017", INCLUDE_ANONYMOUS, MAX_PER_PAGE);
    }
}
