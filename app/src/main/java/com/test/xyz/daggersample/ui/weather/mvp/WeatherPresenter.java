package com.test.xyz.daggersample.ui.weather.mvp;

public interface WeatherPresenter extends OnWeatherInfoCompletedListener {
    void requestWeatherInformation();
}
