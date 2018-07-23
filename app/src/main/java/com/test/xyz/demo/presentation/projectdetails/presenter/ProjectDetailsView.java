package com.test.xyz.demo.presentation.projectdetails.presenter;

import com.test.xyz.demo.domain.model.GitHubRepo;

public interface ProjectDetailsView {
    void showProjectDetails(GitHubRepo gitHubRepo);
    void showError(final String errorMessage);
}
