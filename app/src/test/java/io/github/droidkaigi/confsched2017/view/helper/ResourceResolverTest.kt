package io.github.droidkaigi.confsched2017.view.helper

import com.google.firebase.FirebaseApp
import com.taroid.knit.should
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment

@RunWith(RobolectricTestRunner::class)
class ResourceResolverTest {
    @Test
    @Throws(Exception::class)
    fun loadJSONFromAsset_succseedsWhenFileExists() {
        val context = RuntimeEnvironment.application
        context.assets.list("json").forEach {
            val expect = context.assets.open("json/" + it)
                                       .reader(charset = Charsets.UTF_8)
                                       .use { it.readText() }
            val resolver = ResourceResolver(context)
            val actual = resolver.loadJSONFromAsset(it)

            actual.should be expect
        }
    }

    @Test
    @Throws(Exception::class)
    fun loadJSONFromAsset_failsWhenFileNotExists() {
        val context = RuntimeEnvironment.application
        FirebaseApp.initializeApp(context)
        ResourceResolver(context).loadJSONFromAsset("NonExistsFile.json").should be null
    }
}
