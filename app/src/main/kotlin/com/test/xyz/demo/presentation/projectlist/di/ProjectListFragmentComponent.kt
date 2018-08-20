package com.test.xyz.demo.presentation.projectlist.di

import com.test.xyz.demo.presentation.common.di.scope.ActivityScope
import com.test.xyz.demo.presentation.projectlist.ProjectListFragment
import dagger.Subcomponent

@ActivityScope
@Subcomponent(modules = arrayOf(ProjectListFragmentModule::class))
interface ProjectListFragmentComponent {
    fun inject(projectListFragment: ProjectListFragment)
}

