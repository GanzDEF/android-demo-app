package com.test.xyz.demo.domain.interactor.project;

import com.google.common.base.Strings;
import com.test.xyz.demo.domain.model.GitHubRepo;
import com.test.xyz.demo.domain.repository.api.ProjectListRepository;

import java.util.List;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

class ProjectInteractorImpl implements ProjectInteractor {
    private static final String TAG = ProjectInteractorImpl.class.getName();

    private final ProjectListRepository projectListRepository;

    public ProjectInteractorImpl(ProjectListRepository projectListRepository) {
        this.projectListRepository = projectListRepository;
    }

    @Override
    public void getProjectList(String userName, ProjectActionCallback listener) {
        if (Strings.isNullOrEmpty(userName)) {
            listener.onFailure(new Exception("Username must be provided!"));
            return;
        }

        projectListRepository.getProjectList(userName)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<GitHubRepo>>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        listener.onFailure(e);
                    }

                    @Override
                    public void onNext(List<GitHubRepo> gitHubRepoList) {
                        listener.onSuccess(gitHubRepoList);
                    }
                });
    }

    @Override
    public void getProjectDetails(String userName, String projectID, ProjectActionCallback listener) {
        if (Strings.isNullOrEmpty(userName)) {
            listener.onFailure(new Exception("Username must be provided!"));
            return;
        }

        projectListRepository.getProjectDetails(userName, projectID)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<GitHubRepo>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        listener.onFailure(e);
                    }

                    @Override
                    public void onNext(GitHubRepo gitHubRepo) {
                        listener.onSuccess(gitHubRepo);
                    }
                });
    }
}
