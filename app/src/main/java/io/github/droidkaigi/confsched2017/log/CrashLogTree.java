package io.github.droidkaigi.confsched2017.log;

import com.google.firebase.crash.FirebaseCrash;

import android.util.Log;

import timber.log.Timber;

/**
 * Created by KeishinYokomaku on 2017/01/18.
 */
public class CrashLogTree extends Timber.Tree {
    @Override
    protected void log(int priority, String tag, String message, Throwable t) {
        if (priority == Log.VERBOSE || priority == Log.DEBUG) {
            FirebaseCrash.log((priority == Log.DEBUG ? "[debug] " : "[verbose] ") + tag + ": " + message);
            return;
        }
        FirebaseCrash.logcat(priority, tag, message);
        if (t == null) {
            return;
        }
        if (priority == Log.ERROR || priority == Log.WARN) {
            FirebaseCrash.report(t);
        }
    }
}
