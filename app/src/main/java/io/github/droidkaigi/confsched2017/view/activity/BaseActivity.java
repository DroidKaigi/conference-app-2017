package io.github.droidkaigi.confsched2017.view.activity;

import com.tomoima.debot.Debot;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.MenuItem;

import io.github.droidkaigi.confsched2017.MainApplication;
import io.github.droidkaigi.confsched2017.di.ActivityComponent;
import io.github.droidkaigi.confsched2017.di.ActivityModule;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public abstract class BaseActivity extends AppCompatActivity {

    static {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }

    private ActivityComponent activityComponent;
    private Debot debot;

    @NonNull
    public ActivityComponent getComponent() {
        if (activityComponent == null) {
            MainApplication mainApplication = (MainApplication) getApplication();
            activityComponent = mainApplication.getComponent().plus(new ActivityModule(this));
        }
        return activityComponent;
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        debot = Debot.getInstance();
        debot.allowShake(getApplicationContext());
    }

    @Override
    protected void onResume() {
        super.onResume();
        debot.startSensor(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        debot.stopSensor();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_MENU) {
            debot.showDebugMenu(this);
        }
        return super.onKeyUp(keyCode, event);
    }

    final void replaceFragment(@NonNull Fragment fragment, @IdRes @LayoutRes int layoutResId) {
        final FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(layoutResId, fragment, fragment.getClass().getSimpleName());
        ft.commit();
    }

    final void initBackToolbar(Toolbar toolbar) {
        setSupportActionBar(toolbar);

        ActionBar bar = getSupportActionBar();
        if (bar != null) {
            bar.setTitle(toolbar.getTitle());
            bar.setDisplayHomeAsUpEnabled(true);
            bar.setDisplayShowHomeEnabled(true);
            bar.setDisplayShowTitleEnabled(true);
            bar.setHomeButtonEnabled(true);
        }
    }
}
