package com.test.xyz.demo.ui.repodetails.mvp;

import com.test.xyz.demo.domain.interactor.MainInteractor;
import com.test.xyz.demo.domain.repository.api.model.Repo;
import com.test.xyz.demo.ui.BasePresenterTest;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.nullable;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

public class RepoDetailsPresenterTest  extends BasePresenterTest {
    private static final String USER_NAME = "google";
    private static final String PROJECT_ID = "test";

    private RepoDetailsPresenter repoDetailsPresenter;

    @Mock
    private MainInteractor mainInteractor;

    @Mock
    private RepoDetailsView repoDetailsView;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        mockInteractor(mainInteractor);
        repoDetailsPresenter = new RepoDetailsPresenterImpl(repoDetailsView, mainInteractor);
    }

    @Test
    public void requestRepoDetails_shouldReturnRepoDetails() {
        //GIVEN
        //NOTHING

        //WHEN
        repoDetailsPresenter.requestRepoDetails(USER_NAME, PROJECT_ID);

        //THEN
        verify(repoDetailsView).showRepoDetails(any(Repo.class));
        verify(repoDetailsView, never()).showError(any(String.class));
    }

    @Test
    public void requestRepoDetails_whenUserNameIsEmpty_shouldReturnError() {
        //GIVEN
        //NOTHING

        //WHEN
        repoDetailsPresenter.requestRepoDetails("", PROJECT_ID);

        //THEN
        verify(repoDetailsView, never()).showRepoDetails(any(Repo.class));
        verify(repoDetailsView).showError(nullable(String.class));
    }
}
