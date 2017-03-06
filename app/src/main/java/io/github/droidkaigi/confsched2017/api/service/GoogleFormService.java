package io.github.droidkaigi.confsched2017.api.service;

import io.reactivex.Single;
import retrofit2.Response;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface GoogleFormService {

    @POST("e/1FAIpQLSf5NydpYm48GXqlKqbG3e0dna3bw5HJ4GUg8W1Yfe4znTWH_g/formResponse")
    @FormUrlEncoded
    Single<Response<Void>> submitSessionFeedback(
            @Field("entry.1298546024") int sessionId,
            @Field("entry.413792998") String sessionTitle,
            @Field("entry.335146475") int relevancy,
            @Field("entry.1916895481") int asExpected,
            @Field("entry.1501292277") int difficulty,
            @Field("entry.2121897737") int knowledgeable,
            @Field("entry.645604473") String comment);

}
