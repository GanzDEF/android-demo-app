package com.test.xyz.daggersample.domain.repository.api;

import rx.Observable;

public interface WeatherRepository {
    Observable<Integer> getWeatherInfo(String city);
}
