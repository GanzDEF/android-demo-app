package com.test.xyz.demo.presentation.projectlist.presenter;

import com.test.xyz.demo.domain.model.github.GitHubRepo;

import java.util.List;

public interface ProjectListView {
    void showProjectList(List<GitHubRepo> projectList);
    void showError(final String errorMessage);
}
