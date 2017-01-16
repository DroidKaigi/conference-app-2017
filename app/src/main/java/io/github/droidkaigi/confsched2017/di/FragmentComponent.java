package io.github.droidkaigi.confsched2017.di;

import dagger.Subcomponent;
import io.github.droidkaigi.confsched2017.di.scope.FragmentScope;

@FragmentScope
@Subcomponent(modules = FragmentModule.class)
public interface FragmentComponent {

}
