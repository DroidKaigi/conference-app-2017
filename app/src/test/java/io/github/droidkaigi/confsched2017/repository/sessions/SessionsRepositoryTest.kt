package io.github.droidkaigi.confsched2017.repository.sessions

import com.sys1yagi.kmockito.invoked
import com.sys1yagi.kmockito.mock
import com.sys1yagi.kmockito.verify
import io.github.droidkaigi.confsched2017.api.DroidKaigiClient
import io.github.droidkaigi.confsched2017.model.OrmaDatabase
import io.github.droidkaigi.confsched2017.model.Session
import io.reactivex.Completable
import io.reactivex.Single
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.*
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment

@RunWith(RobolectricTestRunner::class)
class SessionsRepositoryTest {

    @Test
    fun hasCacheSessions() {
        // false
        run {
            val repository = SessionsRepository(
                    SessionsLocalDataSource(mock()),
                    SessionsRemoteDataSource(mock())
            )

            assertThat(repository.hasCacheSessions()).isFalse()
        }

        // true
        run {
            val repository = SessionsRepository(
                    SessionsLocalDataSource(mock()),
                    SessionsRemoteDataSource(mock())
            )
            repository.cachedSessions = mapOf(0 to Session())
            repository.setIdDirty(false)

            assertThat(repository.hasCacheSessions()).isTrue()
        }
    }

    @Test
    fun findAllRemoteRequestAndLocalCache() {
        val sessions = listOf(Session())
        val client = mockDroidKaigiClient(sessions)
        val ormaDatabase = mock<OrmaDatabase>().apply {
            transactionAsCompletable(any()).invoked.thenReturn(Completable.complete())
        }
        val cachedSessions: Map<Int, Session> = spy(mutableMapOf())

        val repository = SessionsRepository(
                SessionsLocalDataSource(ormaDatabase),
                SessionsRemoteDataSource(client)
        ).apply {
            this.cachedSessions = cachedSessions
        }

        // TODO I want to use enum for language id.
        repository.findAll(Session.LANG_JA_ID)
                .test()
                .run {
                    assertNoErrors()
                    assertResult(sessions)
                    assertComplete()

                    client.verify().getSessions(eq(Session.LANG_JA_ID))
                    ormaDatabase.verify().transactionAsCompletable(any())
                    cachedSessions.verify(never()).values
                }

        repository.findAll(Session.LANG_JA_ID)
                .test()
                .run {
                    assertNoErrors()
                    assertThat(values().first().size).isEqualTo(1)
                    assertComplete()

                    cachedSessions.verify().values
                }
    }

    @Test
    fun findAllLocalCache() {
        val sessions = listOf(Session())
        val client = mockDroidKaigiClient(sessions)
        val ormaDatabase = OrmaDatabase
                .builder(RuntimeEnvironment.application)
                .name(null)
                .build()
        val cachedSessions: Map<Int, Session> = mock()

        val repository = SessionsRepository(
                SessionsLocalDataSource(ormaDatabase),
                SessionsRemoteDataSource(client)
        ).apply {
            this.cachedSessions = cachedSessions
        }

        repository.findAll(Session.LANG_JA_ID)
                .test()
                .run {
                    assertNoErrors()
                    assertResult(sessions)
                    assertComplete()

                    client.verify().getSessions(eq(Session.LANG_JA_ID))
                    cachedSessions.verify(never()).values
                }

        repository.setIdDirty(true)

        repository.findAll(Session.LANG_JA_ID)
                .test()
                .run {
                    assertNoErrors()
                    assertThat(values().first().size).isEqualTo(1)
                    assertComplete()

                    cachedSessions.verify(never()).values
                }
    }

    @Test
    fun hasCacheSession() {
        // false cachedSessions is null
        run {
            val repository = SessionsRepository(
                    SessionsLocalDataSource(mock()),
                    SessionsRemoteDataSource(mock())
            )

            assertThat(repository.hasCacheSession(0)).isFalse()
        }

        // false sessionId not found
        run {
            val repository = SessionsRepository(
                    SessionsLocalDataSource(mock()),
                    SessionsRemoteDataSource(mock())
            )
            repository.cachedSessions = mapOf(1 to Session())
            repository.setIdDirty(false)

            assertThat(repository.hasCacheSession(0)).isFalse()
        }

        // false dirty
        run {
            val repository = SessionsRepository(
                    SessionsLocalDataSource(mock()),
                    SessionsRemoteDataSource(mock())
            )
            repository.cachedSessions = mapOf(1 to Session())
            repository.setIdDirty(true)

            assertThat(repository.hasCacheSession(1)).isFalse()
        }

        // true
        run {
            val repository = SessionsRepository(
                    SessionsLocalDataSource(mock()),
                    SessionsRemoteDataSource(mock())
            )
            repository.cachedSessions = mapOf(1 to Session())
            repository.setIdDirty(false)

            assertThat(repository.hasCacheSession(1)).isTrue()
        }
    }

    fun mockDroidKaigiClient(sessions: List<Session>) = mock<DroidKaigiClient>().apply {
        getSessions(anyString()).invoked.thenReturn(
                Single.just(sessions)
        )
    }

}
