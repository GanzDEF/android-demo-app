package com.test.xyz.demo.presentation.weather.presenter

import com.test.xyz.demo.R
import com.test.xyz.demo.domain.interactor.weather.CityValidationException
import com.test.xyz.demo.domain.interactor.weather.UserNameValidationException
import com.test.xyz.demo.domain.interactor.weather.WeatherInteractor
import com.test.xyz.demo.domain.model.weather.WeatherSummaryInfo
import com.test.xyz.demo.presentation.common.DisposableManager
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableObserver
import javax.inject.Inject

class WeatherPresenterImpl @Inject
constructor(private val mainView: WeatherView, private val weatherInteractor: WeatherInteractor,
            private val weatherDataFormatter: WeatherDataFormatter,
            private val weatherDegreeConverterProxy: WeatherDegreeConverterProxy) : WeatherPresenter {

    private val disposableManager: DisposableManager

    init {
        this.disposableManager = DisposableManager()
    }

    override fun requestWeatherInformation() {
        mainView.showBusyIndicator()

        val disposable = weatherInteractor.getWeatherInformation(mainView.getUserNameText(), mainView.getCityText())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableObserver<WeatherSummaryInfo>() {
                    override fun onNext(weatherSummaryInfo: WeatherSummaryInfo) {
                        mainView.hideBusyIndicator()

                        weatherSummaryInfo.setTemperature(
                                weatherDegreeConverterProxy.convertFahrenheitToCelsius(weatherSummaryInfo.temperature().toFloat())
                        )

                        mainView.showResult(weatherDataFormatter.format(weatherSummaryInfo))
                    }

                    override fun onError(e: Throwable) {
                        mainView.hideBusyIndicator()

                        if (e is UserNameValidationException) {
                            mainView.showUserNameError(R.string.username_validation_error_message)
                            return
                        }

                        if (e is CityValidationException) {
                            mainView.showCityNameError(R.string.cityname_validation_error_message)
                            return
                        }

                        mainView.showGenericError(R.string.weather_error)
                    }

                    override fun onComplete() {}
                })

        disposableManager.add(disposable)
    }

    override fun onStop() {
        disposableManager.dispose()
    }
}
