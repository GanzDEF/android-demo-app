package com.test.xyz.demo.domain.interactor.weather;

import com.test.xyz.demo.domain.model.weather.WeatherSummaryInfo;

import io.reactivex.Observable;

public interface WeatherInteractor {
    Observable<WeatherSummaryInfo> getWeatherInformation(String userName, String cityName);
}
