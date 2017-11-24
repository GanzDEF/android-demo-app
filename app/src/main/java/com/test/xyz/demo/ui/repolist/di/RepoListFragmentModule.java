package com.test.xyz.demo.ui.repolist.di;

import com.test.xyz.demo.ui.common.di.scope.ActivityScope;
import com.test.xyz.demo.domain.interactor.MainInteractor;
import com.test.xyz.demo.domain.interactor.MainInteractorImpl;
import com.test.xyz.demo.ui.repolist.vp.RepoListPresenter;
import com.test.xyz.demo.ui.repolist.vp.RepoListPresenterImpl;
import com.test.xyz.demo.ui.repolist.vp.RepoListView;

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
    MainInteractor provideMainInteractor(MainInteractorImpl interactor) {
        return interactor;
    }

    @Provides
    @ActivityScope
    RepoListPresenter provideRepoListPresenter(RepoListPresenterImpl presenter) {
        return presenter;
    }

    private final RepoListView view;
}
