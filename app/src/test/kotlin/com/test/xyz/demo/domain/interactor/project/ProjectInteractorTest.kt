package com.test.xyz.demo.domain.interactor.project

import com.nhaarman.mockitokotlin2.eq
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
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
import java.util.*

class ProjectInteractorTest {
    private val projectListRepository = mock<ProjectListRepository>()
    private var gitHubRepo = mock<GitHubRepo>()
    private var gitHubRepoList = mock<MutableList<GitHubRepo>>()

    private lateinit var testSubject: ProjectInteractorImpl

    @Before
    fun setup() {
        initializeTest()
        testSubject = ProjectInteractorImpl(projectListRepository)
    }

    @Test
    fun getProjectList_whenUserNameIsCorrect_shouldReturnRepoListInfo() {
        //GIVEN
        mockGetProjectListAPI()

        //WHEN
        val projectListObservable = testSubject.getProjectList(USER_NAME)

        //THEN
        projectListObservable.test()
                .assertValues(gitHubRepoList)
                .assertNoErrors()
    }

    @Test
    fun getProjectList_whenUserNameIsEmpty_shouldReturnValidationError() {
        //GIVEN
        mockGetProjectListAPI()

        //WHEN
        val projectListObservable = testSubject.getProjectList("")

        //THEN
        projectListObservable.test()
                .assertError(IllegalArgumentException::class.java)
    }

    @Test
    fun getProjectList_whenNetworkErrorHappen_shouldReturnFailureError() {
        //GIVEN
        mockGetProjectListAPI()

        //WHEN
        val projectListObservable = testSubject.getProjectList(UNLUCKY_ACCOUNT)

        //THEN
        projectListObservable.test()
                .assertError(IOException::class.java)
    }

    @Test
    fun getProjectDetails_whenUserNameAndProjectIDAreCorrect_shouldReturnRepoItemInfo() {
        //GIVEN
        mockGetProjectDetailsAPI()

        //WHEN
        val projectDetailsObservable = testSubject.getProjectDetails(USER_NAME, PROJECT_ID)

        //THEN
        projectDetailsObservable.test()
                .assertValues(gitHubRepo)
                .assertNoErrors()
    }

    @Test
    fun getProjectDetails_whenUserNameIsEmpty_shouldReturnValidationError() {
        //GIVEN
        mockGetProjectDetailsAPI()

        // WHEN
        val projectDetailsObservable = testSubject.getProjectDetails("", PROJECT_ID)

        //THEN
        projectDetailsObservable.test()
                .assertError(IllegalArgumentException::class.java)
    }

    @Test
    fun getProjectDetails_whenProjectIDIsEmpty_shouldReturnValidationError() {
        //GIVEN
        mockGetProjectDetailsAPI()

        // WHEN
        val projectDetailsObservable = testSubject.getProjectDetails("", PROJECT_ID)

        //THEN
        projectDetailsObservable.test()
                .assertError(IllegalArgumentException::class.java)
    }

    @Test
    fun getProjectDetails_whenNetworkErrorHappen_shouldReturnFailureError() {
        //GIVEN
        mockGetProjectDetailsAPI()

        // WHEN
        val projectDetailsObservable = testSubject.getProjectDetails(UNLUCKY_ACCOUNT, PROJECT_ID)

        //THEN
        projectDetailsObservable.test()
                .assertError(IOException::class.java)
    }

    private fun mockGetProjectListAPI() {
        mockGetProjectListHappyPath()
        mockGetProjectListErrorPath()
    }

    private fun mockGetProjectDetailsAPI() {
        mockGetProjectDetailsHappyPath()
        mockGetProjectDetailsErrorPath()
    }

    private fun mockGetProjectListHappyPath() {
        val observable = Observable.just<List<GitHubRepo>>(gitHubRepoList)
        whenever(projectListRepository.getProjectList(USER_NAME)).thenReturn(observable)
    }

    private fun mockGetProjectListErrorPath() {
        whenever(projectListRepository.getProjectList(eq(UNLUCKY_ACCOUNT)))
                .thenReturn(Observable.error<List<GitHubRepo>>(IOException("Invalid account")))
    }

    private fun mockGetProjectDetailsHappyPath() {
        val observable = Observable.just(gitHubRepo)
        whenever(projectListRepository.getProjectDetails(eq(USER_NAME), anyString())).thenReturn(observable)
    }

    private fun mockGetProjectDetailsErrorPath() {
        whenever(projectListRepository.getProjectDetails(eq(UNLUCKY_ACCOUNT), anyString()))
                .thenReturn(Observable.error<GitHubRepo>(IOException("Invalid account")))
    }

    private fun initializeTest() {
        MockitoAnnotations.initMocks(this)
        RxJavaPlugins.setIoSchedulerHandler { scheduler -> Schedulers.trampoline() }
        initializeFakeGitHubRepos()
    }

    private fun initializeFakeGitHubRepos() {
        gitHubRepo = GitHubRepo("RepoItem")
        gitHubRepoList = ArrayList()

        for (i in 0..9) {
            gitHubRepoList.add(GitHubRepo("SampleRepoItem$i"))
        }
    }

    companion object {
        private val USER_NAME = "hazems"
        private val PROJECT_ID = "Test"
        private val UNLUCKY_ACCOUNT = "UNLUCKY_ACCOUNT"
    }
}
