package com.test.xyz.daggersample.ui.repodetails.di;

import com.test.xyz.daggersample.ui.repodetails.mvp.RepoDetailsPresenter;
import com.test.xyz.daggersample.ui.common.di.scope.ActivityScope;
import com.test.xyz.daggersample.domain.interactor.MainInteractor;
import com.test.xyz.daggersample.domain.interactor.MainInteractorImpl;
import com.test.xyz.daggersample.ui.repodetails.mvp.RepoDetailsPresenterImpl;
import com.test.xyz.daggersample.ui.repodetails.mvp.RepoDetailsView;

import dagger.Module;
import dagger.Provides;

@Module
public class RepoDetailsActivityModule {
    public RepoDetailsActivityModule(RepoDetailsView view) {
        this.view = view;
    }

    @Provides
    @ActivityScope
    RepoDetailsView provideRepoDetailsView() {
        return this.view;
    }

    @Provides
    @ActivityScope
    MainInteractor provideMainInteractor(MainInteractorImpl interactor) {
        return interactor;
    }

    @Provides
    @ActivityScope
    RepoDetailsPresenter provideRepoDetailsPresenter(RepoDetailsPresenterImpl presenter) {
        return presenter;
    }

    private final RepoDetailsView view;
}
