package com.test.xyz.demo.domain.repository.api;

import com.test.xyz.demo.domain.model.weather.WeatherRawResponse;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

public interface WeatherRepository {
    String HTTPS_API_WEATHER_URL = "http://query.yahooapis.com/";

    @GET("v1/public/yql")
    Observable<WeatherRawResponse> getWeatherInfo(@Query("q") String query);
}
