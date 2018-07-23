package com.test.xyz.demo.presentation.repodetails.presenter;

import com.test.xyz.demo.domain.interactor.project.ProjectInteractor;
import com.test.xyz.demo.domain.model.GitHubRepo;

import javax.inject.Inject;

public class RepoDetailsPresenterImpl implements RepoDetailsPresenter, ProjectInteractor.ProjectActionCallback<GitHubRepo> {
    private RepoDetailsView repoDetailsView;
    private ProjectInteractor projectInteractor;

    @Inject
    public RepoDetailsPresenterImpl(RepoDetailsView repoDetailsView, ProjectInteractor projectInteractor) {
        this.repoDetailsView = repoDetailsView;
        this.projectInteractor = projectInteractor;
    }

    @Override
    public void requestRepoDetails(String userName, String projectID) {
        projectInteractor.getProjectDetails(userName, projectID, this);
    }

    @Override
    public void onSuccess(GitHubRepo data) {
        repoDetailsView.showRepoDetails(data);
    }

    @Override
    public void onFailure(Throwable throwable) {
        repoDetailsView.showError("Unable to show project details. Message: " + throwable.getMessage());
    }
}
