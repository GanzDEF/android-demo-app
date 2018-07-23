package com.test.xyz.demo.presentation.projectdetails.presenter;

import com.test.xyz.demo.domain.interactor.project.ProjectInteractor;
import com.test.xyz.demo.domain.model.GitHubRepo;
import com.test.xyz.demo.presentation.BasePresenterTest;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.nullable;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

public class ProjectDetailsPresenterTest extends BasePresenterTest {
    private static final String USER_NAME = "google";
    private static final String PROJECT_ID = "test";

    @Mock ProjectInteractor projectInteractor;
    @Mock ProjectDetailsView projectDetailsView;

    ProjectDetailsPresenter projectDetailsPresenter;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        mockProjectInteractor(projectInteractor);
        projectDetailsPresenter = new ProjectDetailsPresenterImpl(projectDetailsView, projectInteractor);
    }

    @Test
    public void requestProjectDetails_shouldReturnRepoDetails() {
        //GIVEN
        //NOTHING

        //WHEN
        projectDetailsPresenter.requestProjectDetails(USER_NAME, PROJECT_ID);

        //THEN
        verify(projectDetailsView).showProjectDetails(any(GitHubRepo.class));
        verify(projectDetailsView, never()).showError(any(String.class));
    }

    @Test
    public void requestProjectDetails_whenUserNameIsEmpty_shouldReturnError() {
        //GIVEN
        //NOTHING

        //WHEN
        projectDetailsPresenter.requestProjectDetails("", PROJECT_ID);

        //THEN
        verify(projectDetailsView, never()).showProjectDetails(any(GitHubRepo.class));
        verify(projectDetailsView).showError(nullable(String.class));
    }
}
