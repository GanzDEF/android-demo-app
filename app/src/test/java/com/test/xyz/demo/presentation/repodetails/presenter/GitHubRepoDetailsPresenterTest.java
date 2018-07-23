package com.test.xyz.demo.presentation.repodetails.presenter;

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

public class GitHubRepoDetailsPresenterTest extends BasePresenterTest {
    private static final String USER_NAME = "google";
    private static final String PROJECT_ID = "test";

    private RepoDetailsPresenter repoDetailsPresenter;

    @Mock private ProjectInteractor projectInteractor;

    @Mock
    private RepoDetailsView repoDetailsView;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        mockProjectInteractor(projectInteractor);
        repoDetailsPresenter = new RepoDetailsPresenterImpl(repoDetailsView, projectInteractor);
    }

    @Test
    public void requestRepoDetails_shouldReturnRepoDetails() {
        //GIVEN
        //NOTHING

        //WHEN
        repoDetailsPresenter.requestRepoDetails(USER_NAME, PROJECT_ID);

        //THEN
        verify(repoDetailsView).showRepoDetails(any(GitHubRepo.class));
        verify(repoDetailsView, never()).showError(any(String.class));
    }

    @Test
    public void requestRepoDetails_whenUserNameIsEmpty_shouldReturnError() {
        //GIVEN
        //NOTHING

        //WHEN
        repoDetailsPresenter.requestRepoDetails("", PROJECT_ID);

        //THEN
        verify(repoDetailsView, never()).showRepoDetails(any(GitHubRepo.class));
        verify(repoDetailsView).showError(nullable(String.class));
    }
}
