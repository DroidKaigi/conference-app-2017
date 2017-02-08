package io.github.droidkaigi.confsched2017.repository.contributors

import com.sys1yagi.kmockito.invoked
import com.sys1yagi.kmockito.mock
import com.sys1yagi.kmockito.verify
import io.github.droidkaigi.confsched2017.model.Contributor
import io.reactivex.Single
import org.junit.Test
import org.mockito.Mockito

class ContributorsRepositoryTest {

    private val localDataSource = mock<ContributorsLocalDataSource>()
    private val remoteDataSource = mock<ContributorsRemoteDataSource>()

    private val repository = ContributorsRepository(localDataSource, remoteDataSource)

    @Test
    @Throws(Exception::class)
    fun findAllFromEmptyRepository() {
        localDataSource.findAll().invoked.thenReturn(Single.just(listOf()))
        remoteDataSource.findAll().invoked.thenReturn(Single.just(listOf()))
        repository.findAll().test().assertOf { check ->
            check.assertNoErrors()
            check.assertValue(listOf())
        }
    }

    @Test
    @Throws(Exception::class)
    fun updateLocalWhenRemoteReturns() {
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
        localDataSource.findAll().invoked.thenReturn(Single.just(listOf()))
        remoteDataSource.findAll().invoked.thenReturn(Single.just(contributors))
        repository.findAll().test().assertOf { check ->
            check.assertNoErrors()
            localDataSource.verify(Mockito.times(1)).updateAllAsync(contributors)
        }
    }

}