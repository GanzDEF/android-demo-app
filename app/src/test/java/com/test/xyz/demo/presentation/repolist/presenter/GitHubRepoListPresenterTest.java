package com.test.xyz.demo.presentation.repolist.presenter;

import com.test.xyz.demo.domain.interactor.project.ProjectInteractor;
import com.test.xyz.demo.presentation.BasePresenterTest;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

public class GitHubRepoListPresenterTest extends BasePresenterTest {
    private static final String USER_NAME = "google";

    private RepoListPresenter repoListPresenter;

    @Mock
    RepoListView repoListView;
    @Mock ProjectInteractor projectInteractor;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        mockProjectInteractor(projectInteractor);
        repoListPresenter = new RepoListPresenterImpl(repoListView, projectInteractor);
    }

    @Test
    public void requestRepoList_shouldReturnRepoList() {
        //GIVEN
        //NOTHING

        //WHEN
        repoListPresenter.requestRepoList(USER_NAME);

        //THEN
        verify(repoListView).showRepoList(any(List.class));
        verify(repoListView, never()).showError(any(String.class));
    }

    @Test
    public void requestRepoList_whenUserNameIsEmpty_shouldError() {
        //GIVEN
        //NOTHING

        //WHEN
        repoListPresenter.requestRepoList("");

        //THEN
        verify(repoListView, never()).showRepoList(any(List.class));
        verify(repoListView).showError(any(String.class));
    }
}
