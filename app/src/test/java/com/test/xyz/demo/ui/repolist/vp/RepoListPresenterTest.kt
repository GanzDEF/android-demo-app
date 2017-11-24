@file:Suppress("IllegalIdentifier")
package com.test.xyz.demo.ui.repolist.vp

import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.never
import com.nhaarman.mockito_kotlin.verify
import com.test.xyz.demo.domain.interactor.MainInteractor
import com.test.xyz.demo.ui.BasePresenterTest
import org.junit.Before
import org.junit.Test

class RepoListPresenterTest : BasePresenterTest() {

    lateinit var repoListPresenter: RepoListPresenter

    val repoListView: RepoListView = mock()
    val mainInteractor: MainInteractor = mock()

    @Before
    fun setup() {
        mockInteractor(mainInteractor)
        repoListPresenter = RepoListPresenterImpl(repoListView, mainInteractor)
    }

    @Test
    fun `requestRepoList shouldReturnRepoList`() {
        //GIVEN
        //NOTHING

        //WHEN
        repoListPresenter.requestRepoList(USER_NAME)

        //THEN
        verify(repoListView).showRepoList(any())
        verify(repoListView, never()).showError(any<String>())
    }

    @Test
    fun `requestRepoList whenUserNameIsEmpty shouldReturnError`() {
        //GIVEN
        //NOTHING

        //WHEN
        repoListPresenter.requestRepoList("")

        //THEN
        verify(repoListView, never()).showRepoList(any())
        verify(repoListView).showError(any<String>())
    }

    companion object {
        private val USER_NAME = "google"
    }
}
