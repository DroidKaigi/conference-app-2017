package io.github.droidkaigi.confsched2017.viewmodel

import com.google.gson.Gson
import com.sys1yagi.kmockito.any
import com.sys1yagi.kmockito.mock
import com.sys1yagi.kmockito.verify
import com.taroid.knit.should
import io.github.droidkaigi.confsched2017.model.Sponsor
import io.github.droidkaigi.confsched2017.model.Sponsorship
import io.github.droidkaigi.confsched2017.util.RxTestSchedulerRule
import io.github.droidkaigi.confsched2017.view.helper.ResourceResolver
import io.reactivex.disposables.CompositeDisposable
import org.junit.After
import org.junit.Before
import org.junit.ClassRule
import org.junit.Test
import org.mockito.Mockito.never

class SponsorshipsViewModelTest {

    companion object {
        @ClassRule
        @JvmField
        val schedulerRule = RxTestSchedulerRule

        private val EXPECTED_SPONSORSHIPS = listOf(
                Sponsorship().apply {
                    category = "Category A"
                    sponsors = listOf(
                            createDummySponsor("a"),
                            createDummySponsor("b")
                    )
                },
                Sponsorship().apply {
                    category = "Category B"
                    sponsors = listOf(
                            createDummySponsor("c")
                    )
                }
        )

        private fun createDummySponsor(name: String) = Sponsor().apply {
            imageUrl = "imageUrl_$name"
            url = "url_$name"
        }
    }

    private val resourceResolver = object : ResourceResolver(null) {
        override fun getString(resId: Int): String = "dummy"

        override fun loadJSONFromAsset(jsonFileName: String?): String = Gson().toJson(EXPECTED_SPONSORSHIPS)
    }

    private lateinit var viewModel: SponsorshipsViewModel

    @Before
    fun setUp() {
        viewModel = SponsorshipsViewModel(resourceResolver, CompositeDisposable())
    }

    @After
    fun tearDown() {
        viewModel.destroy()
    }

    @Test
    @Throws(Exception::class)
    fun start() {
        viewModel.start()
        schedulerRule.testScheduler.triggerActions()

        assertSponsorShipEq(viewModel.sponsorShipViewModels, EXPECTED_SPONSORSHIPS)
    }

    @Test
    @Throws(Exception::class)
    fun onSponsorClick() {
        val callback = mock<SponsorViewModel.Callback>()

        viewModel.start()
        schedulerRule.testScheduler.triggerActions()

        val targetSponsor = viewModel.sponsorShipViewModels[0].sponsorViewModels[0]
        targetSponsor.setCallback(callback)

        callback.verify(never()).onClickSponsor(any())
        targetSponsor.onClickSponsor(null)
        callback.verify().onClickSponsor("url_a")
    }

    private fun assertSponsorShipEq(actual: List<SponsorshipViewModel>, expected: List<Sponsorship>) {
        actual.size.should be expected.size
        actual.map { it.category }.should be expected.map { it.category }
        actual.zip(expected).forEach {
            val actualSponsors = it.first.sponsorship.sponsors
            val expectedSponsors = it.second.sponsors
            assertSponsorEq(actualSponsors, expectedSponsors)
        }
    }

    private fun assertSponsorEq(actual: List<Sponsor>, expected: List<Sponsor>) {
        actual.size.should be expected.size
        actual.map { it.imageUrl }.should be expected.map { it.imageUrl }
        actual.map { it.url }.should be expected.map { it.url }
    }
}
