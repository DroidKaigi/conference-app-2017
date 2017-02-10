package io.github.droidkaigi.confsched2017.di;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.github.droidkaigi.confsched2017.api.RequestInterceptor;
import io.github.droidkaigi.confsched2017.api.service.DroidKaigiService;
import io.github.droidkaigi.confsched2017.api.service.GithubService;
import io.github.droidkaigi.confsched2017.api.service.GoogleFormService;
import io.github.droidkaigi.confsched2017.model.OrmaDatabase;
import io.github.droidkaigi.confsched2017.pref.DefaultPrefs;
import io.reactivex.disposables.CompositeDisposable;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by KeishinYokomaku on 2017/01/20.
 */
@Module
public class AppModule {

    static final String SHARED_PREF_NAME = "preferences";

    private Context context;

    public AppModule(Application app) {
        context = app;
    }

    @Provides
    public Context provideContext() {
        return context;
    }

    @Provides
    public SharedPreferences provideSharedPreferences(Context context) {
        return context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
    }

    @Provides
    public CompositeDisposable provideCompositeDisposable() {
        return new CompositeDisposable();
    }

    @Provides
    public DefaultPrefs provideDefaultPrefs() {
        return DefaultPrefs.get(context);
    }

    @Singleton
    @Provides
    public OrmaDatabase provideOrmaDatabase(Context context) {
        return OrmaDatabase.builder(context).build();
    }

    @Provides
    public Interceptor provideRequestInterceptor(RequestInterceptor interceptor) {
        return interceptor;
    }

    @Singleton
    @Provides
    public DroidKaigiService provideDroidKaigiService(OkHttpClient client) {
        return new Retrofit.Builder()
                .client(client)
                .baseUrl("https://droidkaigi.github.io")
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(createGson()))
                .build()
                .create(DroidKaigiService.class);
    }

    @Singleton
    @Provides
    public GithubService provideGithubService(OkHttpClient client) {
        return new Retrofit.Builder().client(client)
                .baseUrl("https://api.github.com")
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(createGson()))
                .build()
                .create(GithubService.class);
    }

    @Singleton
    @Provides
    public GoogleFormService provideGoogleFormService(OkHttpClient client) {
        return new Retrofit.Builder().client(client)
                .baseUrl("https://docs.google.com/forms/d/")
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(createGson()))
                .build()
                .create(GoogleFormService.class);
    }

    private static Gson createGson() {
        return new GsonBuilder().setDateFormat("yyyy/MM/dd HH:mm:ss").create();
    }
}
