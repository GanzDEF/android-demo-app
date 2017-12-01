@file:Suppress("IllegalIdentifier")

package com.test.xyz.demo.ui.weather.vp

import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import com.test.xyz.demo.R
import com.test.xyz.demo.domain.interactor.MainInteractor
import com.test.xyz.demo.ui.BasePresenterTest
import org.junit.Before
import org.junit.Test

class WeatherPresenterTest : BasePresenterTest() {

    lateinit var weatherPresenter: WeatherPresenter

    val mainInteractor: MainInteractor = mock()
    val mainView: WeatherView = mock()

    @Before
    fun setup() {
        mockInteractor(mainInteractor)

        // Instantiate main object
        weatherPresenter = WeatherPresenterImpl(mainView, mainInteractor)
    }

    @Test
    @Throws(Exception::class)
    fun `requestInformation should return weather information`() {
        //GIVEN
        whenever(mainView.userNameText).thenReturn(USER_NAME)
        whenever(mainView.cityText).thenReturn(VALID_CITY)

        //WHEN
        weatherPresenter.requestWeatherInformation()

        //THEN
        //TODO comment the next line to show how it work.
        //verify(mainView).showBusyIndicator();
        //verify(mainView).hideBusyIndicator();
        verify(mainView).showResult(MOCK_INFO_SUCCESS_MSG)
    }

    @Test
    @Throws(Exception::class)
    fun `requestInformation when user name is empty should return error`() {
        //GIVEN
        whenever(mainView.userNameText).thenReturn("")
        whenever(mainView.cityText).thenReturn(VALID_CITY)

        //WHEN
        weatherPresenter.requestWeatherInformation()

        //THEN
        //TODO comment the next method to show how it work.
        //verify(mainView).showBusyIndicator();
        //verify(mainView).hideBusyIndicator();
        verify(mainView).showUserNameError(R.string.username_empty_message)
    }

    @Test
    @Throws(Exception::class)
    fun `requestInformation when city is empty should return error`() {
        //GIVEN
        whenever(mainView.userNameText).thenReturn(USER_NAME)
        whenever(mainView.cityText).thenReturn("")

        //WHEN
        weatherPresenter.requestWeatherInformation()

        //THEN
        //TODO comment the next method to show how it work.
        //verify(mainView).showBusyIndicator();
        //verify(mainView).hideBusyIndicator();
        verify(mainView).showCityNameError(R.string.city_empty_message)
    }

    @Test
    @Throws(Exception::class)
    fun `requestInformation when city is invalid should return error`() {
        //GIVEN
        whenever(mainView.userNameText).thenReturn(USER_NAME)
        whenever(mainView.cityText).thenReturn(INVALID_CITY)

        //WHEN
        weatherPresenter.requestWeatherInformation()

        //THEN
        //TODO comment the next method to show how it work.
        //verify(mainView).showBusyIndicator();
        //verify(mainView).hideBusyIndicator();
        verify(mainView).showGenericError(R.string.weather_error)
    }

    companion object {
        private val USER_NAME = "hazems"
    }
}
