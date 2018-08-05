package com.test.xyz.demo.domain.interactor.project;

import io.reactivex.disposables.Disposable;

public interface ProjectInteractor {
    interface ProjectActionCallback<T> {
        void onSuccess(T data);
        void onFailure(Throwable throwable);
    }

    Disposable getProjectList(String userName, ProjectActionCallback listener);
    Disposable getProjectDetails(String userName, String projectID, ProjectActionCallback listener);
}
