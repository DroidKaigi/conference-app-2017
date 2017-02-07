package io.github.droidkaigi.confsched2017.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import android.support.annotation.NonNull;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.github.droidkaigi.confsched2017.model.Contributor;
import io.github.droidkaigi.confsched2017.model.Session;
import io.reactivex.Single;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

@Singleton
public class DroidKaigiClient {

    private final DroidKaigiService droidKaigiService;

    private final GithubService githubService;

    private static final int INCLUDE_ANONYMOUS = 1;

    private static final int MAX_PER_PAGE = 100;

    @Inject
    public DroidKaigiClient(OkHttpClient client) {
        Retrofit droidkaigiRetrofit = new Retrofit.Builder()
                .client(client)
                .baseUrl("https://droidkaigi.github.io")
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(createGson()))
                .build();
        droidKaigiService = droidkaigiRetrofit.create(DroidKaigiService.class);

        Retrofit githubRetrofit = new Retrofit.Builder().client(client)
                .baseUrl("https://api.github.com")
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(createGson()))
                .build();
        githubService = githubRetrofit.create(GithubService.class);
    }

    private static Gson createGson() {
        return new GsonBuilder().setDateFormat("yyyy/MM/dd HH:mm:ss").create();
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

    interface DroidKaigiService {

        @GET("/2017/sessions.json")
        Single<List<Session>> getSessionsJa();
    }

    interface GithubService {

        @GET("/repos/{owner}/{repo}/contributors")
        Single<List<Contributor>> getContributors(@Path("owner") String owner,
                @Path("repo") String repo, @Query("anon") int anon, @Query("per_page") int perPage);
    }
}
