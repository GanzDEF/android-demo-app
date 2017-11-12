package com.test.xyz.daggersample.ui.repodetails.mvp

import com.test.xyz.daggersample.domain.interactor.MainInteractor
import com.test.xyz.daggersample.domain.repository.api.model.Repo
import com.test.xyz.daggersample.ui.BasePresenterTest
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Matchers
import org.mockito.Mock
import org.mockito.Mockito.never
import org.mockito.Mockito.verify
import org.mockito.runners.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class RepoDetailsPresenterTest : BasePresenterTest() {

    private var repoDetailsPresenter: RepoDetailsPresenter? = null

    @Mock
    internal var mainInteractor: MainInteractor? = null

    @Mock
    internal var repoDetailsView: RepoDetailsView? = null

    @Before
    fun setup() {
        mockInteractor(mainInteractor)
        repoDetailsPresenter = RepoDetailsPresenterImpl(repoDetailsView, mainInteractor)
    }

    @Test
    fun requestRepoDetails_shouldReturnRepoDetails() {
        //GIVEN
        //NOTHING

        //WHEN
        repoDetailsPresenter!!.requestRepoDetails(USER_NAME, PROJECT_ID)

        //THEN
        verify<RepoDetailsView>(repoDetailsView).showRepoDetails(Matchers.any(Repo::class.java))
        verify<RepoDetailsView>(repoDetailsView, never()).showError(Matchers.any(String::class.java))
    }

    @Test
    fun requestRepoDetails_whenUserNameIsEmpty_shouldReturnError() {
        //GIVEN
        //NOTHING

        //WHEN
        repoDetailsPresenter!!.requestRepoDetails("", PROJECT_ID)

        //THEN
        verify<RepoDetailsView>(repoDetailsView, never()).showRepoDetails(Matchers.any(Repo::class.java))
        verify<RepoDetailsView>(repoDetailsView).showError(Matchers.any(String::class.java))
    }

    companion object {
        private val USER_NAME = "google"
        private val PROJECT_ID = "test"
    }
}
