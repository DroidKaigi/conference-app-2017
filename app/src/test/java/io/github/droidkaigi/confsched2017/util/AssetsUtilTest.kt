package io.github.droidkaigi.confsched2017.util

import org.junit.Assert.fail
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import java.lang.reflect.InvocationTargetException

@RunWith(RobolectricTestRunner::class)
class AssetsUtilTest {
    @Test
    @Throws(Exception::class)
    fun ctor() {
        try {
            val ctor = AssetsUtil::class.java.getDeclaredConstructor()
            ctor.isAccessible = true
            ctor.newInstance()
        } catch (e: InvocationTargetException) {
            if (e.cause !is AssertionError)
                fail()
        }
    }
}
