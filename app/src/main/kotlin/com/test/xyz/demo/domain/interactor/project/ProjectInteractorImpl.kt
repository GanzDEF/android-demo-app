package com.test.xyz.demo.domain.interactor.project

import com.google.common.base.Strings
import com.test.xyz.demo.domain.model.github.GitHubRepo
import com.test.xyz.demo.domain.repository.api.ProjectListRepository

import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers

internal class ProjectInteractorImpl(private val projectListRepository: ProjectListRepository) : ProjectInteractor {

    override fun getProjectList(userName: String): Observable<List<GitHubRepo>> {
        return if (Strings.isNullOrEmpty(userName)) {
            Observable.error(IllegalArgumentException("Username must be provided!"))
        } else projectListRepository.getProjectList(userName)
                .subscribeOn(Schedulers.io())

    }

    override fun getProjectDetails(userName: String, projectID: String): Observable<GitHubRepo> {
        return if (Strings.isNullOrEmpty(userName) || Strings.isNullOrEmpty(projectID)) {
            Observable.error(IllegalArgumentException("Username and projectID must be provided!"))
        } else projectListRepository.getProjectDetails(userName, projectID)
                .subscribeOn(Schedulers.io())

    }

}
