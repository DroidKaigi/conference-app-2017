package io.github.droidkaigi.confsched2017.api.service

import io.github.droidkaigi.confsched2017.model.Session
import io.reactivex.Single
import retrofit2.http.GET

interface DroidKaigiService {

    @GET("/2017/sessions.json")
    fun getSessionsJa(): Single<List<Session>>
}
