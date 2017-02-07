package io.github.droidkaigi.confsched2017.repository.sessions

import com.sys1yagi.kmockito.invoked
import com.sys1yagi.kmockito.mock
import com.sys1yagi.kmockito.verify
import com.taroid.knit.should
import io.github.droidkaigi.confsched2017.model.*
import io.github.droidkaigi.confsched2017.util.DummyCreator
import io.reactivex.Completable
import io.reactivex.Single
import org.junit.Test
import org.mockito.Mockito

class MySessionsRepositoryTest {

    private val localDataSource = mock<MySessionsLocalDataSource>()
    private val repository = MySessionsRepository(localDataSource)

    @Test
    @Throws(Exception::class)
    fun findAll() {
        val expected = Array(10) { i -> DummyCreator.newMySession(i) }.toList()
        localDataSource.findAll().invoked.thenReturn(Single.just(expected))

        repository.findAll().test().run {
            assertNoErrors()
            assertResult(expected)
            assertComplete()
            localDataSource.verify(Mockito.times(1)).findAll()
        }

        // check if found sessions are cached
        repository.findAll().test().run {
            assertNoErrors()
            assertResult(expected)
            assertComplete()
            localDataSource.verify(Mockito.times(1)).findAll()
        }
    }

    @Test
    @Throws(Exception::class)
    fun save() {
        val session = DummyCreator.newSession(1)
        localDataSource.save(session).invoked.thenReturn(Completable.complete())
        repository.save(session).test().run {
            assertNoErrors()
            assertComplete()
        }

         // check if session is cached
        repository.findAll().test().run {
            assertNoErrors()
            assertResult(listOf(MySession(session)))
            assertComplete()
            localDataSource.verify(Mockito.never()).findAll()
        }
    }

    @Test
    @Throws(Exception::class)
    fun delete() {
        val session1 = DummyCreator.newSession(1)
        val session2 = DummyCreator.newSession(2)

        // ready caches
        repository.save(session1)
        repository.save(session2)

        localDataSource.delete(session1).invoked.thenReturn(Single.just(1))
        repository.delete(session1).test().run {
            assertNoErrors()
            assertResult(1)
            assertComplete()
        }

        // check if cached session1 is deleted
        repository.findAll().test().run {
            assertNoErrors()
            assertResult(listOf(MySession(session2)))
            assertComplete()
        }
    }

    @Test
    @Throws(Exception::class)
    fun isExist() {
        localDataSource.isExist(1).invoked.thenReturn(false)
        repository.isExist(1).should be false

        localDataSource.isExist(1).invoked.thenReturn(true)
        repository.isExist(1).should be true
    }

}
