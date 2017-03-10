package io.github.droidkaigi.confsched2017.util;

import android.app.Application;

import jp.wasabeef.takt.Seat;
import jp.wasabeef.takt.Takt;
import timber.log.Timber;

public class FpsMeasureUtil {

    private FpsMeasureUtil() {
    }

    public static void play(Application application) {
        Takt.stock(application)
                .seat(Seat.BOTTOM_RIGHT)
                .interval(250)
                .listener(fps -> Timber.i("heartbeat() called with: fps = [ %1$.3f ms ]", fps))
                .play();
    }

    public static void finish() {
        Takt.finish();
    }

}
