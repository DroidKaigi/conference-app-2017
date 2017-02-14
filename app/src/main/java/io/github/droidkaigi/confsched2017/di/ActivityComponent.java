package io.github.droidkaigi.confsched2017.di;


import android.app.Activity;
import dagger.Subcomponent;
import io.github.droidkaigi.confsched2017.di.scope.ActivityScope;
import io.github.droidkaigi.confsched2017.view.activity.*;

@ActivityScope
@Subcomponent(modules = ActivityModule.class)
public interface ActivityComponent{

    void inject(BaseActivity activity);

    void inject(ContributorsActivity activity);

    FragmentComponent plus(FragmentModule module);
}
