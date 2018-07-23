package com.test.xyz.demo.presentation.projectlist.di;

import com.test.xyz.demo.presentation.common.di.scope.ActivityScope;
import com.test.xyz.demo.presentation.projectlist.presenter.ProjectListPresenter;
import com.test.xyz.demo.presentation.projectlist.presenter.ProjectListPresenterImpl;
import com.test.xyz.demo.presentation.projectlist.presenter.ProjectListView;

import dagger.Module;
import dagger.Provides;

@Module
public class ProjectListFragmentModule {
    private final ProjectListView view;

    public ProjectListFragmentModule(ProjectListView view) {
        this.view = view;
    }

    @Provides
    @ActivityScope
    ProjectListView provideRepoListView() {
        return this.view;
    }

    @Provides
    @ActivityScope
    ProjectListPresenter provideProjectListPresenter(ProjectListPresenterImpl presenter) {
        return presenter;
    }
}
