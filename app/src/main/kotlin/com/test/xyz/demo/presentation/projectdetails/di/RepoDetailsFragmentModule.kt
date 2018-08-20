package com.test.xyz.demo.presentation.projectdetails.di

import com.test.xyz.demo.presentation.common.di.scope.ActivityScope
import com.test.xyz.demo.presentation.projectdetails.presenter.ProjectDetailsPresenter
import com.test.xyz.demo.presentation.projectdetails.presenter.ProjectDetailsPresenterImpl
import com.test.xyz.demo.presentation.projectdetails.presenter.ProjectDetailsView
import dagger.Module
import dagger.Provides

@Module
class RepoDetailsFragmentModule(private val view: ProjectDetailsView) {

    @Provides
    @ActivityScope
    internal fun provideRepoDetailsView(): ProjectDetailsView {
        return this.view
    }

    @Provides
    @ActivityScope
    internal fun provideRepoDetailsPresenter(presenter: ProjectDetailsPresenterImpl): ProjectDetailsPresenter {
        return presenter
    }
}
