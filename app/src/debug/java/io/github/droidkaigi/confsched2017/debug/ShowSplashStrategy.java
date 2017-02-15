package io.github.droidkaigi.confsched2017.debug;

import com.tomoima.debot.strategy.DebotStrategy;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;

import javax.inject.Inject;

import io.github.droidkaigi.confsched2017.view.activity.SplashViewActivity;

public class ShowSplashStrategy extends DebotStrategy {

    @Inject
    ShowSplashStrategy() {
    }

    @Override
    public void startAction(@NonNull Activity activity) {
        activity.startActivity(new Intent(activity, SplashViewActivity.class));
    }
}
