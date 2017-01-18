package io.github.droidkaigi.confsched2017.di;

import dagger.Subcomponent;
import io.github.droidkaigi.confsched2017.di.scope.FragmentScope;
import io.github.droidkaigi.confsched2017.view.fragment.InformationFragment;
import io.github.droidkaigi.confsched2017.view.fragment.MapFragment;
import io.github.droidkaigi.confsched2017.view.fragment.SessionsFragment;
import io.github.droidkaigi.confsched2017.view.fragment.SettingsFragment;

@FragmentScope
@Subcomponent(modules = FragmentModule.class)
public interface FragmentComponent {

    void inject(SessionsFragment fragment);

    void inject(MapFragment fragment);

    void inject(InformationFragment fragment);

    void inject(SettingsFragment fragment);

}
