package com.test.xyz.demo.presentation.weather.presenter;

import com.test.xyz.demo.R;
import com.test.xyz.demo.domain.interactor.weather.WeatherInteractor;
import com.test.xyz.demo.presentation.BasePresenterTest;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static com.test.xyz.demo.domain.interactor.weather.WeatherInteractor.WeatherInfoActionCallback;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class WeatherPresenterTest extends BasePresenterTest {
    private static final String USER_NAME = "hazems";

    @Mock WeatherInteractor weatherInteractor;
    @Mock WeatherView weatherView;

    WeatherPresenter weatherPresenter;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        mockWeatherInteractorBehavior(weatherInteractor);
        weatherPresenter = new WeatherPresenterImpl(weatherView, weatherInteractor);
    }

    @Test
    public void requestWeatherInformation_shouldReturnInfo() throws Exception {
        //GIVEN
        when(weatherView.getUserNameText()).thenReturn(USER_NAME);
        when(weatherView.getCityText()).thenReturn(VALID_CITY);

        //WHEN
        weatherPresenter.requestWeatherInformation();

        //THEN
        verify(weatherInteractor).getWeatherInformation(eq(USER_NAME), eq(VALID_CITY), any(WeatherInfoActionCallback.class));
        verify(weatherView).showResult(INFO_SUCCESS_MSG);
    }

    @Test
    public void requestWeatherInformation_whenUserNameIsEmpty_shouldReturnError() throws Exception {
        //GIVEN
        when(weatherView.getUserNameText()).thenReturn("");
        when(weatherView.getCityText()).thenReturn(VALID_CITY);

        //WHEN
        weatherPresenter.requestWeatherInformation();

        //THEN
        verify(weatherInteractor).getWeatherInformation(eq(""), eq(VALID_CITY), any(WeatherInfoActionCallback.class));
        verify(weatherView).showUserNameError(R.string.username_empty_message);
    }

    @Test
    public void requestWeatherInformation_whenCityIsEmpty_shouldReturnError() throws Exception {
        //GIVEN
        when(weatherView.getUserNameText()).thenReturn(USER_NAME);
        when(weatherView.getCityText()).thenReturn("");

        //WHEN
        weatherPresenter.requestWeatherInformation();

        //THEN
        verify(weatherInteractor).getWeatherInformation(eq(USER_NAME), eq(""), any(WeatherInfoActionCallback.class));
        verify(weatherView).showCityNameError(R.string.city_empty_message);
    }

    @Test
    public void requestWeatherInformation_whenCityIsInvalid_shouldReturnError() throws Exception {
        //GIVEN
        when(weatherView.getUserNameText()).thenReturn(USER_NAME);
        when(weatherView.getCityText()).thenReturn(INVALID_CITY);

        //WHEN
        weatherPresenter.requestWeatherInformation();

        //THEN
        verify(weatherInteractor).getWeatherInformation(eq(USER_NAME), eq(INVALID_CITY), any(WeatherInfoActionCallback.class));
        verify(weatherView).showGenericError(R.string.weather_error);
    }
}
