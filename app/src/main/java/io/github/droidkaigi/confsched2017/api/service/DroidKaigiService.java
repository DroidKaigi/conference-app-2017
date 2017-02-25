package io.github.droidkaigi.confsched2017.api.service;

import java.util.List;

import io.github.droidkaigi.confsched2017.model.Session;
import io.reactivex.Single;
import retrofit2.http.GET;

public interface DroidKaigiService {

    @GET("/sessions.json")
    Single<List<Session>> getSessionsJa();

    @GET("/en/sessions.json")
    Single<List<Session>> getSessionsEn();

}
