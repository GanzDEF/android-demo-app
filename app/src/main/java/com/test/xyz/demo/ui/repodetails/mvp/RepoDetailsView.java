package com.test.xyz.demo.ui.repodetails.mvp;

import com.test.xyz.demo.domain.repository.api.model.Repo;

public interface RepoDetailsView {
    void showRepoDetails(Repo data);

    void showError(final String errorMessage);
}
