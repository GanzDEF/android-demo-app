package com.test.xyz.demo.domain.repository.api

import com.test.xyz.demo.domain.model.weather.WeatherRawResponse

import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherRepository {

    @GET("v1/public/yql")
    fun getWeatherInfo(@Query("q") query: String): Observable<WeatherRawResponse>

    companion object {
        val HTTPS_API_WEATHER_URL = "http://query.yahooapis.com/"
    }
}
