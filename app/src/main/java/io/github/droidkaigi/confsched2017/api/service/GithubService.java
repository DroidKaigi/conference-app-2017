package io.github.droidkaigi.confsched2017.api.service;

import android.support.annotation.NonNull;

import java.util.List;

import io.github.droidkaigi.confsched2017.model.Contributor;
import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface GithubService {

    @GET("/repos/{owner}/{repo}/contributors")
    Single<List<Contributor>> getContributors(@Path("owner") @NonNull String owner, @Path("repo") @NonNull String repo,
                        @Query("anon") int anon, @Query("per_page") int perPage);

}
