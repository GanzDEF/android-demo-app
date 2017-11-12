package com.test.xyz.daggersample.ui.repodetails.mvp;

public interface RepoDetailsPresenter extends OnRepoDetailsCompletedListener {
    void requestRepoDetails(String userName, String projectID);
}
