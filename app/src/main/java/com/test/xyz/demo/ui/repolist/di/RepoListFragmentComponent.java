package com.test.xyz.demo.ui.repolist.di;

import com.test.xyz.demo.ui.common.di.scope.ActivityScope;
import com.test.xyz.demo.ui.repolist.RepoListFragment;

import dagger.Subcomponent;

@ActivityScope
@Subcomponent(
        modules = {RepoListFragmentModule.class}
)
public interface RepoListFragmentComponent {
    void inject(RepoListFragment repoListFragment);
}

