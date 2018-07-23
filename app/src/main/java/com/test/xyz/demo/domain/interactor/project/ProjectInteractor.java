package com.test.xyz.demo.domain.interactor.project;

public interface ProjectInteractor {
    interface ProjectActionCallback<T> {
        void onSuccess(T data);
        void onFailure(Throwable throwable);
    }

    void getProjectList(String userName, ProjectActionCallback listener);
    void getProjectDetails(String userName, String projectID, ProjectActionCallback listener);
}
