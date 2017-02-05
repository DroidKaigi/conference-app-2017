package io.github.droidkaigi.confsched2017.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import android.support.annotation.NonNull;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.github.droidkaigi.confsched2017.model.Session;
import io.reactivex.Single;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;

@Singleton
public class DroidKaigiClient {

    private final DroidKaigiService droidKaigiService;

    @Inject
    public DroidKaigiClient(OkHttpClient client) {
        Retrofit droidkaigiRetrofit = new Retrofit.Builder()
                .client(client)
                .baseUrl("https://droidkaigi.github.io")
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(createGson()))
                .build();
        droidKaigiService = droidkaigiRetrofit.create(DroidKaigiService.class);
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

    interface DroidKaigiService {

        @GET("/2017/sessions.json")
        Single<List<Session>> getSessionsJa();
    }
}
