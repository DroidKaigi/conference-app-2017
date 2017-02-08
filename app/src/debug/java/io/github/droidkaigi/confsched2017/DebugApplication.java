package io.github.droidkaigi.confsched2017;

import com.facebook.stetho.Stetho;
import com.facebook.stetho.timber.StethoTree;

import timber.log.Timber;

public final class DebugApplication extends MainApplication {

    @Override
    public void onCreate() {
        super.onCreate();

        Stetho.initializeWithDefaults(this);
        Timber.plant(new StethoTree());
        Timber.plant(new Timber.DebugTree());
    }
}
