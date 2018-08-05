package com.test.xyz.demo.presentation.common.di.component;

import com.test.xyz.demo.domain.interactor.project.ProjectInteractorModule;
import com.test.xyz.demo.domain.interactor.weather.WeatherModule;
import com.test.xyz.demo.presentation.common.di.module.AppModule;
import com.test.xyz.demo.presentation.projectdetails.di.RepoDetailsFragmentComponent;
import com.test.xyz.demo.presentation.projectdetails.di.RepoDetailsFragmentModule;
import com.test.xyz.demo.presentation.projectlist.di.ProjectListFragmentComponent;
import com.test.xyz.demo.presentation.projectlist.di.ProjectListFragmentModule;
import com.test.xyz.demo.presentation.weather.di.WeatherFragmentComponent;
import com.test.xyz.demo.presentation.weather.di.WeatherFragmentModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {AppModule.class, ProjectInteractorModule.class, WeatherModule.class})
public interface AppComponent {
    WeatherFragmentComponent plus(WeatherFragmentModule module);
    ProjectListFragmentComponent plus(ProjectListFragmentModule module);
    RepoDetailsFragmentComponent plus(RepoDetailsFragmentModule module);
}
