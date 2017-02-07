package io.github.droidkaigi.confsched2017;

import org.junit.Test;
import org.junit.runner.RunWith;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import static org.junit.Assert.assertEquals;

/**
 * Instrumentation test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {

    @Test
    public void useAppContext() throws Exception {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();

        final StringBuilder expectedPackageName = new StringBuilder("io.github.droidkaigi.confsched2017");
        if (BuildConfig.FLAVOR.equals("develop")) {
            expectedPackageName.append(".develop");
        }
        if (BuildConfig.DEBUG) {
            expectedPackageName.append(".debug");
        }

        assertEquals(expectedPackageName.toString(), appContext.getPackageName());
    }
}
