package io.github.droidkaigi.confsched2017.di;


import javax.inject.Singleton;

import dagger.Component;
import io.github.droidkaigi.confsched2017.MainApplication;

@Singleton
@Component(modules = {AppModule.class, AndroidModule.class, HttpClientModule.class})
public interface AppComponent {

    void inject(MainApplication application);
    ActivityComponent plus(ActivityModule module);
    ServiceComponent plus(ServiceModule module);
}
