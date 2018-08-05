package com.test.xyz.demo.presentation.projectlist.presenter;

import com.test.xyz.demo.R;
import com.test.xyz.demo.domain.interactor.project.ProjectInteractor;
import com.test.xyz.demo.domain.model.github.GitHubRepo;
import com.test.xyz.demo.presentation.common.DisposableManager;

import java.util.List;

import javax.inject.Inject;

public class ProjectListPresenterImpl implements ProjectListPresenter, ProjectInteractor.ProjectActionCallback<List<GitHubRepo>> {
    private final ProjectListView projectListView;
    private final ProjectInteractor projectInteractor;
    private final DisposableManager disposableManager;

    @Inject
    public ProjectListPresenterImpl(ProjectListView projectListView, ProjectInteractor projectInteractor) {
        this.projectListView = projectListView;
        this.projectInteractor = projectInteractor;
        this.disposableManager = new DisposableManager();
    }

    @Override
    public void requestProjectList(String userName) {
        disposableManager.add(
                projectInteractor.getProjectList(userName, this)
        );
    }

    @Override
    public void onStop() {
        disposableManager.dispose();
    }

    @Override
    public void onSuccess(List<GitHubRepo> data) {
        projectListView.showProjectList(data);
    }

    @Override
    public void onFailure(Throwable throwable) {
        projectListView.showError(R.string.project_list_ret_error);
    }
}
