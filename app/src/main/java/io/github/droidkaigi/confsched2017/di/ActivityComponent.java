package io.github.droidkaigi.confsched2017.di;


import dagger.Subcomponent;
import io.github.droidkaigi.confsched2017.di.scope.ActivityScope;
import io.github.droidkaigi.confsched2017.view.activity.MainActivity;
import io.github.droidkaigi.confsched2017.view.activity.SessionDetailActivity;
import io.github.droidkaigi.confsched2017.view.activity.SponsorsActivity;

@ActivityScope
@Subcomponent(modules = ActivityModule.class)
public interface ActivityComponent {

    void inject(MainActivity activity);

    void inject(SessionDetailActivity activity);

    void inject(SponsorsActivity activity);

    FragmentComponent plus(FragmentModule module);
}
