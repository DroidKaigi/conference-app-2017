package io.github.droidkaigi.confsched2017.util;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Date;

import io.github.droidkaigi.confsched2017.R;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

/**
 * Created by KeishinYokomaku on 2017/01/17.
 */
@RunWith(RobolectricTestRunner.class)
public class DateUtilTest {
    @Test
    public void ctor() throws Exception {
        // no instance allowed even if creating a new instance via reflection
        // because this is an utility!
        try {
            Constructor<DateUtil> ctor = DateUtil.class.getDeclaredConstructor();
            ctor.setAccessible(true);
            ctor.newInstance();
        } catch (InvocationTargetException e) {
            if (!(e.getCause() instanceof AssertionError))
                fail();
        }
    }

    @Test
    public void getMonthDate_nonNull() throws Exception {
        String month = DateUtil.getMonthDate(new Date(System.currentTimeMillis()), RuntimeEnvironment.application);
        assertNotNull(month);
    }
}
