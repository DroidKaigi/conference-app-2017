package io.github.droidkaigi.confsched2017;

import com.facebook.stetho.Stetho;
import com.facebook.stetho.timber.StethoTree;

import javax.inject.Inject;

import io.github.droidkaigi.confsched2017.repository.sessions.SessionsRepository;
import timber.log.Timber;

public final class DebugApplication extends MainApplication {

    @Inject
    SessionsRepository repository;

    @Override
    public void onCreate() {
        super.onCreate();

        Stetho.initializeWithDefaults(this);
        Timber.plant(new StethoTree());
        Timber.plant(new Timber.DebugTree());
    }
}
