package io.github.droidkaigi.confsched2017.api

import com.sys1yagi.kmockito.invoked
import com.sys1yagi.kmockito.mock
import com.sys1yagi.kmockito.verify
import io.github.droidkaigi.confsched2017.api.service.DroidKaigiService
import io.github.droidkaigi.confsched2017.api.service.GithubService
import io.github.droidkaigi.confsched2017.api.service.GoogleFormService
import io.github.droidkaigi.confsched2017.model.Session
import io.github.droidkaigi.confsched2017.util.DummyCreator
import io.reactivex.Single
import org.junit.Test
import org.mockito.Mockito

class DroidKaigiClientTest {

    private val droidKaigiService = mock<DroidKaigiService>()

    private val githubService = mock<GithubService>()

    private val googleFormService = mock<GoogleFormService>()

    private val client = DroidKaigiClient(droidKaigiService, githubService, googleFormService)

    @Test
    @Throws(Exception::class)
    fun getSessions() {
        val expected = Array(10) { DummyCreator.newSession(it) }.toList()
        droidKaigiService.getSessionsJa().invoked.thenReturn(Single.just(expected))

        client.getSessions(LocaleUtil.LANG_JA).test().run {
            assertNoErrors()
            assertResult(expected)
            assertComplete()
        }

        // TODO: multilingual
        client.getSessions(LocaleUtil.LANG_EN).test().run {
            assertNoErrors()
            assertResult(expected)
            assertComplete()
        }
        droidKaigiService.verify(Mockito.times(2)).getSessionsJa()
    }

    @Test
    @Throws(Exception::class)
    fun getContributors() {
        val expected = Array(10) { DummyCreator.newContributor(it) }.toList()
        githubService.getContributors("DroidKaigi", "conference-app-2017", 1, 100)
                .invoked.thenReturn(Single.just(expected))

        client.getContributors().test().run {
            assertNoErrors()
            assertResult(expected)
            assertComplete()
        }
    }
}
