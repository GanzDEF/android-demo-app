package com.test.xyz.demo.presentation.projectdetails.presenter;

import com.test.xyz.demo.R;
import com.test.xyz.demo.domain.interactor.project.ProjectInteractor;
import com.test.xyz.demo.domain.model.github.GitHubRepo;
import com.test.xyz.demo.presentation.common.DisposableManager;

import javax.inject.Inject;

public class ProjectDetailsPresenterImpl implements ProjectDetailsPresenter, ProjectInteractor.ProjectActionCallback<GitHubRepo> {
    private final ProjectDetailsView projectDetailsView;
    private final ProjectInteractor projectInteractor;
    private final DisposableManager disposableManager;

    @Inject
    public ProjectDetailsPresenterImpl(ProjectDetailsView projectDetailsView, ProjectInteractor projectInteractor) {
        this.projectDetailsView = projectDetailsView;
        this.projectInteractor = projectInteractor;
        this.disposableManager = new DisposableManager();
    }

    @Override
    public void requestProjectDetails(String userName, String projectID) {
        disposableManager.add(
            projectInteractor.getProjectDetails(userName, projectID, this)
        );
    }

    @Override
    public void onStop() {
        disposableManager.dispose();
    }

    @Override
    public void onSuccess(GitHubRepo data) {
        projectDetailsView.showProjectDetails(data);
    }

    @Override
    public void onFailure(Throwable throwable) {
        projectDetailsView.showError(R.string.project_details_ret_error);
    }
}
