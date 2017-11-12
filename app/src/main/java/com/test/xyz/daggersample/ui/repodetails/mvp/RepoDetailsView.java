package com.test.xyz.daggersample.ui.repodetails.mvp;

import com.test.xyz.daggersample.domain.repository.api.model.Repo;

public interface RepoDetailsView {
    void showRepoDetails(Repo data);

    void showError(final String errorMessage);
}
