package com.test.xyz.demo.presentation.projectdetails.presenter;

import com.test.xyz.demo.R;
import com.test.xyz.demo.domain.interactor.project.ProjectInteractor;
import com.test.xyz.demo.domain.model.github.GitHubRepo;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.plugins.RxAndroidPlugins;
import io.reactivex.schedulers.Schedulers;

import static org.mockito.AdditionalMatchers.not;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class ProjectDetailsPresenterTest {
    private static final String EMPTY_VALUE = "";
    private static final String USER_NAME = "google";
    private static final String PROJECT_ID = "test";
    private GitHubRepo gitHubRepo;

    @Mock ProjectInteractor projectInteractor;
    @Mock ProjectDetailsView projectDetailsView;

    ProjectDetailsPresenter projectDetailsPresenter;

    @Before
    public void setup() {
        initializeTest();
        mockGetProjectDetailsAPI(projectInteractor);
        projectDetailsPresenter = new ProjectDetailsPresenterImpl(projectDetailsView, projectInteractor);
    }

    @Test
    public void requestProjectDetails_shouldReturnRepoDetails() {
        //WHEN
        projectDetailsPresenter.requestProjectDetails(USER_NAME, PROJECT_ID);

        //THEN
        verify(projectInteractor).getProjectDetails(eq(USER_NAME), eq(PROJECT_ID));
        verify(projectDetailsView).showProjectDetails(gitHubRepo);
        verify(projectDetailsView, never()).showError(R.string.project_details_ret_error);
    }

    @Test
    public void requestProjectDetails_whenUserNameIsEmpty_shouldReturnError() {
        //WHEN
        projectDetailsPresenter.requestProjectDetails("", PROJECT_ID);

        //THEN
        verify(projectInteractor).getProjectDetails(eq(""), eq(PROJECT_ID));
        verify(projectDetailsView, never()).showProjectDetails(any(GitHubRepo.class));
        verify(projectDetailsView).showError(R.string.project_details_ret_error);
    }

    @Test
    public void requestProjectDetails_whenProjectIdIsEmpty_shouldReturnError() {
        //WHEN
        projectDetailsPresenter.requestProjectDetails(USER_NAME, "");

        //THEN
        verify(projectInteractor).getProjectDetails(eq(USER_NAME), eq(""));
        verify(projectDetailsView, never()).showProjectDetails(any(GitHubRepo.class));
        verify(projectDetailsView).showError(R.string.project_details_ret_error);
    }

    //region Helper Mocks
    private void mockGetProjectDetailsAPI(ProjectInteractor projectInteractor) {
        Observable<GitHubRepo> observable = Observable.just(gitHubRepo);

        when(projectInteractor.getProjectDetails(eq(EMPTY_VALUE), any(String.class)))
                .thenReturn((Observable<GitHubRepo>) Observable.error(new IllegalArgumentException("Username must be provided!")).cast((Class) List.class));

        when(projectInteractor.getProjectDetails(any(String.class), eq(EMPTY_VALUE)))
                .thenReturn((Observable<GitHubRepo>) Observable.error(new IllegalArgumentException("Project ID must be provided!")).cast((Class) List.class));

        when(projectInteractor.getProjectDetails(not(eq(EMPTY_VALUE)), not(eq(EMPTY_VALUE)))).thenReturn(observable);
    }

    private void initializeTest() {
        MockitoAnnotations.initMocks(this);
        RxAndroidPlugins.setInitMainThreadSchedulerHandler(scheduler ->  Schedulers.trampoline());
        gitHubRepo = new GitHubRepo("Fake gitHubRepo");
    }
    //endregion
}
