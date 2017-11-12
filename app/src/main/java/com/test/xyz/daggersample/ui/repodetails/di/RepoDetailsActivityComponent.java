package com.test.xyz.daggersample.ui.repodetails.di;

import com.test.xyz.daggersample.ui.common.di.scope.ActivityScope;
import com.test.xyz.daggersample.ui.repodetails.RepoDetailsActivity;

import dagger.Subcomponent;

@ActivityScope
@Subcomponent(
        modules = {RepoDetailsActivityModule.class}
)
public interface RepoDetailsActivityComponent {
    void inject(RepoDetailsActivity activity);
}

