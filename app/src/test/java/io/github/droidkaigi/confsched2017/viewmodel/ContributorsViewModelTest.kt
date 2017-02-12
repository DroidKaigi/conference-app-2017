package io.github.droidkaigi.confsched2017.viewmodel

import com.sys1yagi.kmockito.any
import com.sys1yagi.kmockito.invoked
import com.sys1yagi.kmockito.mock
import com.sys1yagi.kmockito.verify
import com.taroid.knit.should
import io.github.droidkaigi.confsched2017.model.Contributor
import io.github.droidkaigi.confsched2017.repository.contributors.ContributorsRepository
import io.github.droidkaigi.confsched2017.util.RxTestSchedulerRule
import io.github.droidkaigi.confsched2017.view.helper.ResourceResolver
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable
import org.junit.After
import org.junit.Before
import org.junit.ClassRule
import org.junit.Test
import org.mockito.Mockito.never

class ContributorsViewModelTest {

    companion object {
        @ClassRule
        @JvmField
        val schedulerRule = RxTestSchedulerRule

        private val EXPECTED_CONTRIBUTORS = listOf(
                Contributor().apply {
                    name = "Alice"
                    htmlUrl = "AliceUrl"
                },
                Contributor().apply {
                    name = "Bob"
                },
                Contributor().apply {
                    name = "Charlie"
                }
        )
    }

    private val resourceResolver = object : ResourceResolver(null) {
        override fun getString(resId: Int): String = "Contributors"

        override fun getString(resId: Int, vararg formatArgs: Any?): String = "(${formatArgs[0]} people)"
    }

    private val toolbarViewModel = mock<ToolbarViewModel>()

    private val repository = mock<ContributorsRepository>().apply {
        findAll().invoked.thenReturn(Single.just(EXPECTED_CONTRIBUTORS))
    }

    private lateinit var viewModel: ContributorsViewModel

    @Before
    fun setUp() {
        viewModel = ContributorsViewModel(resourceResolver, toolbarViewModel, repository, CompositeDisposable())
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

        assertEq(viewModel.contributorViewModels, EXPECTED_CONTRIBUTORS)
        viewModel.loadingVisibility.should be 8 // GONE
        viewModel.refreshing.should be false
        toolbarViewModel.verify().toolbarTitle = "Contributors (3 people)"
    }

    @Test
    @Throws(Exception::class)
    fun onSwipeRefresh() {
        viewModel.onSwipeRefresh()
        schedulerRule.testScheduler.triggerActions()

        assertEq(viewModel.contributorViewModels, EXPECTED_CONTRIBUTORS)
        viewModel.loadingVisibility.should be 8 // GONE
        viewModel.refreshing.should be false
        toolbarViewModel.verify().toolbarTitle = "Contributors (3 people)"
    }

    @Test
    @Throws(Exception::class)
    fun onContributorClick() {
        val callback = mock<ContributorsViewModel.Callback>()
        viewModel.setCallback(callback)

        viewModel.start()
        schedulerRule.testScheduler.triggerActions()

        callback.verify(never()).onClickContributor(any())
        viewModel.contributorViewModels[0].onClickContributor(null)
        callback.verify().onClickContributor("AliceUrl")
    }

    private fun assertEq(actual: List<ContributorViewModel>, expected: List<Contributor>) {
        actual.size.should be expected.size
        actual.map { it.name }.should be expected.map { it.name }
    }
}
