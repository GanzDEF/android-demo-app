package com.test.xyz.demo.ui.repolist.mvp;

import com.test.xyz.demo.domain.repository.api.model.Repo;

import java.util.List;

public interface OnRepoListCompletedListener {
    void onRepoListRetrievalSuccess(List<Repo> data);

    void onRepoListRetrievalFailure(String errorMessage);
}
