package io.github.droidkaigi.confsched2017.util

import android.os.Build
import com.taroid.knit.should
import org.hamcrest.Matchers.instanceOf
import org.junit.Assert.assertNotNull
import org.junit.Rule
import org.junit.Test
import org.junit.rules.ExpectedException
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment
import org.robolectric.annotation.Config
import java.lang.reflect.InvocationTargetException
import java.util.*


/**
 * Created by KeishinYokomaku on 2017/01/17.
 */
@RunWith(RobolectricTestRunner::class)
@Config(sdk = intArrayOf(Build.VERSION_CODES.JELLY_BEAN_MR1, Build.VERSION_CODES.JELLY_BEAN_MR2))
class DateUtilTest {

    @Rule
    @JvmField var thrown = ExpectedException.none()

    @Test
    @Throws(Exception::class)
    fun ctor() {
        // no instance allowed even if creating a new instance via reflection
        // because this is an utility!
        thrown.expect(InvocationTargetException::class.java)
        thrown.expectCause(instanceOf(AssertionError::class.java))

        val ctor = DateUtil::class.java.getDeclaredConstructor()
        ctor.isAccessible = true
        ctor.newInstance()
    }

    @Test
    @Throws(Exception::class)
    fun getMonthDate_nonNull() {
        val month = DateUtil.getMonthDate(Date(System.currentTimeMillis()), RuntimeEnvironment.application)
        assertNotNull(month)
    }

    @Test
    @Throws(Exception::class)
    fun getHourMinute_nonNull() {
        val actual = DateUtil.getHourMinute(Date(System.currentTimeMillis()))
        assertNotNull(actual)
    }

    @Test
    @Throws(Exception::class)
    fun getLongFormatDate_nonNull() {
        val actual = DateUtil.getLongFormatDate(null)
        assertNotNull(actual)
    }

    @Test
    @Throws(Exception::class)
    fun getMinutes_etime_moreOverMinute_stime() {
        val stime = Date(1483196400000)
        val etime = Date(1483196459999)

        DateUtil.getMinutes(stime, etime).should be 0
    }

    @Test
    @Throws(Exception::class)
    fun getMinutes_returnDifferenceInMinutes() {
        val stime = Date(1483196400000)
        val etime = Date(1483196460000)

        DateUtil.getMinutes(stime, etime).should be 1
    }
}
