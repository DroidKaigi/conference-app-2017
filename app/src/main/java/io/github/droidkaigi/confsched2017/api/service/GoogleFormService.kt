package io.github.droidkaigi.confsched2017.api.service

import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface GoogleFormService {
    // TODO: change test url and add form item
    @POST("1FAIpQLSeSr-Tn3TnNum2md8l3jCZa0cnMYMXXaouHomubecdaRlGFCQ/formResponse")
    @FormUrlEncoded
    fun submitSessionFeedback(@Field("entry.684428626") id: Int): Single<Response<Void>>
}