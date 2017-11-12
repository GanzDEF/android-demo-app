package com.test.xyz.daggersample.ui.repodetails.mvp;

import com.test.xyz.daggersample.domain.repository.api.model.Repo;

public interface OnRepoDetailsCompletedListener {
    void onRepoDetailsRetrievalSuccess(Repo data);

    void onRepoDetailsRetrievalFailure(String errorMessage);
}
