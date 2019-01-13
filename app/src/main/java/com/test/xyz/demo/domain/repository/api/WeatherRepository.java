package com.test.xyz.demo.domain.repository.api;

import com.test.xyz.demo.domain.model.weather.Result;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface WeatherRepository {
    String HTTPS_API_WEATHER_URL = "https://api.openweathermap.org/";

    @GET("data/2.5/weather")
    Observable<Result> getWeatherInfo(@Query("q") String query, @Query("APPID") String appID, @Query("units") String units);
}
