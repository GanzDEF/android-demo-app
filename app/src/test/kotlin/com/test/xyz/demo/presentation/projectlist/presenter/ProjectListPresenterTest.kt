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
    val projectListView = mock<ProjectListView>()
    val projectInteractor = mock<ProjectInteractor>()

    lateinit var gitHubRepoList: MutableList<GitHubRepo>

    lateinit var projectListPresenter: ProjectListPresenter

    @Before
    fun setup() {
        initializeTest()
        mockGetProjectListAPI(projectInteractor)
        projectListPresenter = ProjectListPresenterImpl(projectListView, projectInteractor)
    }

    @Test
    fun requestProjectList_shouldReturnRepoList() {
        //WHEN
        projectListPresenter.requestProjectList(USER_NAME)

        //THEN
        verify(projectInteractor).getProjectList(eq(USER_NAME))
        verify(projectListView).showProjectList(gitHubRepoList)
        verify(projectListView, never()).showError(R.string.project_list_ret_error)
    }

    @Test
    fun requestProjectList_whenUserNameIsEmpty_shouldError() {
        //WHEN
        projectListPresenter.requestProjectList("")

        //THEN
        verify(projectInteractor).getProjectList(eq(""))
        verify(projectListView, never()).showProjectList(anyList())
        verify(projectListView).showError(R.string.project_list_ret_error)
    }

    //region Helper mocks
    private fun mockGetProjectListAPI(projectInteractor: ProjectInteractor) {
        val observable = Observable.just<List<GitHubRepo>>(gitHubRepoList)

        whenever(projectInteractor.getProjectList(eq(EMPTY_VALUE)))
                .thenReturn(Observable.error<List<GitHubRepo>>(IllegalArgumentException("Username must be provided!")))

        whenever(projectInteractor.getProjectList(eq(USER_NAME))).thenReturn(observable)
    }

    private fun initializeTest() {
        MockitoAnnotations.initMocks(this)
        RxAndroidPlugins.setInitMainThreadSchedulerHandler { scheduler -> Schedulers.trampoline() }

        gitHubRepoList = ArrayList()

        for (i in 0..9) {
            gitHubRepoList.add(GitHubRepo("Fake gitHubRepo: $i"))
        }
    }

    companion object {
        private val USER_NAME = "google"
        private val EMPTY_VALUE = ""
    }
    //endregion
}
