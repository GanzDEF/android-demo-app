package com.test.xyz.demo.domain.interactor.weather

import com.test.xyz.demo.domain.model.weather.WeatherSummaryInfo

import io.reactivex.Observable

interface WeatherInteractor {
    fun getWeatherInformation(userName: String, cityName: String): Observable<WeatherSummaryInfo>
}
