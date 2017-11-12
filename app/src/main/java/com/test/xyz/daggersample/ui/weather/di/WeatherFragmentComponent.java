package com.test.xyz.daggersample.ui.weather.di;

import com.test.xyz.daggersample.ui.common.di.scope.ActivityScope;
import com.test.xyz.daggersample.ui.weather.WeatherFragment;

import dagger.Subcomponent;

@ActivityScope
@Subcomponent(
        modules = {WeatherFragmentModule.class}
)
public interface WeatherFragmentComponent {
    void inject(WeatherFragment mainFragment);
}

