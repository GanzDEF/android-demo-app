package com.test.xyz.demo.ui.weather.mvp;

public interface WeatherPresenter extends OnWeatherInfoCompletedListener {
    void requestWeatherInformation();
}
