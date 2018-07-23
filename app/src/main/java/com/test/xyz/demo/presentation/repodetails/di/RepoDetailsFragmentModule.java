package com.test.xyz.demo.presentation.repodetails.di;

import com.test.xyz.demo.presentation.common.di.scope.ActivityScope;
import com.test.xyz.demo.presentation.repodetails.presenter.RepoDetailsPresenter;
import com.test.xyz.demo.presentation.repodetails.presenter.RepoDetailsPresenterImpl;
import com.test.xyz.demo.presentation.repodetails.presenter.RepoDetailsView;

import dagger.Module;
import dagger.Provides;

@Module
public class RepoDetailsFragmentModule {
    private final RepoDetailsView view;

    public RepoDetailsFragmentModule(RepoDetailsView view) {
        this.view = view;
    }

    @Provides
    @ActivityScope
    RepoDetailsView provideRepoDetailsView() {
        return this.view;
    }

    @Provides
    @ActivityScope
    RepoDetailsPresenter provideRepoDetailsPresenter(RepoDetailsPresenterImpl presenter) {
        return presenter;
    }
}
