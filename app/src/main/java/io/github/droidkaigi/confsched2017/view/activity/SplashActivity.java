package io.github.droidkaigi.confsched2017.view.activity;

import android.databinding.DataBindingUtil;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

import java.util.Locale;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import io.github.droidkaigi.confsched2017.R;
import io.github.droidkaigi.confsched2017.repository.sessions.MySessionsRepository;
import io.github.droidkaigi.confsched2017.repository.sessions.SessionsRepository;
import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import jp.wasabeef.takt.Seat;
import jp.wasabeef.takt.Takt;
import timber.log.Timber;

public class SplashActivity extends BaseActivity {

    private static final String TAG = SplashActivity.class.getSimpleName();

    private static final int MINIMUM_LOADING_TIME = 1500;

    @Inject
    CompositeDisposable compositeDisposable;

    @Inject
    SessionsRepository sessionsRepository;

    @Inject
    MySessionsRepository mySessionsRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getComponent().inject(this);
        DataBindingUtil.setContentView(this, R.layout.activity_splash);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            findViewById(android.R.id.content).setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        loadSessionsForCache();

        // Starting new Activity normally will not destroy this Activity, so set this up in start/stop cycle
        Takt.stock(getApplication())
                .seat(Seat.BOTTOM_RIGHT)
                .interval(250)
                .listener(fps -> Timber.i("heartbeat() called with: fps = [ %1$.3f ms ]", fps))
                .play();
    }

    @Override
    protected void onStop() {
        super.onStop();
        compositeDisposable.dispose();

        // Stop tracking the frame rate.
        Takt.finish();
    }

    private void loadSessionsForCache() {
        Disposable disposable = Single.zip(sessionsRepository.findAll(Locale.getDefault()),
                mySessionsRepository.findAll(),
                Single.timer(MINIMUM_LOADING_TIME, TimeUnit.MILLISECONDS),
                (sessions, mySessions, obj) -> Observable.empty())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> {
                    if (isFinishing()) return;
                    startActivity(MainActivity.createIntent(SplashActivity.this));
                    SplashActivity.this.finish();
                })
                .subscribe(observable -> Timber.tag(TAG).d("Succeeded in loading sessions."),
                        throwable -> Timber.tag(TAG).e(throwable, "Failed to load sessions."));
        compositeDisposable.add(disposable);
    }

}
