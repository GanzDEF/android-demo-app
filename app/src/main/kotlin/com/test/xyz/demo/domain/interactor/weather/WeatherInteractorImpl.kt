package com.test.xyz.demo.domain.interactor.weather

import android.util.Log

import com.google.common.base.Strings
import com.test.xyz.demo.BuildConfig
import com.test.xyz.demo.domain.model.weather.WeatherSummaryInfo
import com.test.xyz.demo.domain.repository.api.GreetRepository
import com.test.xyz.demo.domain.repository.api.WeatherRepository

import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers

class WeatherInteractorImpl(private val greetRepository: GreetRepository,
                            private val weatherRepository: WeatherRepository) : WeatherInteractor {

    override fun getWeatherInformation(userName: String, cityName: String): Observable<WeatherSummaryInfo> {
        val greeting = greetRepository.greet(userName) + "\n"

        if (Strings.isNullOrEmpty(userName)) {
            return Observable.error(UserNameValidationException("Username must be provided!"))
        }

        return if (Strings.isNullOrEmpty(cityName)) {
            Observable.error(CityValidationException("City must be provided!"))
        } else weatherRepository.getWeatherInfo(cityName, BuildConfig.OPEN_WEATHER_APP_KEY, "imperial")
                .map { result ->
                    val temperature = result!!.main!!.temp!!.toInt()
                    WeatherSummaryInfo(cityName, greeting, temperature)
                }
                .doOnError { error ->
                    error.printStackTrace()
                    Log.e(TAG, "error = " + error.message)
                }
                .subscribeOn(Schedulers.io())

    }

    companion object {
        private val TAG = WeatherInteractorImpl::class.java.name
    }
}
