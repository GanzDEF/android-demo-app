package com.test.xyz.demo.ui.repolist.vp;

import com.test.xyz.demo.domain.repository.api.model.Repo;

import java.util.List;

public interface RepoListView {
    void showRepoList(List<Repo> values);

    void showError(final String errorMessage);
}
