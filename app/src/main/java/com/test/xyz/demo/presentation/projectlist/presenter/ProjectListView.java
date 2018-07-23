package com.test.xyz.demo.presentation.projectlist.presenter;

import com.test.xyz.demo.domain.model.GitHubRepo;

import java.util.List;

public interface ProjectListView {
    void showProjectList(List<GitHubRepo> projectList);
    void showError(final String errorMessage);
}
