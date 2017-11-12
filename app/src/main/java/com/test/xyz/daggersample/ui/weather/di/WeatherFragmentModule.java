package com.test.xyz.daggersample.ui.weather.di;

import com.test.xyz.daggersample.ui.common.di.scope.ActivityScope;
import com.test.xyz.daggersample.domain.interactor.MainInteractor;
import com.test.xyz.daggersample.domain.interactor.MainInteractorImpl;
import com.test.xyz.daggersample.ui.weather.mvp.WeatherPresenter;
import com.test.xyz.daggersample.ui.weather.mvp.WeatherPresenterImpl;
import com.test.xyz.daggersample.ui.weather.mvp.WeatherView;

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
    MainInteractor provideMainInteractor(MainInteractorImpl interactor) {
        return interactor;
    }

    @Provides
    @ActivityScope
    WeatherPresenter provideMainPresenter(WeatherPresenterImpl presenter) {
        return presenter;
    }

    private final WeatherView view;
}
