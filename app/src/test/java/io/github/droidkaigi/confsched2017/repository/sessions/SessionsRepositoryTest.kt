package io.github.droidkaigi.confsched2017.repository.sessions

import com.sys1yagi.kmockito.mock
import io.github.droidkaigi.confsched2017.model.Session
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

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
}
