package com.test.xyz.demo.ui.weather.di;

import com.test.xyz.demo.ui.common.di.scope.ActivityScope;
import com.test.xyz.demo.ui.weather.WeatherFragment;

import dagger.Subcomponent;

@ActivityScope
@Subcomponent(
        modules = {WeatherFragmentModule.class}
)
public interface WeatherFragmentComponent {
    void inject(WeatherFragment mainFragment);
}

