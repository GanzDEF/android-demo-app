package com.test.xyz.demo.presentation.repolist.presenter;

import com.test.xyz.demo.domain.model.GitHubRepo;

import java.util.List;

public interface RepoListView {
    void showRepoList(List<GitHubRepo> values);

    void showError(final String errorMessage);
}
