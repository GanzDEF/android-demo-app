package com.test.xyz.demo.ui.repodetails.di;

import com.test.xyz.demo.ui.repodetails.vp.RepoDetailsPresenter;
import com.test.xyz.demo.ui.common.di.scope.ActivityScope;
import com.test.xyz.demo.domain.interactor.MainInteractor;
import com.test.xyz.demo.domain.interactor.MainInteractorImpl;
import com.test.xyz.demo.ui.repodetails.vp.RepoDetailsPresenterImpl;
import com.test.xyz.demo.ui.repodetails.vp.RepoDetailsView;

import dagger.Module;
import dagger.Provides;

@Module
public class RepoDetailsFragmentModule {
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
