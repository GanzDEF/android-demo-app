package com.test.xyz.demo.ui.repodetails.vp;

import com.test.xyz.demo.domain.repository.api.model.Repo;

public interface OnRepoDetailsCompletedListener {
    void onRepoDetailsRetrievalSuccess(Repo data);

    void onRepoDetailsRetrievalFailure(String errorMessage);
}
