package io.github.droidkaigi.confsched2017.debug;

import com.tomoima.debot.strategy.DebotStrategy;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.widget.Toast;

import javax.inject.Inject;

import io.github.droidkaigi.confsched2017.repository.sessions.SessionsRepository;
import io.reactivex.Completable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;


public class ClearCache extends DebotStrategy {

    SessionsRepository sessionsRepository;
    @Inject
    public ClearCache(SessionsRepository sessionsRepository) {
        this.sessionsRepository = sessionsRepository;
    }

    @Override
    public void startAction(@NonNull Activity activity) {
        Completable.fromAction(() -> sessionsRepository.deleteAll())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(() -> Toast.makeText(activity.getApplicationContext(), "Cache Cleared", Toast.LENGTH_LONG).show());
    }
}
