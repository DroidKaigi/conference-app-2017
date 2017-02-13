package io.github.droidkaigi.confsched2017.di;

import android.app.Service;
import android.view.WindowManager;

import dagger.Module;
import dagger.Provides;
import io.github.droidkaigi.confsched2017.service.helper.OverlayViewManager;

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

    @Provides
    public OverlayViewManager overlayViewManager(WindowManager windowManager) {
        return new OverlayViewManager(service, windowManager);
    }
}
