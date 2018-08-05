package com.test.xyz.demo.presentation.projectdetails.presenter;

import com.test.xyz.demo.R;
import com.test.xyz.demo.domain.interactor.project.ProjectInteractor;
import com.test.xyz.demo.domain.model.github.GitHubRepo;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static com.test.xyz.demo.domain.interactor.project.ProjectInteractor.ProjectActionCallback;
import static org.mockito.AdditionalMatchers.not;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

public class ProjectDetailsPresenterTest {
    private static final String USER_NAME = "google";
    private static final String PROJECT_ID = "test";

    @Mock ProjectInteractor projectInteractor;
    @Mock ProjectDetailsView projectDetailsView;

    ProjectDetailsPresenter projectDetailsPresenter;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        mockGetProjectDetailsAPI(projectInteractor);
        projectDetailsPresenter = new ProjectDetailsPresenterImpl(projectDetailsView, projectInteractor);
    }

    @Test
    public void requestProjectDetails_shouldReturnRepoDetails() {
        //WHEN
        projectDetailsPresenter.requestProjectDetails(USER_NAME, PROJECT_ID);

        //THEN
        verify(projectInteractor).getProjectDetails(eq(USER_NAME), eq(PROJECT_ID), any(ProjectActionCallback.class));
        verify(projectDetailsView).showProjectDetails(any(GitHubRepo.class));
        verify(projectDetailsView, never()).showError(R.string.project_details_ret_error);
    }

    @Test
    public void requestProjectDetails_whenUserNameIsEmpty_shouldReturnError() {
        //WHEN
        projectDetailsPresenter.requestProjectDetails("", PROJECT_ID);

        //THEN
        verify(projectInteractor).getProjectDetails(eq(""), eq(PROJECT_ID), any(ProjectActionCallback.class));
        verify(projectDetailsView, never()).showProjectDetails(any(GitHubRepo.class));
        verify(projectDetailsView).showError(R.string.project_details_ret_error);
    }

    //region Helper Mocks
    private void mockGetProjectDetailsAPI(ProjectInteractor projectInteractor) {
        doAnswer((invocation) -> {
            ((ProjectActionCallback) invocation.getArguments()[2]).onFailure(mock(Throwable.class));
            return null;
        }).when(projectInteractor).getProjectDetails(eq(EMPTY_VALUE), any(String.class), any(ProjectActionCallback.class));

        doAnswer((invocation) -> {
            ((ProjectActionCallback) invocation.getArguments()[2]).onSuccess(new GitHubRepo("Fake gitHubRepo"));
            return null;
        }).when(projectInteractor).getProjectDetails(not(eq(EMPTY_VALUE)), any(String.class), any(ProjectActionCallback.class));
    }

    static final String EMPTY_VALUE = "";
    //endregion
}
