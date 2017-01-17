package io.github.droidkaigi.confsched2017;

import com.facebook.stetho.Stetho;

public final class DebugApplication extends MainApplication {

    @Override
    public void onCreate() {
        super.onCreate();

        Stetho.initializeWithDefaults(this);
    }
}
