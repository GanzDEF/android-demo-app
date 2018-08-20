package com.test.xyz.demo.domain.interactor.project;

import com.test.xyz.demo.domain.model.github.GitHubRepo;
import com.test.xyz.demo.domain.repository.api.ProjectListRepository;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.plugins.RxJavaPlugins;
import io.reactivex.schedulers.Schedulers;

import static org.mockito.AdditionalMatchers.not;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.when;

public class ProjectInteractorTest {
    private static final String USER_NAME = "hazems";
    private static final String PROJECT_ID = "Test";
    private static final String UNLUCKY_ACCOUNT = "UNLUCKY_ACCOUNT";
    private GitHubRepo gitHubRepo;
    private List<GitHubRepo> gitHubRepoList;

    @Mock ProjectListRepository projectListRepository;

    ProjectInteractorImpl testSubject;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        initializeRxSchedulers();
        initializeFakeGitHubRepos();
        testSubject = new ProjectInteractorImpl(projectListRepository);
    }

    @Test
    public void getProjectList_whenUserNameIsCorrect_shouldReturnRepoListInfo() {
        //GIVEN
        mockGetProjectListAPI();

        //WHEN
        Observable<List<GitHubRepo>> projectListObservable = testSubject.getProjectList(USER_NAME);

        //THEN
        projectListObservable.test()
                .assertValues(gitHubRepoList)
                .assertNoErrors();
    }

    @Test
    public void getProjectList_whenUserNameIsEmpty_shouldReturnValidationError() {
        //GIVEN
        mockGetProjectListAPI();

        //WHEN
        Observable<List<GitHubRepo>> projectListObservable = testSubject.getProjectList("");

        //THEN
        projectListObservable.test()
                .assertError(IllegalArgumentException.class);
    }

    @Test
    public void getProjectList_whenNetworkErrorHappen_shouldReturnFailureError() {
        //GIVEN
        mockGetProjectListAPI();

        //WHEN
        Observable<List<GitHubRepo>> projectListObservable = testSubject.getProjectList(UNLUCKY_ACCOUNT);

        //THEN
        projectListObservable.test()
                .assertError(IOException.class);
    }

    @Test
    public void getProjectDetails_whenUserNameAndProjectIDAreCorrect_shouldReturnRepoItemInfo() {
        //GIVEN
        mockGetProjectDetailsAPI();

        //WHEN
        Observable<GitHubRepo> projectDetailsObservable = testSubject.getProjectDetails(USER_NAME, PROJECT_ID);

        //THEN
        projectDetailsObservable.test()
                .assertValues(gitHubRepo)
                .assertNoErrors();
    }

    @Test
    public void getProjectDetails_whenUserNameIsEmpty_shouldReturnValidationError() {
        //GIVEN
        mockGetProjectDetailsAPI();

        // WHEN
        Observable<GitHubRepo> projectDetailsObservable = testSubject.getProjectDetails("", PROJECT_ID);

        //THEN
        projectDetailsObservable.test()
                .assertError(IllegalArgumentException.class);
    }

    @Test
    public void getProjectDetails_whenProjectIDIsEmpty_shouldReturnValidationError() {
        //GIVEN
        mockGetProjectDetailsAPI();

        // WHEN
        Observable<GitHubRepo> projectDetailsObservable = testSubject.getProjectDetails("", PROJECT_ID);

        //THEN
        projectDetailsObservable.test()
                .assertError(IllegalArgumentException.class);
    }

    @Test
    public void getProjectDetails_whenNetworkErrorHappen_shouldReturnFailureError() {
        //GIVEN
        mockGetProjectDetailsAPI();

        // WHEN
        Observable<GitHubRepo> projectDetailsObservable = testSubject.getProjectDetails(UNLUCKY_ACCOUNT, PROJECT_ID);

        //THEN
        projectDetailsObservable.test()
                .assertError(IOException.class);
    }

    private void mockGetProjectListAPI() {
        mockGetProjectListHappyPath();
        mockGetProjectListErrorPath();
    }

    private void mockGetProjectDetailsAPI() {
        mockGetProjectDetailsHappyPath();
        mockGetProjectDetailsErrorPath();
    }

    private void mockGetProjectListHappyPath() {
        Observable<List<GitHubRepo>> observable = Observable.just(gitHubRepoList);
        when(projectListRepository.getProjectList(not(eq(UNLUCKY_ACCOUNT)))).thenReturn(observable);
    }

    private void mockGetProjectListErrorPath() {
        when(projectListRepository.getProjectList(eq(UNLUCKY_ACCOUNT)))
                .thenReturn(Observable.error(new IOException("Invalid account")).cast((Class) List.class));
    }

    private void mockGetProjectDetailsHappyPath() {
        Observable<GitHubRepo> observable = Observable.just(gitHubRepo);
        when(projectListRepository.getProjectDetails(not(eq(UNLUCKY_ACCOUNT)), anyString())).thenReturn(observable);
    }

    private void mockGetProjectDetailsErrorPath() {
        when(projectListRepository.getProjectDetails(eq(UNLUCKY_ACCOUNT), anyString()))
                .thenReturn(Observable.error(new IOException("Invalid account")).cast((Class) List.class));
    }

    private void initializeFakeGitHubRepos() {
        gitHubRepo = new GitHubRepo("RepoItem");
        gitHubRepoList = new ArrayList<>();

        for (int i = 0; i < 10; ++i) {
            gitHubRepoList.add(new GitHubRepo("SampleRepoItem" + i));
        }
    }

    private void initializeRxSchedulers() {
        RxJavaPlugins.setIoSchedulerHandler(scheduler -> Schedulers.trampoline());
    }
}
