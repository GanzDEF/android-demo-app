package com.test.xyz.demo.presentation.projectlist.di

import com.test.xyz.demo.presentation.common.di.scope.ActivityScope
import com.test.xyz.demo.presentation.projectlist.presenter.ProjectListPresenter
import com.test.xyz.demo.presentation.projectlist.presenter.ProjectListPresenterImpl
import com.test.xyz.demo.presentation.projectlist.presenter.ProjectListView

import dagger.Module
import dagger.Provides

@Module
class ProjectListFragmentModule(private val view: ProjectListView) {

    @Provides
    @ActivityScope
    internal fun provideRepoListView(): ProjectListView {
        return this.view
    }

    @Provides
    @ActivityScope
    internal fun provideProjectListPresenter(presenter: ProjectListPresenterImpl): ProjectListPresenter {
        return presenter
    }
}
