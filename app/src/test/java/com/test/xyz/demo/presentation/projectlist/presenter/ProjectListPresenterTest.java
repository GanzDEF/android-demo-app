package com.test.xyz.demo.presentation.projectlist.presenter;

import com.test.xyz.demo.R;
import com.test.xyz.demo.domain.interactor.project.ProjectInteractor;
import com.test.xyz.demo.domain.model.github.GitHubRepo;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
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

public class ProjectListPresenterTest {
    private static final String USER_NAME = "google";
    private List<GitHubRepo> gitHubRepoList;

    @Mock ProjectListView projectListView;
    @Mock ProjectInteractor projectInteractor;

    ProjectListPresenter projectListPresenter;

    @Before
    public void setup() {
        initializeTest();
        mockGetProjectListAPI(projectInteractor);
        projectListPresenter = new ProjectListPresenterImpl(projectListView, projectInteractor);
    }

    @Test
    public void requestProjectList_shouldReturnRepoList() {
        //WHEN
        projectListPresenter.requestProjectList(USER_NAME);

        //THEN
        verify(projectInteractor).getProjectList(eq(USER_NAME));
        verify(projectListView).showProjectList(gitHubRepoList);
        verify(projectListView, never()).showError(R.string.project_list_ret_error);
    }

    @Test
    public void requestProjectList_whenUserNameIsEmpty_shouldError() {
        //WHEN
        projectListPresenter.requestProjectList("");

        //THEN
        verify(projectInteractor).getProjectList(eq(""));
        verify(projectListView, never()).showProjectList(any(List.class));
        verify(projectListView).showError(R.string.project_list_ret_error);
    }

    //region Helper mocks
    private void mockGetProjectListAPI(ProjectInteractor projectInteractor) {
        Observable<List<GitHubRepo>> observable = Observable.just(gitHubRepoList);

        when(projectInteractor.getProjectList(eq(EMPTY_VALUE)))
                .thenReturn(Observable.error(new IllegalArgumentException("Username must be provided!")).cast((Class) List.class));

        when(projectInteractor.getProjectList(not(eq(EMPTY_VALUE)))).thenReturn(observable);
    }

    private void initializeTest() {
        MockitoAnnotations.initMocks(this);
        RxAndroidPlugins.setInitMainThreadSchedulerHandler(scheduler ->  Schedulers.trampoline());

        gitHubRepoList = new ArrayList<>();

        for (int i = 0; i < 10; ++i) {
            gitHubRepoList.add(new GitHubRepo("Fake gitHubRepo: " + i));
        }
    }

    private static final String EMPTY_VALUE = "";
    //endregion
}
