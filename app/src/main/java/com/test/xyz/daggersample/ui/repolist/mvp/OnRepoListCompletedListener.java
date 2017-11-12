package com.test.xyz.daggersample.ui.repolist.mvp;

import com.test.xyz.daggersample.domain.repository.api.model.Repo;

import java.util.List;

public interface OnRepoListCompletedListener {
    void onRepoListRetrievalSuccess(List<Repo> data);

    void onRepoListRetrievalFailure(String errorMessage);
}
