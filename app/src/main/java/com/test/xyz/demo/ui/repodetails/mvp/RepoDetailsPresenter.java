package com.test.xyz.demo.ui.repodetails.mvp;

public interface RepoDetailsPresenter extends OnRepoDetailsCompletedListener {
    void requestRepoDetails(String userName, String projectID);
}
