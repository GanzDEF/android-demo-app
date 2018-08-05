package com.test.xyz.demo.domain.interactor.project;

import com.google.common.base.Strings;
import com.test.xyz.demo.domain.model.github.GitHubRepo;
import com.test.xyz.demo.domain.repository.api.ProjectListRepository;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

class ProjectInteractorImpl implements ProjectInteractor {
    private static final String TAG = ProjectInteractorImpl.class.getName();

    private final ProjectListRepository projectListRepository;

    public ProjectInteractorImpl(ProjectListRepository projectListRepository) {
        this.projectListRepository = projectListRepository;
    }

    @Override
    public Disposable getProjectList(String userName, ProjectActionCallback listener) {
        if (Strings.isNullOrEmpty(userName)) {
            listener.onFailure(new Exception("Username must be provided!"));
            return null;
        }

        return projectListRepository.getProjectList(userName)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<List<GitHubRepo>>() {

                    @Override
                    public void onNext(List<GitHubRepo> gitHubRepoList) {
                        listener.onSuccess(gitHubRepoList);
                    }

                    @Override
                    public void onError(Throwable e) {
                        listener.onFailure(e);
                    }

                    @Override
                    public void onComplete() {
                    }
                });
    }

    @Override
    public Disposable getProjectDetails(String userName, String projectID, ProjectActionCallback listener) {
        if (Strings.isNullOrEmpty(userName)) {
            listener.onFailure(new Exception("Username must be provided!"));
            return null;
        }

        return projectListRepository.getProjectDetails(userName, projectID)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<GitHubRepo>() {
                    @Override
                    public void onError(Throwable e) {
                        listener.onFailure(e);
                    }

                    @Override
                    public void onNext(GitHubRepo gitHubRepo) {
                        listener.onSuccess(gitHubRepo);
                    }

                    @Override
                    public void onComplete() {
                    }
                });
    }
}
