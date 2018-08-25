package com.test.xyz.demo.domain.interactor.weather

import com.google.common.base.Strings
import com.test.xyz.demo.domain.model.weather.WeatherSummaryInfo
import com.test.xyz.demo.domain.repository.api.GreetRepository
import com.test.xyz.demo.domain.repository.api.WeatherRepository

import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers

class WeatherInteractorImpl(private val greetRepository: GreetRepository,
                            private val weatherRepository: WeatherRepository,
                            private val weatherQueryBuilder: WeatherQueryBuilder) : WeatherInteractor {

    override fun getWeatherInformation(userName: String, cityName: String): Observable<WeatherSummaryInfo> {
        val greeting = greetRepository.greet(userName) + "\n"

        if (Strings.isNullOrEmpty(userName)) {
            return Observable.error(UserNameValidationException("Username must be provided!"))
        }

        return if (Strings.isNullOrEmpty(cityName)) {
            Observable.error(CityValidationException("City must be provided!"))
        } else weatherRepository.getWeatherInfo(weatherQueryBuilder.createWeatherQuery(cityName))
                .map { weatherRawResponse ->
                    val temperature = Integer.parseInt(weatherRawResponse.temperature())
                    WeatherSummaryInfo(cityName, greeting, temperature)
                }
                .subscribeOn(Schedulers.io())

    }

}
