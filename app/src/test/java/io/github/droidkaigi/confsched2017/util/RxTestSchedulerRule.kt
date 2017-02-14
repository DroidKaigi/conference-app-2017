package io.github.droidkaigi.confsched2017.util

import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.plugins.RxJavaPlugins
import io.reactivex.schedulers.TestScheduler
import org.junit.rules.ExternalResource

object RxTestSchedulerRule : ExternalResource() {
    val testScheduler: TestScheduler = TestScheduler()

    override fun before() {
        RxJavaPlugins.reset()
        RxJavaPlugins.setInitIoSchedulerHandler { testScheduler }

        RxAndroidPlugins.reset()
        RxAndroidPlugins.setInitMainThreadSchedulerHandler { testScheduler }
    }

    override fun after() {
        RxJavaPlugins.reset()
        RxAndroidPlugins.reset()
    }
}
