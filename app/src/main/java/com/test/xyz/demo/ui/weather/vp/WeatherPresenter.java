package com.test.xyz.demo.ui.weather.vp;

public interface WeatherPresenter extends OnWeatherInfoCompletedListener {
    void requestWeatherInformation();
}
