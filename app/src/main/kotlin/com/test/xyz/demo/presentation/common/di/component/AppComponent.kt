package com.test.xyz.demo.presentation.common.di.component

import com.test.xyz.demo.domain.interactor.project.ProjectInteractorModule
import com.test.xyz.demo.domain.interactor.weather.WeatherModule
import com.test.xyz.demo.presentation.common.di.module.AppModule
import com.test.xyz.demo.presentation.projectdetails.di.RepoDetailsFragmentComponent
import com.test.xyz.demo.presentation.projectdetails.di.RepoDetailsFragmentModule
import com.test.xyz.demo.presentation.projectlist.di.ProjectListFragmentComponent
import com.test.xyz.demo.presentation.projectlist.di.ProjectListFragmentModule
import com.test.xyz.demo.presentation.weather.di.WeatherFragmentComponent
import com.test.xyz.demo.presentation.weather.di.WeatherFragmentModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(AppModule::class, ProjectInteractorModule::class, WeatherModule::class))
interface AppComponent {
    operator fun plus(module: WeatherFragmentModule): WeatherFragmentComponent
    operator fun plus(module: ProjectListFragmentModule): ProjectListFragmentComponent
    operator fun plus(module: RepoDetailsFragmentModule): RepoDetailsFragmentComponent
}
