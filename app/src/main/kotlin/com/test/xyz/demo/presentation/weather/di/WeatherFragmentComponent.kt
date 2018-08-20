package com.test.xyz.demo.presentation.weather.di

import com.test.xyz.demo.presentation.common.di.scope.ActivityScope
import com.test.xyz.demo.presentation.weather.WeatherFragment

import dagger.Subcomponent

@ActivityScope
@Subcomponent(modules = arrayOf(WeatherFragmentModule::class))
interface WeatherFragmentComponent {
    fun inject(mainFragment: WeatherFragment)
}

