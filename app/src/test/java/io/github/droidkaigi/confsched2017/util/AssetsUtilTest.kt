package io.github.droidkaigi.confsched2017.util

import com.google.firebase.FirebaseApp
import com.taroid.knit.should
import org.junit.Assert.fail
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment
import java.lang.reflect.InvocationTargetException

@RunWith(RobolectricTestRunner::class)
class AssetsUtilTest {
    @Test
    @Throws(Exception::class)
    fun loadJSONFromAsset_succseedsWhenFileExists() {
        val context = RuntimeEnvironment.application
        context.assets.list("json").forEach {
            val expect = context.assets.open("json/" + it)
                                       .reader(charset = Charsets.UTF_8)
                                       .use { it.readText() }
            val actual = AssetsUtil.loadJSONFromAsset(context, it)

            actual.should be expect
        }
    }

    @Test
    @Throws(Exception::class)
    fun loadJSONFromAsset_failsWhenFileNotExists() {
        FirebaseApp.initializeApp(RuntimeEnvironment.application)
        AssetsUtil.loadJSONFromAsset(RuntimeEnvironment.application, "NonExistsFile.json").should be null
    }
}
