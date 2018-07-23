package com.test.xyz.demo.presentation.weather.presenter;

import com.test.xyz.demo.R;
import com.test.xyz.demo.domain.interactor.weather.WeatherInteractor;
import com.test.xyz.demo.presentation.BasePresenterTest;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class WeatherPresenterTest extends BasePresenterTest {
    private static final String USER_NAME = "hazems";

    @Mock WeatherInteractor weatherInteractor;
    @Mock WeatherView mainView;

    WeatherPresenter weatherPresenter;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        mockWeatherInteractor(weatherInteractor);
        weatherPresenter = new WeatherPresenterImpl(mainView, weatherInteractor);
    }

    @Test
    public void requestWeatherInformation_shouldReturnInfo() throws Exception {
        //GIVEN
        when(mainView.getUserNameText()).thenReturn(USER_NAME);
        when(mainView.getCityText()).thenReturn(VALID_CITY);

        //WHEN
        weatherPresenter.requestWeatherInformation();

        //THEN
        //TODO comment the next line to show how it work.
        //verify(mainView).showBusyIndicator();
        //verify(mainView).hideBusyIndicator();
        verify(mainView).showResult(MOCK_INFO_SUCCESS_MSG);
    }

    @Test
    public void requestWeatherInformation_whenUserNameIsEmpty_shouldReturnError() throws Exception {
        //GIVEN
        when(mainView.getUserNameText()).thenReturn("");
        when(mainView.getCityText()).thenReturn(VALID_CITY);

        //WHEN
        weatherPresenter.requestWeatherInformation();

        //THEN
        //TODO comment the next method to show how it work.
        //verify(mainView).showBusyIndicator();
        //verify(mainView).hideBusyIndicator();
        verify(mainView).showUserNameError(R.string.username_empty_message);
    }

    @Test
    public void requestWeatherInformation_whenCityIsEmpty_shouldReturnError() throws Exception {
        //GIVEN
        when(mainView.getUserNameText()).thenReturn(USER_NAME);
        when(mainView.getCityText()).thenReturn("");

        //WHEN
        weatherPresenter.requestWeatherInformation();

        //THEN
        //TODO comment the next method to show how it work.
        //verify(mainView).showBusyIndicator();
        //verify(mainView).hideBusyIndicator();
        verify(mainView).showCityNameError(R.string.city_empty_message);
    }

    @Test
    public void requestWeatherInformation_whenCityIsInvalid_shouldReturnError() throws Exception {
        //GIVEN
        when(mainView.getUserNameText()).thenReturn(USER_NAME);
        when(mainView.getCityText()).thenReturn(INVALID_CITY);

        //WHEN
        weatherPresenter.requestWeatherInformation();

        //THEN
        //TODO comment the next method to show how it work.
        //verify(mainView).showBusyIndicator();
        //verify(mainView).hideBusyIndicator();
        verify(mainView).showGenericError(R.string.weather_error);
    }
}
