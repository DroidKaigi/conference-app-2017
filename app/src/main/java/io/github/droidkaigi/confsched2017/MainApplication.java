package io.github.droidkaigi.confsched2017;

import com.deploygate.sdk.DeployGate;
import com.squareup.leakcanary.LeakCanary;
import com.tomoima.debot.DebotConfigurator;
import com.tomoima.debot.DebotStrategyBuilder;

import android.app.Application;
import android.os.Build;
import android.support.annotation.NonNull;

import javax.inject.Inject;

import io.github.droidkaigi.confsched2017.debug.ClearCache;
import io.github.droidkaigi.confsched2017.debug.NotificationStrategy;
import io.github.droidkaigi.confsched2017.di.AndroidModule;
import io.github.droidkaigi.confsched2017.di.AppComponent;
import io.github.droidkaigi.confsched2017.di.AppModule;
import io.github.droidkaigi.confsched2017.di.DaggerAppComponent;
import io.github.droidkaigi.confsched2017.log.CrashLogTree;
import io.github.droidkaigi.confsched2017.pref.DefaultPrefs;
import io.github.droidkaigi.confsched2017.util.AppShortcutsUtil;
import io.github.droidkaigi.confsched2017.util.LocaleUtil;
import timber.log.Timber;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

public class MainApplication extends Application {

    AppComponent appComponent;

    @Inject
    ClearCache clearCache;

    @Inject
    NotificationStrategy notificationStrategy;

    @NonNull
    public AppComponent getComponent() {
        return appComponent;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        appComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .androidModule(new AndroidModule(this))
                .build();
        appComponent.inject(this);
        initCalligraphy();
        initLeakCanary();
        initAppShortcuts();

        if (!DeployGate.isInitialized()) {
            DeployGate.install(this, null, true);
        }
        Timber.plant(new CrashLogTree()); // TODO initialize Firebase before this line
        LocaleUtil.initLocale(this);

        initDebot();
    }

    private void initCalligraphy() {
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath(getString(R.string.font_noto_cjk_medium))
                .setFontAttrId(R.attr.fontPath)
                .build()
        );
    }

    private void initLeakCanary() {
        if (LeakCanary.isInAnalyzerProcess(this)) {
            return;
        }
        LeakCanary.install(this);
    }

    public void initDebot() {
        DefaultPrefs prefs = DefaultPrefs.get(this);
        String notificationTestTitle;
        if (prefs.getNotificationTestFlag()) {
            notificationTestTitle = "Notification test OFF";
        } else {
            notificationTestTitle = "Notification test ON";
        }
        DebotStrategyBuilder builder = new DebotStrategyBuilder.Builder(this)
                .registerMenu("Clear cache", clearCache)
                .registerMenu(notificationTestTitle, notificationStrategy)
                .build();
        DebotConfigurator.configureWithCustomizedMenu(this, builder.getStrategyList());
    }

    private void initAppShortcuts() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N_MR1) {
            AppShortcutsUtil.addShortcuts(this);
        }
    }
}
