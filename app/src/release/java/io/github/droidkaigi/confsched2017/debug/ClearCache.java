package io.github.droidkaigi.confsched2017.debug;

import com.tomoima.debot.strategy.DebotStrategy;

import android.app.Activity;
import android.support.annotation.NonNull;

import javax.inject.Inject;


public class ClearCache extends DebotStrategy {

    @Inject
    public ClearCache() {
    }

    @Override
    public void startAction(@NonNull Activity activity) {
        // Do nothing
    }
}
