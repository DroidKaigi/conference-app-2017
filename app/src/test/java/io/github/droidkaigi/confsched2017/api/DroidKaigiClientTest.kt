package io.github.droidkaigi.confsched2017.api

import com.sys1yagi.kmockito.invoked
import com.sys1yagi.kmockito.mock
import com.sys1yagi.kmockito.verify
import io.github.droidkaigi.confsched2017.api.service.DroidKaigiService
import io.github.droidkaigi.confsched2017.api.service.GithubService
import io.github.droidkaigi.confsched2017.api.service.GoogleFormService
import io.github.droidkaigi.confsched2017.model.SessionFeedback
import io.github.droidkaigi.confsched2017.util.DummyCreator
import io.github.droidkaigi.confsched2017.util.LocaleUtil
import io.reactivex.Single
import org.junit.Test
import org.mockito.Mockito
import retrofit2.Response
import java.util.*

class DroidKaigiClientTest {

    private val droidKaigiService = mock<DroidKaigiService>()

    private val githubService = mock<GithubService>()

    private val googleFormService = mock<GoogleFormService>()

    private val client = DroidKaigiClient(droidKaigiService, githubService, googleFormService)

    @Test
    @Throws(Exception::class)
    fun getSessions() {
        val expected = Array(10) { DummyCreator.newSession(it) }.toList()
        droidKaigiService.sessionsJa.invoked.thenReturn(Single.just(expected))
        droidKaigiService.sessionsEn.invoked.thenReturn(Single.just(expected))

        client.getSessions(Locale.JAPANESE).test().run {
            assertNoErrors()
            assertResult(expected)
            assertComplete()
        }

        client.getSessions(Locale.ENGLISH).test().run {
            assertNoErrors()
            assertResult(expected)
            assertComplete()
        }
        droidKaigiService.verify(Mockito.times(1)).sessionsJa
        droidKaigiService.verify(Mockito.times(1)).sessionsEn
    }

    @Test
    @Throws(Exception::class)
    fun getContributors() {
        val expected = Array(10) { DummyCreator.newContributor(it) }.toList()
        githubService.getContributors("DroidKaigi", "conference-app-2017", 1, 100)
                .invoked.thenReturn(Single.just(expected))

        client.contributors.test().run {
            assertNoErrors()
            assertResult(expected)
            assertComplete()
        }
    }

    @Test
    @Throws(Exception::class)
    fun submitSessionFeedback() {
        val res = Response.success<Void>(null)
        googleFormService.submitSessionFeedback(0)
                .invoked.thenReturn(Single.just(res))

        val feedBack = SessionFeedback(0)
        client.submitSessionFeedback(feedBack).test().run {
            assertNoErrors()
            assertValue(res)
            assertComplete()
        }
        googleFormService.verify().submitSessionFeedback(0)
    }
}
