package com.test.xyz.demo.presentation.projectdetails.presenter

import com.nhaarman.mockitokotlin2.*
import com.test.xyz.demo.R
import com.test.xyz.demo.domain.interactor.project.ProjectInteractor
import com.test.xyz.demo.domain.model.github.GitHubRepo
import io.reactivex.Observable
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.schedulers.Schedulers
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mockito.never
import org.mockito.MockitoAnnotations

class ProjectDetailsPresenterTest {
    val projectInteractor = mock<ProjectInteractor>()
    val projectDetailsView = mock<ProjectDetailsView>()

    lateinit var gitHubRepo: GitHubRepo

    lateinit var projectDetailsPresenter: ProjectDetailsPresenter

    @Before
    fun setup() {
        initializeTest()
        mockGetProjectDetailsAPI(projectInteractor)
        projectDetailsPresenter = ProjectDetailsPresenterImpl(projectDetailsView, projectInteractor)
    }

    @Test
    fun requestProjectDetails_shouldReturnRepoDetails() {
        //WHEN
        projectDetailsPresenter.requestProjectDetails(USER_NAME, PROJECT_ID)

        //THEN
        verify(projectInteractor).getProjectDetails(eq(USER_NAME), eq(PROJECT_ID))
        verify(projectDetailsView).showProjectDetails(gitHubRepo)
        verify(projectDetailsView, never()).showError(R.string.project_details_ret_error)
    }

    @Test
    fun requestProjectDetails_whenUserNameIsEmpty_shouldReturnError() {
        //WHEN
        projectDetailsPresenter.requestProjectDetails("", PROJECT_ID)

        //THEN
        verify(projectInteractor).getProjectDetails(eq(""), eq(PROJECT_ID))
        verify(projectDetailsView, never()).showProjectDetails(any())
        verify(projectDetailsView).showError(R.string.project_details_ret_error)
    }

    @Test
    fun requestProjectDetails_whenProjectIdIsEmpty_shouldReturnError() {
        //WHEN
        projectDetailsPresenter.requestProjectDetails(USER_NAME, "")

        //THEN
        verify(projectInteractor).getProjectDetails(eq(USER_NAME), eq(""))
        verify(projectDetailsView, never()).showProjectDetails(any())
        verify(projectDetailsView).showError(R.string.project_details_ret_error)
    }

    //region Helper Mocks
    private fun mockGetProjectDetailsAPI(projectInteractor: ProjectInteractor) {
        val observable = Observable.just(gitHubRepo)

        whenever(projectInteractor.getProjectDetails(eq(EMPTY_VALUE), anyString()))
                .thenReturn(Observable.error<GitHubRepo>(IllegalArgumentException("Username must be provided!")))

        whenever(projectInteractor.getProjectDetails(anyString(), eq(EMPTY_VALUE)))
                .thenReturn(Observable.error<GitHubRepo>(IllegalArgumentException("Project ID must be provided!")))

        whenever(projectInteractor.getProjectDetails(eq(USER_NAME), eq(PROJECT_ID)))
                .thenReturn(observable)
    }

    private fun initializeTest() {
        MockitoAnnotations.initMocks(this)
        RxAndroidPlugins.setInitMainThreadSchedulerHandler { scheduler -> Schedulers.trampoline() }
        gitHubRepo = GitHubRepo(1, "Fake gitHubRepo")
    }

    companion object {
        private val EMPTY_VALUE = ""
        private val USER_NAME = "google"
        private val PROJECT_ID = "test"
    }
    //endregion
}
