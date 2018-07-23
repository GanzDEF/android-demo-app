package com.test.xyz.demo.presentation.projectlist.di;

import com.test.xyz.demo.presentation.projectlist.ProjectListFragment;
import com.test.xyz.demo.presentation.common.di.scope.ActivityScope;

import dagger.Subcomponent;

@ActivityScope
@Subcomponent(
        modules = {ProjectListFragmentModule.class}
)
public interface ProjectListFragmentComponent {
    void inject(ProjectListFragment projectListFragment);
}

