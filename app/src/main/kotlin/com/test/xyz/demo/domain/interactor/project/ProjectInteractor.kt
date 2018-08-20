package com.test.xyz.demo.domain.interactor.project

import com.test.xyz.demo.domain.model.github.GitHubRepo

import io.reactivex.Observable

interface ProjectInteractor {
    fun getProjectList(userName: String): Observable<List<GitHubRepo>>
    fun getProjectDetails(userName: String, projectID: String): Observable<GitHubRepo>
}
