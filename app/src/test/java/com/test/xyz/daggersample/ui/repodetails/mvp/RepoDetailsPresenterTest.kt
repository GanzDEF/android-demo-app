@file:Suppress("IllegalIdentifier")

package com.test.xyz.daggersample.ui.repodetails.mvp

import com.nhaarman.mockito_kotlin.mock
import com.test.xyz.daggersample.domain.interactor.MainInteractor
import com.test.xyz.daggersample.domain.repository.api.model.Repo
import com.test.xyz.daggersample.ui.BasePresenterTest
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers.any
import org.mockito.Mockito.never
import org.mockito.Mockito.verify

class RepoDetailsPresenterTest : BasePresenterTest() {

    lateinit var repoDetailsPresenter: RepoDetailsPresenter

    var mainInteractor: MainInteractor = mock()
    var repoDetailsView: RepoDetailsView = mock()

    @Before
    fun setup() {
        mockInteractor(mainInteractor)
        repoDetailsPresenter = RepoDetailsPresenterImpl(repoDetailsView, mainInteractor)
    }

    @Test
    fun `requestRepoDetails shouldReturnRepoDetails`() {
        //GIVEN
        //NOTHING

        //WHEN
        repoDetailsPresenter.requestRepoDetails(USER_NAME, PROJECT_ID)

        //THEN
        verify<RepoDetailsView>(repoDetailsView).showRepoDetails(any(Repo::class.java))
        verify<RepoDetailsView>(repoDetailsView, never()).showError(any(String::class.java))
    }

    @Test
    fun `requestRepoDetails whenUserNameIsEmpty shouldReturnError`() {
        //GIVEN
        //NOTHING

        //WHEN
        repoDetailsPresenter.requestRepoDetails("", PROJECT_ID)

        //THEN
        verify(repoDetailsView, never()).showRepoDetails(any(Repo::class.java))
        verify(repoDetailsView).showError(any(String::class.java))
    }

    companion object {
        private val USER_NAME = "google"
        private val PROJECT_ID = "test"
    }
}
