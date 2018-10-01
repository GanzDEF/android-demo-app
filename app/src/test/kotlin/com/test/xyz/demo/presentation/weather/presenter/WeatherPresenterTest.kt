package com.test.xyz.demo.presentation.weather.presenter

import com.nhaarman.mockitokotlin2.*
import com.test.xyz.demo.R
import com.test.xyz.demo.domain.interactor.weather.CityValidationException
import com.test.xyz.demo.domain.interactor.weather.UserNameValidationException
import com.test.xyz.demo.domain.interactor.weather.WeatherInteractor
import com.test.xyz.demo.domain.model.weather.WeatherSummaryInfo
import io.reactivex.Observable
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.schedulers.Schedulers
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers.anyString
import org.mockito.MockitoAnnotations

class WeatherPresenterTest {
    val weatherSummaryInfoSuccessResult: WeatherSummaryInfo = WeatherSummaryInfo(VALID_CITY,
            INTRO_MESSAGE_SAMPLE, FAHRENHEIT_TEMPERATURE_SAMPLE)

    val weatherInteractor = mock<WeatherInteractor> {
        val successObservable = Observable.just(weatherSummaryInfoSuccessResult)
        val requiredCityErrorObservable = Observable.error<WeatherSummaryInfo>(CityValidationException("City must be provided!"))
        val requiredUserErrorObservable = Observable.error<WeatherSummaryInfo>(UserNameValidationException("User must be provided!"))
        val invalidCityErrorObservable = Observable.error<WeatherSummaryInfo>(Exception("City is invalid!"))

        on {getWeatherInformation(eq(VALID_USER_NAME), eq(VALID_CITY))} doReturn successObservable
        on {getWeatherInformation(anyString(), eq(EMPTY_VALUE))}        doReturn requiredCityErrorObservable
        on {getWeatherInformation(eq(EMPTY_VALUE), eq(VALID_CITY))}     doReturn requiredUserErrorObservable
        on {getWeatherInformation(anyString(), eq(INVALID_CITY))}       doReturn invalidCityErrorObservable
    }

    val weatherDataFormatter = mock<WeatherDataFormatter> {
        val output = "NYC weather is 25°C"
        on {format(weatherSummaryInfoSuccessResult)} doReturn output
    }

    val weatherDegreeConverterProxy = mock<WeatherDegreeConverterProxy>()
    val weatherView = mock<WeatherView>()

    lateinit var weatherPresenter: WeatherPresenter

    @Before
    fun setup() {
        init()
        weatherPresenter = WeatherPresenterImpl(weatherView, weatherInteractor,
                weatherDataFormatter,
                weatherDegreeConverterProxy)
    }

    @Test
    fun `requestWeatherInformation() should return weather information`() {
        //GIVEN
        val fahrenheitTemp = weatherSummaryInfoSuccessResult.temperature()
        whenever(weatherView.getUserNameText()).thenReturn(VALID_USER_NAME)
        whenever(weatherView.getCityText()).thenReturn(VALID_CITY)

        //WHEN
        weatherPresenter.requestWeatherInformation()

        //THEN
        verify(weatherInteractor).getWeatherInformation(eq(VALID_USER_NAME), eq(VALID_CITY))
        verify(weatherDegreeConverterProxy).convertFahrenheitToCelsius(fahrenheitTemp.toFloat())
        verify(weatherDataFormatter).format(weatherSummaryInfoSuccessResult)
        verify(weatherView).showResult(anyString())
    }

    @Test
    fun `requestWeatherInformation(), when UserName is empty, it should return an error`() {
        //GIVEN
        whenever(weatherView.getUserNameText()).thenReturn(EMPTY_VALUE)
        whenever(weatherView.getCityText()).thenReturn(VALID_CITY)

        //WHEN
        weatherPresenter.requestWeatherInformation()

        //THEN
        verify(weatherInteractor).getWeatherInformation(eq(EMPTY_VALUE), eq(VALID_CITY))
        verify(weatherView).showUserNameError(R.string.username_validation_error_message)
    }

    @Test
    fun `requestWeatherInformation(), when city is empty, it should return an error`() {
        //GIVEN
        whenever(weatherView.getUserNameText()).thenReturn(VALID_USER_NAME)
        whenever(weatherView.getCityText()).thenReturn(EMPTY_VALUE)

        //WHEN
        weatherPresenter.requestWeatherInformation()

        //THEN
        verify(weatherInteractor).getWeatherInformation(eq(VALID_USER_NAME), eq(EMPTY_VALUE))
        verify(weatherView).showCityNameError(R.string.cityname_validation_error_message)
    }

    @Test
    fun `requestWeatherInformation(), when city is invalid, it should return an error`() {
        //GIVEN
        whenever(weatherView.getUserNameText()).thenReturn(VALID_USER_NAME)
        whenever(weatherView.getCityText()).thenReturn(INVALID_CITY)

        //WHEN
        weatherPresenter.requestWeatherInformation()

        //THEN
        verify(weatherInteractor).getWeatherInformation(eq(VALID_USER_NAME), eq(INVALID_CITY))
        verify(weatherView).showGenericError(R.string.weather_error)
    }

    private fun init() {
        MockitoAnnotations.initMocks(this)
        RxAndroidPlugins.setInitMainThreadSchedulerHandler { scheduler -> Schedulers.trampoline() }
    }

    companion object {
        private val EMPTY_VALUE = ""
        private val INVALID_CITY = "INVALID"
        private val VALID_CITY = "New York, USA"
        private val VALID_USER_NAME = "hazems"
        private val INTRO_MESSAGE_SAMPLE = "Hello Test"
        private val FAHRENHEIT_TEMPERATURE_SAMPLE = 77
    }
}
