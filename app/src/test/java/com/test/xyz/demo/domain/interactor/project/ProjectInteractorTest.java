package com.test.xyz.demo.domain.interactor.project;

import com.test.xyz.demo.domain.model.github.GitHubRepo;
import com.test.xyz.demo.domain.repository.api.ProjectListRepository;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Scheduler;
import rx.android.plugins.RxAndroidPlugins;
import rx.android.plugins.RxAndroidSchedulersHook;
import rx.plugins.RxJavaPlugins;
import rx.plugins.RxJavaSchedulersHook;
import rx.schedulers.Schedulers;

import static com.test.xyz.demo.domain.interactor.project.ProjectInteractor.ProjectActionCallback;
import static org.mockito.AdditionalMatchers.not;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class ProjectInteractorTest {
    static final String USER_NAME = "hazems";
    static final String PROJECT_ID = "Test";
    static final String UNLUCKY_ACCOUNT = "UNLUCKY_ACCOUNT";

    @Mock ProjectActionCallback projectActionCallback;
    @Mock ProjectListRepository projectListRepository;

    ProjectInteractorImpl testSubject;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        setupRxSchedulers();
        testSubject = new ProjectInteractorImpl(projectListRepository);
    }

    @Test
    public void getProjectList_whenUserNameIsCorrect_shouldReturnRepoListInfo() throws Exception {
        //GIVEN
        mockGetProjectListAPI();

        //WHEN
        testSubject.getProjectList(USER_NAME, projectActionCallback);

        //THEN
        verify(projectActionCallback).onSuccess(any(List.class));
    }

    @Test
    public void getProjectList_whenUserNameIsEmpty_shouldReturnValidationError() throws Exception {
        //GIVEN
        mockGetProjectListAPI();

        //WHEN
        testSubject.getProjectList("", projectActionCallback);

        //THEN
        verify(projectActionCallback).onFailure(any(Throwable.class));
    }

    @Test
    public void getProjectList_whenNetworkErrorHappen_shouldReturnFailureError() throws Exception {
        //GIVEN
        mockGetProjectListAPI();

        //WHEN
        testSubject.getProjectList(UNLUCKY_ACCOUNT, projectActionCallback);

        //THEN
        verify(projectActionCallback).onFailure(any(Throwable.class));
    }

    @Test
    public void getProjectDetails_whenUserNameAndProjectIDAreCorrect_shouldReturnRepoItemInfo() throws Exception {
        //GIVEN
        mockGetProjectDetailsAPI();

        //WHEN
        testSubject.getProjectDetails(USER_NAME, PROJECT_ID, projectActionCallback);

        //THEN
        verify(projectActionCallback).onSuccess(any(GitHubRepo.class));
    }

    @Test
    public void getProjectDetails_whenUserNameIsEmpty_shouldReturnValidationError() throws Exception {
        //GIVEN
        mockGetProjectDetailsAPI();

        // WHEN
        testSubject.getProjectDetails("", PROJECT_ID, projectActionCallback);

        //THEN
        verify(projectActionCallback).onFailure(any(Throwable.class));
    }

    @Test
    public void getProjectDetails_whenProjectIDIsEmpty_shouldReturnValidationError() throws Exception {
        //GIVEN
        mockGetProjectDetailsAPI();

        // WHEN
        testSubject.getProjectDetails("", PROJECT_ID, projectActionCallback);

        //THEN
        verify(projectActionCallback).onFailure(any(Throwable.class));
    }

    @Test
    public void getProjectDetails_whenNetworkErrorHappen_shouldReturnFailureError() throws Exception {
        //GIVEN
        mockGetProjectDetailsAPI();

        // WHEN
        testSubject.getProjectDetails(UNLUCKY_ACCOUNT, PROJECT_ID, projectActionCallback);

        //THEN
        verify(projectActionCallback).onFailure(any(Throwable.class));
    }

    @After
    public void tearDown() {
        RxAndroidPlugins.getInstance().reset();
        RxJavaPlugins.getInstance().reset();
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
        List<GitHubRepo> gitHubRepoList = new ArrayList<>();
        Observable<List<GitHubRepo>> observable = Observable.just(gitHubRepoList);

        when(projectListRepository.getProjectList(not(eq(UNLUCKY_ACCOUNT)))).thenReturn(observable);
    }

    private void mockGetProjectListErrorPath() {
        when(projectListRepository.getProjectList(eq(UNLUCKY_ACCOUNT)))
                .thenReturn(Observable.error(new IOException("Invalid account"))
                        .cast((Class) List.class));
    }

    private void mockGetProjectDetailsHappyPath() {
        GitHubRepo gitHubRepo = new GitHubRepo("SampleRepoItem");
        Observable<GitHubRepo> observable = Observable.just(gitHubRepo);

        when(projectListRepository.getProjectDetails(not(eq(UNLUCKY_ACCOUNT)), anyString())).thenReturn(observable);
    }

    private void mockGetProjectDetailsErrorPath() {
        when(projectListRepository.getProjectDetails(eq(UNLUCKY_ACCOUNT), anyString()))
                .thenReturn(Observable.error(
                        new IOException("Invalid account"))
                        .cast((Class) List.class));
    }

    private void setupRxSchedulers() {
        RxJavaPlugins.getInstance().reset();
        RxJavaPlugins.getInstance().registerSchedulersHook(new RxJavaSchedulersHook() {
            @Override
            public Scheduler getIOScheduler() {
                return Schedulers.immediate();
            }
        });
        RxAndroidPlugins.getInstance().reset();
        RxAndroidPlugins.getInstance().registerSchedulersHook(new RxAndroidSchedulersHook() {
            @Override
            public Scheduler getMainThreadScheduler() {
                return Schedulers.immediate();
            }
        });
    }
}
