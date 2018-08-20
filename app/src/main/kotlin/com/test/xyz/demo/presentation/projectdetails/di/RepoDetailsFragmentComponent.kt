package com.test.xyz.demo.presentation.projectdetails.di

import com.test.xyz.demo.presentation.common.di.scope.ActivityScope
import com.test.xyz.demo.presentation.projectdetails.ProjectDetailsFragment

import dagger.Subcomponent

@ActivityScope
@Subcomponent(modules = arrayOf(RepoDetailsFragmentModule::class))
interface RepoDetailsFragmentComponent {
    fun inject(projectDetailsFragment: ProjectDetailsFragment)
}

