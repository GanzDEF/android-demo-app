package com.test.xyz.demo.ui.repodetails.di;

import com.test.xyz.demo.ui.common.di.scope.ActivityScope;
import com.test.xyz.demo.ui.repodetails.RepoDetailsActivity;

import dagger.Subcomponent;

@ActivityScope
@Subcomponent(
        modules = {RepoDetailsActivityModule.class}
)
public interface RepoDetailsActivityComponent {
    void inject(RepoDetailsActivity activity);
}

