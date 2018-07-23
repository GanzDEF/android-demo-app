package com.test.xyz.demo.presentation.repodetails.di;

import com.test.xyz.demo.presentation.common.di.scope.ActivityScope;
import com.test.xyz.demo.presentation.repodetails.RepoDetailsFragment;

import dagger.Subcomponent;

@ActivityScope
@Subcomponent(
        modules = {RepoDetailsFragmentModule.class}
)
public interface RepoDetailsFragmentComponent {
    void inject(RepoDetailsFragment repoDetailsFragment);
}

