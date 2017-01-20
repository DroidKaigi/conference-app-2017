package io.github.droidkaigi.confsched2017.di;


import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {AppModule.class, AndroidModule.class})
public interface AppComponent {

    ActivityComponent plus(ActivityModule module);
}
