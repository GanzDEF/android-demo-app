package com.test.xyz.demo.ui.common.di.component;

import com.test.xyz.demo.ui.common.di.module.AppModule;
import com.test.xyz.demo.ui.common.di.module.CommonModule;
import com.test.xyz.demo.ui.repodetails.di.RepoDetailsFragmentComponent;
import com.test.xyz.demo.ui.repodetails.di.RepoDetailsFragmentModule;
import com.test.xyz.demo.ui.weather.di.WeatherFragmentComponent;
import com.test.xyz.demo.ui.weather.di.WeatherFragmentModule;
import com.test.xyz.demo.ui.repolist.di.RepoListFragmentComponent;
import com.test.xyz.demo.ui.repolist.di.RepoListFragmentModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {AppModule.class, CommonModule.class})
public interface AppComponent {

    WeatherFragmentComponent plus(WeatherFragmentModule module);

    RepoListFragmentComponent plus(RepoListFragmentModule module);

    RepoDetailsFragmentComponent plus(RepoDetailsFragmentModule module);
}
