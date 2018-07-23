package com.test.xyz.demo.presentation.common.di.component;

import com.test.xyz.demo.presentation.projectdetails.di.RepoDetailsFragmentComponent;
import com.test.xyz.demo.presentation.projectdetails.di.RepoDetailsFragmentModule;
import com.test.xyz.demo.presentation.projectlist.di.ProjectListFragmentComponent;
import com.test.xyz.demo.presentation.projectlist.di.ProjectListFragmentModule;
import com.test.xyz.demo.presentation.weather.di.WeatherFragmentComponent;
import com.test.xyz.demo.presentation.weather.di.WeatherFragmentModule;
import com.test.xyz.demo.presentation.common.di.module.AppModule;
import com.test.xyz.demo.presentation.common.di.module.CommonModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {AppModule.class, CommonModule.class})
public interface AppComponent {
    WeatherFragmentComponent plus(WeatherFragmentModule module);
    ProjectListFragmentComponent plus(ProjectListFragmentModule module);
    RepoDetailsFragmentComponent plus(RepoDetailsFragmentModule module);
}
