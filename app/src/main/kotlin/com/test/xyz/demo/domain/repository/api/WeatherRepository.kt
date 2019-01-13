package com.test.xyz.demo.domain.repository.api

import com.test.xyz.demo.domain.model.weather.Result

import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherRepository {

    @GET("data/2.5/weather")
    fun getWeatherInfo(@Query("q") query: String, @Query("APPID") appID: String, @Query("units") units: String): Observable<Result>

    companion object {
        val HTTPS_API_WEATHER_URL = "https://api.openweathermap.org/"
    }
}
