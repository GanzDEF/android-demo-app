package com.test.xyz.demo.presentation.common.di.module;

import com.test.xyz.demo.domain.interactor.project.ProjectInteractorModule;
import com.test.xyz.demo.domain.interactor.weather.WeatherModule;

import dagger.Module;

@Module(includes = {ProjectInteractorModule.class, WeatherModule.class})
public class CommonModule {
}
