package com.test.xyz.demo.domain.interactor.project;

import com.google.common.base.Strings;
import com.test.xyz.demo.domain.model.github.GitHubRepo;
import com.test.xyz.demo.domain.repository.api.ProjectListRepository;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

class ProjectInteractorImpl implements ProjectInteractor {
    private static final String TAG = ProjectInteractorImpl.class.getName();

    private final ProjectListRepository projectListRepository;

    public ProjectInteractorImpl(ProjectListRepository projectListRepository) {
        this.projectListRepository = projectListRepository;
    }

    @Override
    public Observable<List<GitHubRepo>> getProjectList(String userName) {
        if (Strings.isNullOrEmpty(userName)) {
            return Observable.error(new IllegalArgumentException("Username must be provided!"));
        }

        return projectListRepository.getProjectList(userName)
                .subscribeOn(Schedulers.io());
    }

    @Override
    public Observable<GitHubRepo> getProjectDetails(String userName, String projectID) {
        if (Strings.isNullOrEmpty(userName) || Strings.isNullOrEmpty(projectID)) {
            return Observable.error(new IllegalArgumentException("Username and projectID must be provided!"));
        }

        return projectListRepository.getProjectDetails(userName, projectID)
                .subscribeOn(Schedulers.io());
    }
}
