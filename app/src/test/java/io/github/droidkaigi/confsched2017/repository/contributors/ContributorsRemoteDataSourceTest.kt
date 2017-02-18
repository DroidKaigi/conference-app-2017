package io.github.droidkaigi.confsched2017.repository.contributors

import com.sys1yagi.kmockito.invoked
import com.sys1yagi.kmockito.mock
import com.taroid.knit.should
import io.github.droidkaigi.confsched2017.api.DroidKaigiClient
import io.github.droidkaigi.confsched2017.model.Contributor
import io.github.droidkaigi.confsched2017.util.RxTestSchedulerRule
import io.reactivex.Single
import org.junit.ClassRule
import org.junit.Test

class ContributorsRemoteDataSourceTest {

    companion object {
        @ClassRule
        @JvmField
        val schedulerRule = RxTestSchedulerRule
    }

    private val client = mock<DroidKaigiClient>()

    @Test
    fun findAll() {
        client.contributors.invoked.thenReturn(Single.just(listOf(
                Contributor().apply {
                    name = "Alice"
                })))

        ContributorsRemoteDataSource(client).findAll().test().run {
            schedulerRule.testScheduler.triggerActions()
            assertNoErrors()
            assertValueCount(1)
            values()[0].size.should be 1
            values()[0][0].name.should be "Alice"
        }
    }
}
