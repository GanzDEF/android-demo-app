package com.test.xyz.daggersample.ui.repolist.di;

import com.test.xyz.daggersample.ui.common.di.scope.ActivityScope;
import com.test.xyz.daggersample.ui.repolist.RepoListFragment;

import dagger.Subcomponent;

@ActivityScope
@Subcomponent(
        modules = {RepoListFragmentModule.class}
)
public interface RepoListFragmentComponent {
    void inject(RepoListFragment repoListFragment);
}

