package com.test.xyz.demo.presentation.repolist.di;

import com.test.xyz.demo.presentation.common.di.scope.ActivityScope;
import com.test.xyz.demo.presentation.repolist.presenter.RepoListPresenter;
import com.test.xyz.demo.presentation.repolist.presenter.RepoListPresenterImpl;
import com.test.xyz.demo.presentation.repolist.presenter.RepoListView;

import dagger.Module;
import dagger.Provides;

@Module
public class RepoListFragmentModule {
    public RepoListFragmentModule(RepoListView view) {
        this.view = view;
    }

    @Provides
    @ActivityScope
    RepoListView provideRepoListView() {
        return this.view;
    }

    @Provides
    @ActivityScope
    RepoListPresenter provideRepoListPresenter(RepoListPresenterImpl presenter) {
        return presenter;
    }

    private final RepoListView view;
}
