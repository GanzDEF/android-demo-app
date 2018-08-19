package com.test.xyz.demo.domain.interactor.project;

import com.test.xyz.demo.domain.model.github.GitHubRepo;

import java.util.List;

import io.reactivex.Observable;

public interface ProjectInteractor {
    Observable<List<GitHubRepo>> getProjectList(String userName);
    Observable<GitHubRepo> getProjectDetails(String userName, String projectID);
}
