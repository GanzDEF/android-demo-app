package com.test.xyz.demo.presentation.projectdetails.di;

import com.test.xyz.demo.presentation.common.di.scope.ActivityScope;
import com.test.xyz.demo.presentation.projectdetails.presenter.ProjectDetailsView;
import com.test.xyz.demo.presentation.projectdetails.presenter.ProjectDetailsPresenter;
import com.test.xyz.demo.presentation.projectdetails.presenter.ProjectDetailsPresenterImpl;

import dagger.Module;
import dagger.Provides;

@Module
public class RepoDetailsFragmentModule {
    private final ProjectDetailsView view;

    public RepoDetailsFragmentModule(ProjectDetailsView view) {
        this.view = view;
    }

    @Provides
    @ActivityScope
    ProjectDetailsView provideRepoDetailsView() {
        return this.view;
    }

    @Provides
    @ActivityScope
    ProjectDetailsPresenter provideRepoDetailsPresenter(ProjectDetailsPresenterImpl presenter) {
        return presenter;
    }
}
