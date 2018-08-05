package com.test.xyz.demo.presentation.projectdetails.presenter;

import com.test.xyz.demo.domain.interactor.project.ProjectInteractor;
import com.test.xyz.demo.domain.model.github.GitHubRepo;

import javax.inject.Inject;

public class ProjectDetailsPresenterImpl implements ProjectDetailsPresenter, ProjectInteractor.ProjectActionCallback<GitHubRepo> {
    private final ProjectDetailsView projectDetailsView;
    private final ProjectInteractor projectInteractor;

    @Inject
    public ProjectDetailsPresenterImpl(ProjectDetailsView projectDetailsView, ProjectInteractor projectInteractor) {
        this.projectDetailsView = projectDetailsView;
        this.projectInteractor = projectInteractor;
    }

    @Override
    public void requestProjectDetails(String userName, String projectID) {
        projectInteractor.getProjectDetails(userName, projectID, this);
    }

    @Override
    public void onSuccess(GitHubRepo data) {
        projectDetailsView.showProjectDetails(data);
    }

    @Override
    public void onFailure(Throwable throwable) {
        projectDetailsView.showError("Unable to show project details. Message: " + throwable.getMessage());
    }
}
