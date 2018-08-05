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

import static com.test.xyz.demo.domain.interactor.project.ProjectInteractor.ProjectActionCallback;
import static org.mockito.AdditionalMatchers.not;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

public class ProjectListPresenterTest {
    private static final String USER_NAME = "google";

    @Mock ProjectListView projectListView;
    @Mock ProjectInteractor projectInteractor;

    ProjectListPresenter projectListPresenter;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        mockGetProjectListAPI(projectInteractor);
        projectListPresenter = new ProjectListPresenterImpl(projectListView, projectInteractor);
    }

    @Test
    public void requestProjectList_shouldReturnRepoList() {
        //WHEN
        projectListPresenter.requestProjectList(USER_NAME);

        //THEN
        verify(projectInteractor).getProjectList(eq(USER_NAME), any(ProjectActionCallback.class));
        verify(projectListView).showProjectList(any(List.class));
        verify(projectListView, never()).showError(R.string.project_list_ret_error);
    }

    @Test
    public void requestProjectList_whenUserNameIsEmpty_shouldError() {
        //WHEN
        projectListPresenter.requestProjectList("");

        //THEN
        verify(projectInteractor).getProjectList(eq(""), any(ProjectActionCallback.class));
        verify(projectListView, never()).showProjectList(any(List.class));
        verify(projectListView).showError(R.string.project_list_ret_error);
    }

    //region Helper mocks
    private void mockGetProjectListAPI(ProjectInteractor projectInteractor) {
        doAnswer((invocation) -> {
            ((ProjectActionCallback) invocation.getArguments()[1]).onFailure(mock(Throwable.class));
            return null;
        }).when(projectInteractor).getProjectList(eq(EMPTY_VALUE), any(ProjectActionCallback.class));

        doAnswer((invocation) -> {
            ((ProjectActionCallback) invocation.getArguments()[1]).onSuccess(getFakeRepoList());
            return null;
        }).when(projectInteractor).getProjectList(not(eq(EMPTY_VALUE)), any(ProjectActionCallback.class));
    }

    private List<GitHubRepo> getFakeRepoList() {
        List<GitHubRepo> gitHubRepoList = new ArrayList<>();
        gitHubRepoList.add(new GitHubRepo("Fake gitHubRepo"));
        return gitHubRepoList;
    }

    static final String EMPTY_VALUE = "";
    //endregion
}
