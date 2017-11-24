package com.test.xyz.demo.ui.repodetails.vp;

import com.test.xyz.demo.domain.repository.api.model.Repo;

public interface RepoDetailsView {
    void showRepoDetails(Repo data);

    void showError(final String errorMessage);
}
