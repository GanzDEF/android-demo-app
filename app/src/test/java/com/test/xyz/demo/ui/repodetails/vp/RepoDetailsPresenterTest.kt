@file:Suppress("IllegalIdentifier")

package com.test.xyz.demo.ui.repodetails.vp

import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.never
import com.nhaarman.mockito_kotlin.verify
import com.test.xyz.demo.domain.interactor.MainInteractor
import com.test.xyz.demo.domain.repository.api.model.Repo
import com.test.xyz.demo.ui.BasePresenterTest
import org.junit.Before
import org.junit.Test

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
    fun `requestRepoDetails should return RepoDetails`() {
        //GIVEN
        //NOTHING

        //WHEN
        repoDetailsPresenter.requestRepoDetails(USER_NAME, PROJECT_ID)

        //THEN
        verify(repoDetailsView).showRepoDetails(any<Repo>())
        verify(repoDetailsView, never()).showError(any<String>())
    }

    @Test
    fun `requestRepoDetails when UserName is empty shouldReturnError`() {
        //GIVEN
        //NOTHING

        //WHEN
        repoDetailsPresenter.requestRepoDetails("", PROJECT_ID)

        //THEN
        verify(repoDetailsView, never()).showRepoDetails(any<Repo>())
        verify(repoDetailsView).showError(any<String>())
    }

    companion object {
        private val USER_NAME = "google"
        private val PROJECT_ID = "test"
    }
}
