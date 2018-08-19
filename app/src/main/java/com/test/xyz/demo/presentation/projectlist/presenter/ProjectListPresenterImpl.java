package com.test.xyz.demo.presentation.projectlist.presenter;

import com.test.xyz.demo.R;
import com.test.xyz.demo.domain.interactor.project.ProjectInteractor;
import com.test.xyz.demo.domain.model.github.GitHubRepo;
import com.test.xyz.demo.presentation.common.DisposableManager;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;

public class ProjectListPresenterImpl implements ProjectListPresenter {
    private final ProjectListView projectListView;
    private final ProjectInteractor projectInteractor;
    private final DisposableManager disposableManager;

    @Inject
    public ProjectListPresenterImpl(ProjectListView projectListView, ProjectInteractor projectInteractor) {
        this.projectListView = projectListView;
        this.projectInteractor = projectInteractor;
        this.disposableManager = new DisposableManager();
    }

    @Override
    public void requestProjectList(String userName) {
        Disposable disposable = projectInteractor.getProjectList(userName)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<List<GitHubRepo>>() {
                    @Override
                    public void onNext(List<GitHubRepo> gitHubRepos) {
                        projectListView.showProjectList(gitHubRepos);
                    }

                    @Override
                    public void onError(Throwable e) {
                        projectListView.showError(R.string.project_list_ret_error);
                    }

                    @Override
                    public void onComplete() {
                    }
                });

        disposableManager.add(disposable);
    }

    @Override
    public void onStop() {
        disposableManager.dispose();
    }
}
