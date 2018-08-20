package com.test.xyz.demo.presentation.projectdetails.presenter

import com.test.xyz.demo.R
import com.test.xyz.demo.domain.interactor.project.ProjectInteractor
import com.test.xyz.demo.domain.model.github.GitHubRepo
import com.test.xyz.demo.presentation.common.DisposableManager
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableObserver
import javax.inject.Inject

class ProjectDetailsPresenterImpl @Inject
constructor(private val projectDetailsView: ProjectDetailsView, private val projectInteractor: ProjectInteractor) : ProjectDetailsPresenter {
    private val disposableManager: DisposableManager

    init {
        this.disposableManager = DisposableManager()
    }

    override fun requestProjectDetails(userName: String, projectID: String) {
        val disposable = projectInteractor.getProjectDetails(userName, projectID)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableObserver<GitHubRepo>() {
                    override fun onNext(gitHubRepo: GitHubRepo) {
                        projectDetailsView.showProjectDetails(gitHubRepo)
                    }

                    override fun onError(e: Throwable) {
                        projectDetailsView.showError(R.string.project_details_ret_error)
                    }

                    override fun onComplete() {}
                })

        disposableManager.add(disposable)
    }

    override fun onStop() {
        disposableManager.dispose()
    }
}
