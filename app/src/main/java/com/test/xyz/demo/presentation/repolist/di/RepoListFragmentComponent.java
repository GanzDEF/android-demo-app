package com.test.xyz.demo.presentation.repolist.di;

import com.test.xyz.demo.presentation.repolist.RepoListFragment;
import com.test.xyz.demo.presentation.common.di.scope.ActivityScope;

import dagger.Subcomponent;

@ActivityScope
@Subcomponent(
        modules = {RepoListFragmentModule.class}
)
public interface RepoListFragmentComponent {
    void inject(RepoListFragment repoListFragment);
}

