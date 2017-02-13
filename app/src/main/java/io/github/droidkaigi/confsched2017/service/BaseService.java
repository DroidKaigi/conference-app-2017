package io.github.droidkaigi.confsched2017.service;

import android.app.Service;
import android.support.annotation.IntDef;
import android.support.annotation.NonNull;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import io.github.droidkaigi.confsched2017.MainApplication;
import io.github.droidkaigi.confsched2017.di.ServiceComponent;
import io.github.droidkaigi.confsched2017.di.ServiceModule;

/**
 * Created by KeishinYokomaku on 2017/02/12.
 */

public abstract class BaseService extends Service {
    private ServiceComponent serviceComponent;

    @NonNull
    public ServiceComponent getComponent() {
        if (serviceComponent == null) {
            MainApplication mainApplication = (MainApplication) getApplication();
            serviceComponent = mainApplication.getComponent().plus(new ServiceModule(this));
        }
        return serviceComponent;
    }

    @Retention(RetentionPolicy.SOURCE)
    @IntDef(value = {Service.START_FLAG_REDELIVERY, Service.START_FLAG_RETRY}, flag = true)
    /* package */ @interface ServiceFlags {}
}
