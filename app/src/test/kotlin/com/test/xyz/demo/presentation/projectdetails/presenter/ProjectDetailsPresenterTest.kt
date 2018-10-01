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
    val gitHubRepo = GitHubRepo(1, "Fake gitHubRepo")

    val projectInteractor = mock<ProjectInteractor> {
        val successObservable = Observable.just(gitHubRepo)
        val requiredUserNameErrorObservable = Observable.error<GitHubRepo>(IllegalArgumentException("Username must be provided!"))
        val requiredProjectIdErrorObservable = Observable.error<GitHubRepo>(IllegalArgumentException("Project ID must be provided!"))

        on {getProjectDetails(eq(USER_NAME), eq(PROJECT_ID))} doReturn successObservable
        on {getProjectDetails(eq(EMPTY_VALUE), anyString())}  doReturn requiredUserNameErrorObservable
        on {getProjectDetails(anyString(), eq(EMPTY_VALUE))}  doReturn requiredProjectIdErrorObservable
    }

    val projectDetailsView = mock<ProjectDetailsView>()

    lateinit var projectDetailsPresenter: ProjectDetailsPresenter

    @Before
    fun setup() {
        init()
        projectDetailsPresenter = ProjectDetailsPresenterImpl(projectDetailsView, projectInteractor)
    }

    @Test
    fun `requestProjectDetails() should return repo details`() {
        //WHEN
        projectDetailsPresenter.requestProjectDetails(USER_NAME, PROJECT_ID)

        //THEN
        verify(projectInteractor).getProjectDetails(eq(USER_NAME), eq(PROJECT_ID))
        verify(projectDetailsView).showProjectDetails(gitHubRepo)
        verify(projectDetailsView, never()).showError(R.string.project_details_ret_error)
    }

    @Test
    fun `requestProjectDetails(), when user name is empty, it should return an error`() {
        //WHEN
        projectDetailsPresenter.requestProjectDetails("", PROJECT_ID)

        //THEN
        verify(projectInteractor).getProjectDetails(eq(""), eq(PROJECT_ID))
        verify(projectDetailsView, never()).showProjectDetails(any())
        verify(projectDetailsView).showError(R.string.project_details_ret_error)
    }

    @Test
    fun `requestProjectDetails(), when project id is empty, it should return an error`() {
        //WHEN
        projectDetailsPresenter.requestProjectDetails(USER_NAME, "")

        //THEN
        verify(projectInteractor).getProjectDetails(eq(USER_NAME), eq(""))
        verify(projectDetailsView, never()).showProjectDetails(any())
        verify(projectDetailsView).showError(R.string.project_details_ret_error)
    }

    private fun init() {
        MockitoAnnotations.initMocks(this)
        RxAndroidPlugins.setInitMainThreadSchedulerHandler { scheduler -> Schedulers.trampoline() }
    }

    companion object {
        private val EMPTY_VALUE = ""
        private val USER_NAME = "google"
        private val PROJECT_ID = "test"
    }
}
