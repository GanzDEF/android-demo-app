package com.test.xyz.demo.presentation.repolist.presenter;

import com.test.xyz.demo.domain.interactor.project.ProjectInteractor;
import com.test.xyz.demo.domain.model.GitHubRepo;

import java.util.List;

import javax.inject.Inject;

public class RepoListPresenterImpl implements RepoListPresenter, ProjectInteractor.ProjectActionCallback<List<GitHubRepo>> {
    private RepoListView repoListView;
    private ProjectInteractor projectInteractor;

    @Inject
    public RepoListPresenterImpl(RepoListView repoListView, ProjectInteractor projectInteractor) {
        this.repoListView = repoListView;
        this.projectInteractor = projectInteractor;
    }

    @Override
    public void requestRepoList(String userName) {
        projectInteractor.getProjectList(userName, this);
    }

    @Override
    public void onSuccess(List<GitHubRepo> data) {
        repoListView.showRepoList(data);
    }

    @Override
    public void onFailure(Throwable throwable) {
        repoListView.showError("Unable to show project list. Message: " + throwable.getMessage());
    }
}
