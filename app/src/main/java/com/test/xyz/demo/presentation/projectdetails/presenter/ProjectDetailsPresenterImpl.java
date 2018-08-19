package com.test.xyz.demo.presentation.projectdetails.presenter;

import com.test.xyz.demo.R;
import com.test.xyz.demo.domain.interactor.project.ProjectInteractor;
import com.test.xyz.demo.domain.model.github.GitHubRepo;
import com.test.xyz.demo.presentation.common.DisposableManager;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;

public class ProjectDetailsPresenterImpl implements ProjectDetailsPresenter {
    private final ProjectDetailsView projectDetailsView;
    private final ProjectInteractor projectInteractor;
    private final DisposableManager disposableManager;

    @Inject
    public ProjectDetailsPresenterImpl(ProjectDetailsView projectDetailsView, ProjectInteractor projectInteractor) {
        this.projectDetailsView = projectDetailsView;
        this.projectInteractor = projectInteractor;
        this.disposableManager = new DisposableManager();
    }

    @Override
    public void requestProjectDetails(String userName, String projectID) {
        Disposable disposable = projectInteractor.getProjectDetails(userName, projectID)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<GitHubRepo>() {
                    @Override
                    public void onNext(GitHubRepo gitHubRepo) {
                        projectDetailsView.showProjectDetails(gitHubRepo);
                    }

                    @Override
                    public void onError(Throwable e) {
                        projectDetailsView.showError(R.string.project_details_ret_error);
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
