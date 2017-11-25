package com.test.xyz.demo.ui.repodetails.di;

import com.test.xyz.demo.ui.common.di.scope.ActivityScope;
import com.test.xyz.demo.ui.repodetails.RepoDetailsFragment;

import dagger.Subcomponent;

@ActivityScope
@Subcomponent(
        modules = {RepoDetailsFragmentModule.class}
)
public interface RepoDetailsFragmentComponent {
    void inject(RepoDetailsFragment repoDetailsFragment);
}

