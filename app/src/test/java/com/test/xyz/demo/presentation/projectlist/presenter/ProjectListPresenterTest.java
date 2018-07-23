package com.test.xyz.demo.presentation.projectlist.presenter;

import com.test.xyz.demo.domain.interactor.project.ProjectInteractor;
import com.test.xyz.demo.presentation.BasePresenterTest;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static com.test.xyz.demo.domain.interactor.project.ProjectInteractor.ProjectActionCallback;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

public class ProjectListPresenterTest extends BasePresenterTest {
    private static final String USER_NAME = "google";

    @Mock ProjectListView projectListView;
    @Mock ProjectInteractor projectInteractor;

    ProjectListPresenter projectListPresenter;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        mockProjectInteractorBehavior(projectInteractor);
        projectListPresenter = new ProjectListPresenterImpl(projectListView, projectInteractor);
    }

    @Test
    public void requestProjectList_shouldReturnRepoList() {
        //WHEN
        projectListPresenter.requestProjectList(USER_NAME);

        //THEN
        verify(projectInteractor).getProjectList(eq(USER_NAME), any(ProjectActionCallback.class));
        verify(projectListView).showProjectList(any(List.class));
        verify(projectListView, never()).showError(any(String.class));
    }

    @Test
    public void requestProjectList_whenUserNameIsEmpty_shouldError() {
        //WHEN
        projectListPresenter.requestProjectList("");

        //THEN
        verify(projectInteractor).getProjectList(eq(""), any(ProjectActionCallback.class));
        verify(projectListView, never()).showProjectList(any(List.class));
        verify(projectListView).showError(any(String.class));
    }
}
