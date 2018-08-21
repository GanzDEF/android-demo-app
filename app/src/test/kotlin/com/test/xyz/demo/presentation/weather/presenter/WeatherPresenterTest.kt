package com.test.xyz.demo.presentation.weather.presenter

import com.nhaarman.mockitokotlin2.eq
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
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
    val weatherInteractor = mock<WeatherInteractor>()
    val weatherView = mock<WeatherView>()
    val weatherDataFormatter = mock<WeatherDataFormatter>()
    val weatherDegreeConverterProxy = mock<WeatherDegreeConverterProxy>()

    lateinit var weatherSummaryInfoSuccessResult: WeatherSummaryInfo

    lateinit var weatherPresenter: WeatherPresenter

    @Before
    fun setup() {
        initializeTest()
        mockWeatherInteractorBehavior(weatherInteractor)
        weatherPresenter = WeatherPresenterImpl(weatherView, weatherInteractor,
                weatherDataFormatter,
                weatherDegreeConverterProxy)
    }

    @Test
    fun requestWeatherInformation_shouldReturnInfo() {
        //GIVEN
        val output = "NYC weather is 25°C"
        val fahrenheitTemp = weatherSummaryInfoSuccessResult.temperature()

        whenever(weatherView.getUserNameText()).thenReturn(VALID_USER_NAME)
        whenever(weatherView.getCityText()).thenReturn(VALID_CITY)
        whenever(weatherDataFormatter.format(weatherSummaryInfoSuccessResult)).thenReturn(output)

        //WHEN
        weatherPresenter.requestWeatherInformation()

        //THEN
        verify(weatherInteractor).getWeatherInformation(eq(VALID_USER_NAME), eq(VALID_CITY))
        verify(weatherDegreeConverterProxy).convertFahrenheitToCelsius(fahrenheitTemp.toFloat())
        verify(weatherDataFormatter).format(weatherSummaryInfoSuccessResult!!)
        verify(weatherView).showResult(output)
    }

    @Test
    fun requestWeatherInformation_whenUserNameIsEmpty_shouldReturnError() {
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
    fun requestWeatherInformation_whenCityIsEmpty_shouldReturnError() {
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
    fun requestWeatherInformation_whenCityIsInvalid_shouldReturnError() {
        //GIVEN
        whenever(weatherView.getUserNameText()).thenReturn(VALID_USER_NAME)
        whenever(weatherView.getCityText()).thenReturn(INVALID_CITY)

        //WHEN
        weatherPresenter.requestWeatherInformation()

        //THEN
        verify(weatherInteractor).getWeatherInformation(eq(VALID_USER_NAME), eq(INVALID_CITY))
        verify(weatherView).showGenericError(R.string.weather_error)
    }

    //region Helper mocks
    private fun mockWeatherInteractorBehavior(weatherInteractor: WeatherInteractor) {
        val observable = Observable.just(weatherSummaryInfoSuccessResult)

        whenever(weatherInteractor.getWeatherInformation(eq(VALID_USER_NAME), eq(VALID_CITY))).thenReturn(observable)

        whenever(weatherInteractor.getWeatherInformation(anyString(), eq(EMPTY_VALUE))).thenReturn(
                Observable.error<WeatherSummaryInfo>(CityValidationException("City must be provided!")))

        whenever(weatherInteractor.getWeatherInformation(eq(EMPTY_VALUE), eq(VALID_CITY))).thenReturn(
                Observable.error<WeatherSummaryInfo>(UserNameValidationException("User must be provided!")))

        whenever(weatherInteractor.getWeatherInformation(anyString(), eq(INVALID_CITY))).thenReturn(
                Observable.error<WeatherSummaryInfo>(Exception("City is invalid!")))
    }

    private fun initializeTest() {
        MockitoAnnotations.initMocks(this)
        RxAndroidPlugins.setInitMainThreadSchedulerHandler { scheduler -> Schedulers.trampoline() }

        weatherSummaryInfoSuccessResult = WeatherSummaryInfo(VALID_CITY, INTRO_MESSAGE_SAMPLE, FAHRENHEIT_TEMPERATURE_SAMPLE)
    }

    companion object {
        private val EMPTY_VALUE = ""
        private val INVALID_CITY = "INVALID"
        private val VALID_CITY = "New York, USA"
        private val VALID_USER_NAME = "hazems"
        private val INTRO_MESSAGE_SAMPLE = "Hello Test"
        private val FAHRENHEIT_TEMPERATURE_SAMPLE = 77
    }
    //endregion
}
