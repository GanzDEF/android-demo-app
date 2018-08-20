package com.test.xyz.demo.presentation.weather.di

import com.test.xyz.demo.presentation.common.di.scope.ActivityScope
import com.test.xyz.demo.presentation.weather.presenter.*
import dagger.Module
import dagger.Provides

@Module
class WeatherFragmentModule(private val view: WeatherView) {

    @Provides
    @ActivityScope
    internal fun provideMainView(): WeatherView {
        return this.view
    }

    @Provides
    @ActivityScope
    internal fun provideWeatherDataFormatter(): WeatherDataFormatter {
        return WeatherDataFormatter()
    }

    @Provides
    @ActivityScope
    internal fun provideWeatherDegreeConverterProxy(): WeatherDegreeConverterProxy {
        return WeatherDegreeConverterProxy()
    }

    @Provides
    @ActivityScope
    internal fun provideMainPresenter(presenter: WeatherPresenterImpl): WeatherPresenter {
        return presenter
    }
}
