package io.github.droidkaigi.confsched2017.repository.sessions

import com.sys1yagi.kmockito.invoked
import com.sys1yagi.kmockito.mock
import com.sys1yagi.kmockito.verify
import com.taroid.knit.should
import io.github.droidkaigi.confsched2017.model.*
import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.observers.TestObserver
import org.junit.Test
import org.mockito.Mockito
import java.sql.Date

class MySessionsRepositoryTest {

    private val localDataSource = mock<MySessionsLocalDataSource>()
    private val repository = MySessionsRepository(localDataSource)

    @Test
    @Throws(Exception::class)
    fun findAll() {
        val expected = Array(10) { i -> newDummyMySession(i) }.toList()
        localDataSource.findAll().invoked.thenReturn(Single.just(expected))

        with(TestObserver<List<MySession>>()) {
            repository.findAll().subscribe(this)
            localDataSource.verify(Mockito.times(1)).findAll()
            assertNoErrors()
            assertResult(expected)
            assertComplete()
        }

        // check if found sessions are cached
        with(TestObserver<List<MySession>>()) {
            repository.findAll().subscribe(this)
            assertNoErrors()
            assertResult(expected)
            assertComplete()
            localDataSource.verify(Mockito.times(1)).findAll()
        }
    }

    @Test
    @Throws(Exception::class)
    fun save() {
        val session = newDummySession(1)
        localDataSource.save(session).invoked.thenReturn(Completable.complete())
        with(TestObserver<Any>()) {
            repository.save(session).subscribe(this)
            assertNoErrors()
            assertComplete()
        }

         // check if session is cached
        with(TestObserver<List<MySession>>()) {
            repository.findAll().subscribe(this)
            assertNoErrors()
            assertResult(listOf(MySession(session)))
            assertComplete()
            localDataSource.verify(Mockito.never()).findAll()
        }
    }

    @Test
    @Throws(Exception::class)
    fun delete() {
        val session1 = newDummySession(1)
        val session2 = newDummySession(2)

        // ready caches
        repository.save(session1)
        repository.save(session2)

        localDataSource.delete(session1).invoked.thenReturn(Single.just(1))
        with(TestObserver<Int>()) {
            repository.delete(session1).subscribe(this)
            assertNoErrors()
            assertResult(1)
            assertComplete()
        }

        // check if cached session1 is deleted
        with(TestObserver<List<MySession>>()) {
            repository.findAll().subscribe(this)
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

    private fun newDummyMySession(seed: Int) = MySession().apply {
        id = seed
        session = newDummySession(seed)
    }

    private fun newDummySession(seed: Int) = Session().apply {
            val seedString = seed.toString()
            val seedLong = seed.toLong()

            id = seed
            title = seedString
            desc = seedString
            speaker = newDummySpeaker(seed)
            stime = Date(seedLong)
            etime = Date(seedLong)
            durationMin = seed
            type = seedString
            topic = newDummyTopic(seed)
            room = newDummyRoom(seed)
            lang = seedString
            slideUrl = seedString
            movieUrl = seedString
            movieDashUrl = seedString
            shareUrl = seedString
    }

    private fun newDummySpeaker(seed: Int) = Speaker().apply {
        val seedString = seed.toString()
        id = seed
        name = seedString
        imageUrl = seedString
        twitterName = seedString
        githubName = seedString
    }

    private fun newDummyTopic(seed: Int) = Topic().apply {
        val seedString = seed.toString()
        id = seed
        name = seedString
        other = seedString
    }

    private fun newDummyRoom(seed: Int) = Room().apply {
        id = seed
        name = seed.toString()
    }

}