package io.github.droidkaigi.confsched2017.repository.contributors

import io.github.droidkaigi.confsched2017.model.Contributor
import io.reactivex.Single
import org.junit.Assert.assertEquals
import org.junit.Test

class ContributorsRepositoryTest {

    @Test
    @Throws(Exception::class)
    fun findAllFromEmptyRepository() {
        val localRepository = object : ContributorsReadWriteDataSource {
            override fun findAll(): Single<List<Contributor>> = Single.just(listOf())

            override fun updateAllAsync(contributors: List<Contributor>?) = Unit
        }
        val remoteRepository = object : ContributorsReadDataSource {
            override fun findAll(): Single<List<Contributor>> = Single.just(listOf())
        }

        val repository = ContributorsRepository(localRepository, remoteRepository)
        repository.findAll().test().assertOf { check ->
            check.assertNoErrors()
            check.assertValue(listOf())
        }
    }

    @Test
    @Throws(Exception::class)
    fun updateLocalWhenRemoteReturns() {
        class StubLocalRepository: ContributorsReadWriteDataSource {
            var contributors: List<Contributor>? = listOf<Contributor>()
            override fun findAll(): Single<List<Contributor>> = Single.just(listOf())

            override fun updateAllAsync(contributors: List<Contributor>?) {
                this.contributors = contributors
            }
        }
        val contributors = listOf(
                Contributor().apply {
                    name = "Alice"
                },
                Contributor().apply {
                    name = "Bob"
                },
                Contributor().apply {
                    name = "Charlie"
                }
        )
        val localRepository = StubLocalRepository()
        val remoteRepository = object : ContributorsReadDataSource {
            override fun findAll(): Single<List<Contributor>> = Single.just(contributors)
        }

        val repository = ContributorsRepository(localRepository, remoteRepository)
        repository.findAll().test().assertOf { check ->
            check.assertNoErrors()
            assertEquals(localRepository.contributors, contributors)
        }
    }

}