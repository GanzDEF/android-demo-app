package com.test.xyz.demo.ui.repolist.vp;

import com.test.xyz.demo.domain.interactor.MainInteractor;
import com.test.xyz.demo.domain.repository.api.model.Repo;

import java.util.List;

import javax.inject.Inject;

public class RepoListPresenterImpl implements RepoListPresenter, OnRepoListCompletedListener {
    private RepoListView repoListView;
    private MainInteractor mainInteractor;

    @Inject
    public RepoListPresenterImpl(RepoListView repoListView, MainInteractor mainInteractor) {
        this.repoListView = repoListView;
        this.mainInteractor = mainInteractor;
    }

    @Override
    public void requestRepoList(String userName) {
        mainInteractor.getRepoList(userName, this);
    }

    @Override
    public void onRepoListRetrievalSuccess(List<Repo> data) {
        repoListView.showRepoList(data);
    }

    @Override
    public void onRepoListRetrievalFailure(String errorMessage) {
        repoListView.showError(errorMessage);
    }
}
