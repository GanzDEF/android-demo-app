package com.test.xyz.daggersample.ui.repolist.mvp;

import com.test.xyz.daggersample.domain.repository.api.model.Repo;

import java.util.List;

public interface RepoListView {
    void showRepoList(List<Repo> values);

    void showError(final String errorMessage);
}
