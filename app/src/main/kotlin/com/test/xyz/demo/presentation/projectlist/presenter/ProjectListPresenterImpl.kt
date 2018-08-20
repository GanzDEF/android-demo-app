package com.test.xyz.demo.presentation.projectlist.presenter

import com.test.xyz.demo.R
import com.test.xyz.demo.domain.interactor.project.ProjectInteractor
import com.test.xyz.demo.domain.model.github.GitHubRepo
import com.test.xyz.demo.presentation.common.DisposableManager
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableObserver
import javax.inject.Inject

class ProjectListPresenterImpl @Inject
constructor(private val projectListView: ProjectListView, private val projectInteractor: ProjectInteractor) : ProjectListPresenter {
    private val disposableManager: DisposableManager

    init {
        this.disposableManager = DisposableManager()
    }

    override fun requestProjectList(userName: String) {
        val disposable = projectInteractor.getProjectList(userName)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableObserver<List<GitHubRepo>>() {
                    override fun onNext(gitHubRepos: List<GitHubRepo>) {
                        projectListView.showProjectList(gitHubRepos)
                    }

                    override fun onError(e: Throwable) {
                        projectListView.showError(R.string.project_list_ret_error)
                    }

                    override fun onComplete() {}
                })

        disposableManager.add(disposable)
    }

    override fun onStop() {
        disposableManager.dispose()
    }
}
