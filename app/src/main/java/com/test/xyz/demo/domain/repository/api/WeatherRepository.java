package com.test.xyz.demo.domain.repository.api;

import rx.Observable;

public interface WeatherRepository {
    Observable<Integer> getWeatherInfo(String city);
}
