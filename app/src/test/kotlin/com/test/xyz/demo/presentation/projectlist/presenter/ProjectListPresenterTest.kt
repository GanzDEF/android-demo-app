package com.test.xyz.demo.presentation.projectlist.presenter

import com.nhaarman.mockitokotlin2.*
import com.test.xyz.demo.R
import com.test.xyz.demo.domain.interactor.project.ProjectInteractor
import com.test.xyz.demo.domain.model.github.GitHubRepo
import io.reactivex.Observable
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.schedulers.Schedulers
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers.anyList
import org.mockito.MockitoAnnotations

class ProjectListPresenterTest {

    val gitHubRepoList = arrayListOf(GitHubRepo(0, "Fake GitHub Repo-0"),
            GitHubRepo(1, "Fake GitHub Repo-1"),
            GitHubRepo(2, "Fake GitHub Repo-2"))

    val projectInteractor = mock<ProjectInteractor> {
        val successObservable = Observable.just<List<GitHubRepo>>(gitHubRepoList)
        val requiredUserNameErrorObservable = Observable.error<List<GitHubRepo>>(IllegalArgumentException("Username must be provided!"))

        on {getProjectList(eq(USER_NAME))}   doReturn successObservable
        on {getProjectList(eq(EMPTY_VALUE))} doReturn requiredUserNameErrorObservable
    }

    val projectListView = mock<ProjectListView>()

    lateinit var projectListPresenter: ProjectListPresenter

    @Before
    fun setup() {
        init()
        projectListPresenter = ProjectListPresenterImpl(projectListView, projectInteractor)
    }

    @Test
    fun `requestProjectList() should return Repo List`() {
        //WHEN
        projectListPresenter.requestProjectList(USER_NAME)

        //THEN
        verify(projectInteractor).getProjectList(eq(USER_NAME))
        verify(projectListView).showProjectList(gitHubRepoList)
        verify(projectListView, never()).showError(R.string.project_list_ret_error)
    }

    @Test
    fun `requestProjectList(), when user name is empty, should return error`() {
        //WHEN
        projectListPresenter.requestProjectList("")

        //THEN
        verify(projectInteractor).getProjectList(eq(""))
        verify(projectListView, never()).showProjectList(anyList())
        verify(projectListView).showError(R.string.project_list_ret_error)
    }

    private fun init() {
        MockitoAnnotations.initMocks(this)
        RxAndroidPlugins.setInitMainThreadSchedulerHandler { scheduler -> Schedulers.trampoline() }
    }

    companion object {
        private val USER_NAME = "google"
        private val EMPTY_VALUE = ""
    }
}
