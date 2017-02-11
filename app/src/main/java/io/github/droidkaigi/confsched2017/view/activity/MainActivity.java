package io.github.droidkaigi.confsched2017.view.activity;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import io.github.droidkaigi.confsched2017.R;
import io.github.droidkaigi.confsched2017.databinding.ActivityMainBinding;
import io.github.droidkaigi.confsched2017.util.LocaleUtil;
import io.github.droidkaigi.confsched2017.view.fragment.InformationFragment;
import io.github.droidkaigi.confsched2017.view.fragment.MapFragment;
import io.github.droidkaigi.confsched2017.view.fragment.SessionsFragment;
import io.github.droidkaigi.confsched2017.view.fragment.SettingsFragment;
import io.github.droidkaigi.confsched2017.view.helper.BottomNavigationViewHelper;

public class MainActivity extends BaseActivity {

    private ActivityMainBinding binding;

    private Fragment sessionsFragment;

    private Fragment mapFragment;

    private Fragment informationFragment;

    private Fragment settingsFragment;

    public static Intent createIntent(Context context) {
        return new Intent(context, MainActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LocaleUtil.initLocale(this);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        setSupportActionBar(binding.toolbar);
        getComponent().inject(this);

        initView();
        initFragments(savedInstanceState);
    }

    private void initView() {
        BottomNavigationViewHelper.disableShiftingMode(binding.bottomNav);
        binding.bottomNav.setOnNavigationItemSelectedListener(item -> {
            binding.title.setText(item.getTitle());
            item.setChecked(true);
            switch (item.getItemId()) {
                case R.id.nav_sessions:
                    switchFragment(sessionsFragment, SessionsFragment.TAG);
                    break;
                case R.id.nav_map:
                    switchFragment(mapFragment, MapFragment.TAG);
                    break;
                case R.id.nav_information:
                    switchFragment(informationFragment, InformationFragment.TAG);
                    break;
                case R.id.nav_settings:
                    switchFragment(settingsFragment, SettingsFragment.TAG);
                    break;
            }
            return false;
        });
    }

    private void initFragments(Bundle savedInstanceState) {
        final FragmentManager manager = getSupportFragmentManager();
        sessionsFragment = manager.findFragmentByTag(SessionsFragment.TAG);
        mapFragment = manager.findFragmentByTag(MapFragment.TAG);
        informationFragment = manager.findFragmentByTag(InformationFragment.TAG);
        settingsFragment = manager.findFragmentByTag(SettingsFragment.TAG);

        if (sessionsFragment == null) {
            sessionsFragment = SessionsFragment.newInstance();
        }
        if (mapFragment == null) {
            mapFragment = MapFragment.newInstance();
        }
        if (informationFragment == null) {
            informationFragment = InformationFragment.newInstance();
        }
        if (settingsFragment == null) {
            settingsFragment = SettingsFragment.newInstance();
        }

        if (savedInstanceState == null) {
            switchFragment(sessionsFragment, SessionsFragment.TAG);
        }
    }

    private boolean switchFragment(@NonNull Fragment fragment, @NonNull String tag) {
        if (fragment.isAdded()) {
            return false;
        }

        final FragmentManager manager = getSupportFragmentManager();
        final FragmentTransaction ft = manager.beginTransaction();

        final Fragment currentFragment = manager.findFragmentById(R.id.content_view);
        if (currentFragment != null) {
            ft.detach(currentFragment);
        }
        if (fragment.isDetached()) {
            ft.attach(fragment);
        } else {
            ft.add(R.id.content_view, fragment, tag);
        }
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .commit();

        // NOTE: When this method is called by user's continuous hitting at the same time,
        // transactions are queued, so necessary to reflect commit instantly before next transaction starts.
        manager.executePendingTransactions();

        return true;
    }

    @Override
    public void onBackPressed() {
        if (switchFragment(sessionsFragment, SessionsFragment.TAG)) {
            binding.bottomNav.getMenu().findItem(R.id.nav_sessions).setChecked(true);
            binding.title.setText(getString(R.string.sessions));
            return;
        }
        super.onBackPressed();
    }
}
