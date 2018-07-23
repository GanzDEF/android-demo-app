package com.test.xyz.demo.presentation.weather.di;

import com.test.xyz.demo.presentation.common.di.scope.ActivityScope;
import com.test.xyz.demo.presentation.weather.presenter.WeatherPresenter;
import com.test.xyz.demo.presentation.weather.presenter.WeatherPresenterImpl;
import com.test.xyz.demo.presentation.weather.presenter.WeatherView;

import dagger.Module;
import dagger.Provides;

@Module
public class WeatherFragmentModule {
    public WeatherFragmentModule(WeatherView view) {
        this.view = view;
    }

    @Provides
    @ActivityScope
    WeatherView provideMainView() {
        return this.view;
    }

    @Provides
    @ActivityScope
    WeatherPresenter provideMainPresenter(WeatherPresenterImpl presenter) {
        return presenter;
    }

    private final WeatherView view;
}
