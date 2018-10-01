package com.test.xyz.demo.domain.interactor.project

import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.eq
import com.nhaarman.mockitokotlin2.mock
import com.test.xyz.demo.domain.model.github.GitHubRepo
import com.test.xyz.demo.domain.repository.api.ProjectListRepository
import io.reactivex.Observable
import io.reactivex.plugins.RxJavaPlugins
import io.reactivex.schedulers.Schedulers
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers.anyString
import org.mockito.MockitoAnnotations
import java.io.IOException

class ProjectInteractorTest {
    val gitHubRepo = GitHubRepo(1, "RepoItem")
    val gitHubRepoList = arrayListOf(GitHubRepo(0, "Fake GitHub Repo-0"),
            GitHubRepo(1, "Fake GitHub Repo-1"),
            GitHubRepo(2, "Fake GitHub Repo-2"))

    val projectListRepository = mock<ProjectListRepository> {
        on {getProjectList(USER_NAME)}                           doReturn Observable.just<List<GitHubRepo>>(gitHubRepoList)
        on {getProjectList(eq(INVALID_ACCOUNT))}                 doReturn Observable.error<List<GitHubRepo>>(IOException("Invalid account"))
        on {getProjectDetails(eq(USER_NAME), anyString())}       doReturn Observable.just(gitHubRepo)
        on {getProjectDetails(eq(INVALID_ACCOUNT), anyString())} doReturn Observable.error<GitHubRepo>(IOException("Invalid account"))
    }

    private lateinit var testSubject: ProjectInteractorImpl

    @Before
    fun setup() {
        init()
        testSubject = ProjectInteractorImpl(projectListRepository)
    }

    @Test
    fun `getProjectList() should return repo list information`() {
        //WHEN
        val projectListObservable = testSubject.getProjectList(USER_NAME)

        //THEN
        projectListObservable.test()
                .assertValues(gitHubRepoList)
                .assertNoErrors()
    }

    @Test
    fun `getProjectList(), when user name is empty, it should return a validation error`() {
        //WHEN
        val projectListObservable = testSubject.getProjectList("")

        //THEN
        projectListObservable.test()
                .assertError(IllegalArgumentException::class.java)
    }

    @Test
    fun `getProjectList(), when network error happens, it should return a failure error`() {
        //WHEN
        val projectListObservable = testSubject.getProjectList(INVALID_ACCOUNT)

        //THEN
        projectListObservable.test()
                .assertError(IOException::class.java)
    }

    @Test
    fun `getProjectDetails() should return a Repo item Info`() {
        //WHEN
        val projectDetailsObservable = testSubject.getProjectDetails(USER_NAME, PROJECT_ID)

        //THEN
        projectDetailsObservable.test()
                .assertValues(gitHubRepo)
                .assertNoErrors()
    }

    @Test
    fun `getProjectDetails(), when user name is empty, it should return a validation error`() {
        // WHEN
        val projectDetailsObservable = testSubject.getProjectDetails("", PROJECT_ID)

        //THEN
        projectDetailsObservable.test()
                .assertError(IllegalArgumentException::class.java)
    }

    @Test
    fun `getProjectDetails(), when project is is empty, it should return a validation error`() {
        // WHEN
        val projectDetailsObservable = testSubject.getProjectDetails("", PROJECT_ID)

        //THEN
        projectDetailsObservable.test()
                .assertError(IllegalArgumentException::class.java)
    }

    @Test
    fun `getProjectDetails(), when a network error happens, it should return a failure error`() {
        // WHEN
        val projectDetailsObservable = testSubject.getProjectDetails(INVALID_ACCOUNT, PROJECT_ID)

        //THEN
        projectDetailsObservable.test()
                .assertError(IOException::class.java)
    }

    private fun init() {
        MockitoAnnotations.initMocks(this)
        RxJavaPlugins.setIoSchedulerHandler { scheduler -> Schedulers.trampoline() }
    }

    companion object {
        private val USER_NAME = "hazems"
        private val PROJECT_ID = "Test"
        private val INVALID_ACCOUNT = "INVALID_ACCOUNT"
    }
}
