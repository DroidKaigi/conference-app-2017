package io.github.droidkaigi.confsched2017;

import android.app.Application;
import android.support.annotation.NonNull;

import io.github.droidkaigi.confsched2017.di.AppComponent;
import io.github.droidkaigi.confsched2017.di.AppModule;
import io.github.droidkaigi.confsched2017.di.DaggerAppComponent;

public class MainApplication extends Application {

    AppComponent appComponent;

    @NonNull
    public AppComponent getComponent() {
        return appComponent;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        appComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .build();
    }
}
