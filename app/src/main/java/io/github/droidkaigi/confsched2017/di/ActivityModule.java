package io.github.droidkaigi.confsched2017.di;

import android.support.v7.app.AppCompatActivity;

import dagger.Module;
import dagger.Provides;
import io.github.droidkaigi.confsched2017.view.activity.BaseActivity;
import io.github.droidkaigi.confsched2017.view.helper.Navigator;

@Module
public class ActivityModule {

    final BaseActivity activity;

    public ActivityModule(BaseActivity activity) {
        this.activity = activity;
    }

    @Provides
    public AppCompatActivity activity() {
        return activity;
    }

    @Provides
    public Navigator navigator() {
        return activity;
    }

}
