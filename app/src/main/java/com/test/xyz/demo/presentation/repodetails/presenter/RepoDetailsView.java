package com.test.xyz.demo.presentation.repodetails.presenter;

import com.test.xyz.demo.domain.model.GitHubRepo;

public interface RepoDetailsView {
    void showRepoDetails(GitHubRepo data);
    void showError(final String errorMessage);
}
