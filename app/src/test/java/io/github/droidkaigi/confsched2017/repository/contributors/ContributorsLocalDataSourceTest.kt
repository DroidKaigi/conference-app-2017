package io.github.droidkaigi.confsched2017.repository.contributors

import com.taroid.knit.should
import io.github.droidkaigi.confsched2017.model.Contributor
import io.github.droidkaigi.confsched2017.model.OrmaDatabase
import io.github.droidkaigi.confsched2017.util.RxTestSchedulerRule
import org.junit.Before
import org.junit.ClassRule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment
import java.util.concurrent.TimeUnit

@RunWith(RobolectricTestRunner::class)
class ContributorsLocalDataSourceTest {

    companion object {
        @ClassRule
        @JvmField
        val schedulerRule = RxTestSchedulerRule
    }

    private lateinit var ormaDatabase: OrmaDatabase

    @Before
    fun setUp() {
        ormaDatabase = OrmaDatabase.builder(RuntimeEnvironment.application).name(null).build()
    }

    @Test
    fun findAllWhenEmpty() {
        ContributorsLocalDataSource(ormaDatabase).findAll().test().run {
            await(10, TimeUnit.SECONDS).should be true
            assertNoErrors()
            assertValue(listOf())
        }
    }

    @Test
    fun findAllWhenNotEmpty() {
        ormaDatabase.apply {
            insertIntoContributor(Contributor().apply {
                name = "Alice"
            })
        }.let(::ContributorsLocalDataSource).findAll().test().run {
            await(10, TimeUnit.SECONDS).should be true
            assertNoErrors()
            assertValueCount(1)
            values()[0].size.should be 1
            values()[0][0].name.should be "Alice"
        }
    }

    @Test
    fun updateAllAsyncAsInsert() {
        ContributorsLocalDataSource(ormaDatabase).updateAllAsync(listOf(
                Contributor().apply {
                    name = "Alice"
                },
                Contributor().apply {
                    name = "Bob"
                }))
        schedulerRule.testScheduler.triggerActions()

        ormaDatabase.selectFromContributor().toList().run {
            size.should be 2
            this[1].name.should be "Bob"
        }
    }

    @Test
    fun updateAllAsyncAsUpdate() {
        ormaDatabase.apply {
            insertIntoContributor(Contributor().apply {
                name = "Alice"
                contributions = 10
            })
        }.let(::ContributorsLocalDataSource).updateAllAsync(listOf(
                Contributor().apply {
                    name = "Alice"
                    contributions = 100
                }
        ))
        schedulerRule.testScheduler.triggerActions()

        ormaDatabase.selectFromContributor().toList().run {
            size.should be 1
            this[0].contributions.should be 100
        }
    }

}
