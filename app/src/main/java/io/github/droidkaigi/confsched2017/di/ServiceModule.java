package io.github.droidkaigi.confsched2017.di;

import android.app.Service;

import dagger.Module;
import dagger.Provides;

/**
 * Created by KeishinYokomaku on 2017/02/12.
 */
@Module
public class ServiceModule {
    private final Service service;

    public ServiceModule(Service service) {
        this.service = service;
    }

    @Provides
    public Service service() {
        return service;
    }
}
