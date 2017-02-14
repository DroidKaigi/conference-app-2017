package io.github.droidkaigi.confsched2017.api.service;

import java.util.List;

import io.github.droidkaigi.confsched2017.model.Session;
import io.reactivex.Single;
import retrofit2.http.GET;

public interface DroidKaigiService {

    @GET("/2017/sessions.json")
    Single<List<Session>> getSessionsJa();

    @GET("/2017/en/sessions.json")
    Single<List<Session>> getSessionsEn();

}
