package com.test.xyz.demo.ui.repolist.mvp;

import com.test.xyz.demo.domain.interactor.MainInteractor;
import com.test.xyz.demo.ui.BasePresenterTest;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

public class RepoListPresenterTest extends BasePresenterTest {
    private static final String USER_NAME = "google";

    private RepoListPresenter repoListPresenter;

    @Mock
    private RepoListView repoListView;

    @Mock
    private MainInteractor mainInteractor;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        mockInteractor(mainInteractor);
        repoListPresenter = new RepoListPresenterImpl(repoListView, mainInteractor);
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
