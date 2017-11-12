package com.test.xyz.daggersample.ui.weather.mvp

import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.whenever
import com.test.xyz.daggersample.R
import com.test.xyz.daggersample.domain.interactor.MainInteractor
import com.test.xyz.daggersample.ui.BasePresenterTest
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers
import org.mockito.Mockito.verify

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
    fun requestInformation_shouldReturnInfo() {
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
    fun requestInformation_whenUserNameIsEmpty_shouldReturnError() {
        //GIVEN
        whenever(mainView.userNameText).thenReturn("")
        whenever(mainView.cityText).thenReturn(VALID_CITY)

        //WHEN
        weatherPresenter!!.requestWeatherInformation()

        //THEN
        //TODO comment the next method to show how it work.
        //verify(mainView).showBusyIndicator();
        //verify(mainView).hideBusyIndicator();
        //verify(mainView, never()).showResult(any(String.class));
        verify(mainView).showUserNameError(R.string.username_empty_message)
    }

    @Test
    @Throws(Exception::class)
    fun requestInformation_whenCityIsEmpty_shouldReturnError() {
        //GIVEN
        whenever(mainView.userNameText).thenReturn(USER_NAME)
        whenever(mainView.cityText).thenReturn("")

        //WHEN
        weatherPresenter.requestWeatherInformation()

        //THEN
        //TODO comment the next method to show how it work.
        //verify(mainView).showBusyIndicator();
        //verify(mainView).hideBusyIndicator();
        //verify(mainView, never()).showResult(any(String.class));
        verify(mainView).showCityNameError(R.string.city_empty_message)
    }

    @Test
    @Throws(Exception::class)
    fun requestInformation_whenCityIsInvalid_shouldReturnError() {
        //GIVEN
        whenever(mainView.userNameText).thenReturn(USER_NAME)
        whenever(mainView.cityText).thenReturn(BasePresenterTest.INVALID_CITY)

        //WHEN
        weatherPresenter.requestWeatherInformation()

        //THEN
        //TODO comment the next method to show how it work.
        //verify(mainView).showBusyIndicator();
        //verify(mainView).hideBusyIndicator();
        verify(mainView).showError(ArgumentMatchers.anyString())
    }

    companion object {
        private val USER_NAME = "hazems"
    }
}
