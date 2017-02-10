package io.github.droidkaigi.confsched2017.util

import io.github.droidkaigi.confsched2017.BuildConfig
import io.github.droidkaigi.confsched2017.pref.DefaultPrefs
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment
import java.util.*

@RunWith(RobolectricTestRunner::class)
class LocaleUtilTest {

    val testTimeZone = TimeZone.getTimeZone("Pacific/Guam") // UTC+10

    val pref = DefaultPrefs.get(RuntimeEnvironment.application.applicationContext)

    @Test
    @Throws(Exception::class)
    fun getDisplayTimeZone() {
        // return conference timezone.
        run {
            pref.putShowLocalTimeFlag(false)
            TimeZone.setDefault(testTimeZone)

            val timeZone = LocaleUtil.getDisplayTimeZone(RuntimeEnvironment.application)
            assertThat(timeZone).isEqualTo(TimeZone.getTimeZone(BuildConfig.CONFERENCE_TIMEZONE))
        }

        // return local timezone.
        run {
            pref.putShowLocalTimeFlag(true)
            TimeZone.setDefault(testTimeZone)

            val timeZone = LocaleUtil.getDisplayTimeZone(RuntimeEnvironment.application)
            assertThat(timeZone).isEqualTo(testTimeZone)
        }
    }

    @Test
    @Throws(Exception::class)
    fun getDisplayDateConferenceLocalTime() {
        pref.putShowLocalTimeFlag(false)

        val time = 1489021200000
        val inputDate = Date(time)
        TimeZone.setDefault(testTimeZone)

        val resultDate = LocaleUtil.getDisplayDate(inputDate, RuntimeEnvironment.application)

        assertThat(inputDate.time).isEqualTo(time)
        assertThat(resultDate.time).isEqualTo(time)
    }

    @Test
    @Throws(Exception::class)
    fun getDisplayDateLocalTime() {
        pref.putShowLocalTimeFlag(true)

        val time = 1489021200000
        val inputDate = Date(time)
        TimeZone.setDefault(testTimeZone)

        val resultDate = LocaleUtil.getDisplayDate(inputDate, RuntimeEnvironment.application)

        assertThat(inputDate.time).isEqualTo(time)
        assertThat(resultDate.time).isEqualTo(time - (60L * 60L * 1000L))
    }
}
