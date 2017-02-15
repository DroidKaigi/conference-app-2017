package io.github.droidkaigi.confsched2017.di;

import dagger.Subcomponent;
import io.github.droidkaigi.confsched2017.di.scope.FragmentScope;
import io.github.droidkaigi.confsched2017.view.fragment.ContributorsFragment;
import io.github.droidkaigi.confsched2017.view.fragment.InformationFragment;
import io.github.droidkaigi.confsched2017.view.fragment.LicensesFragment;
import io.github.droidkaigi.confsched2017.view.fragment.MapFragment;
import io.github.droidkaigi.confsched2017.view.fragment.MySessionsFragment;
import io.github.droidkaigi.confsched2017.view.fragment.SearchFragment;
import io.github.droidkaigi.confsched2017.view.fragment.SessionDetailFragment;
import io.github.droidkaigi.confsched2017.view.fragment.SessionFeedbackFragment;
import io.github.droidkaigi.confsched2017.view.fragment.SessionsFragment;
import io.github.droidkaigi.confsched2017.view.fragment.SettingsFragment;
import io.github.droidkaigi.confsched2017.view.fragment.SponsorsFragment;

@FragmentScope
@Subcomponent(modules = FragmentModule.class)
public interface FragmentComponent {

    void inject(SessionsFragment fragment);

    void inject(MapFragment fragment);

    void inject(InformationFragment fragment);

    void inject(SettingsFragment fragment);

    void inject(SessionDetailFragment fragment);

    void inject(SponsorsFragment fragment);

    void inject(ContributorsFragment fragment);

    void inject(LicensesFragment fragment);

    void inject(SessionFeedbackFragment fragment);

    void inject(SearchFragment fragment);

    void inject(MySessionsFragment fragment);

}
