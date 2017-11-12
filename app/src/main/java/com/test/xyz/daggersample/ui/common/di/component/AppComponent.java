package com.test.xyz.daggersample.ui.common.di.component;

import com.test.xyz.daggersample.ui.common.di.module.AppModule;
import com.test.xyz.daggersample.ui.common.di.module.CommonModule;
import com.test.xyz.daggersample.ui.repodetails.di.RepoDetailsActivityComponent;
import com.test.xyz.daggersample.ui.repodetails.di.RepoDetailsActivityModule;
import com.test.xyz.daggersample.ui.weather.di.WeatherFragmentComponent;
import com.test.xyz.daggersample.ui.weather.di.WeatherFragmentModule;
import com.test.xyz.daggersample.ui.repolist.di.RepoListFragmentComponent;
import com.test.xyz.daggersample.ui.repolist.di.RepoListFragmentModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {AppModule.class, CommonModule.class})
public interface AppComponent {

    WeatherFragmentComponent plus(WeatherFragmentModule module);

    RepoListFragmentComponent plus(RepoListFragmentModule module);

    RepoDetailsActivityComponent plus(RepoDetailsActivityModule module);
}
