package io.github.droidkaigi.confsched2017.util

import android.os.Build
import com.taroid.knit.should
import org.junit.Assert.assertNotNull
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment
import org.robolectric.annotation.Config
import java.util.*

/**
 * Created by KeishinYokomaku on 2017/01/17.
 */
@RunWith(RobolectricTestRunner::class)
@Config(sdk = intArrayOf(Build.VERSION_CODES.JELLY_BEAN_MR1, Build.VERSION_CODES.JELLY_BEAN_MR2))
class DateUtilTest {

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
