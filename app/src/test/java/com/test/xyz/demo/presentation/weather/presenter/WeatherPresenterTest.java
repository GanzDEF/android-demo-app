package com.test.xyz.demo.presentation.weather.presenter;

import com.test.xyz.demo.R;
import com.test.xyz.demo.domain.interactor.weather.WeatherInteractor;
import com.test.xyz.demo.domain.model.weather.WeatherSummaryInfo;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static com.test.xyz.demo.domain.interactor.weather.WeatherInteractor.WeatherInfoActionCallback;
import static org.mockito.AdditionalMatchers.and;
import static org.mockito.AdditionalMatchers.not;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class WeatherPresenterTest {
    @Mock WeatherInteractor weatherInteractor;
    @Mock WeatherView weatherView;
    @Mock WeatherDataFormatter weatherDataFormatter;

    WeatherPresenter weatherPresenter;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        mockWeatherInteractorBehavior(weatherInteractor);
        weatherPresenter = new WeatherPresenterImpl(weatherView, weatherInteractor, weatherDataFormatter);
    }

    @Test
    public void requestWeatherInformation_shouldReturnInfo() throws Exception {
        //GIVEN
        String weatherDataFormatterOutput = "NYC weather is 75 F";
        when(weatherView.getUserNameText()).thenReturn(USER_NAME);
        when(weatherView.getCityText()).thenReturn(VALID_CITY);
        when(weatherDataFormatter.format(weatherSummaryInfo)).thenReturn(weatherDataFormatterOutput);

        //WHEN
        weatherPresenter.requestWeatherInformation();

        //THEN
        verify(weatherInteractor).getWeatherInformation(eq(USER_NAME), eq(VALID_CITY), any(WeatherInfoActionCallback.class));
        verify(weatherView).showResult(weatherDataFormatterOutput);
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

    private void mockWeatherInteractorBehavior(WeatherInteractor weatherInteractor) {
        mockErrorFlow(weatherInteractor);
        mockSuccessFlow(weatherInteractor);
    }

    private void mockSuccessFlow(WeatherInteractor weatherInteractor) {
        doAnswer((invocation) -> {
            ((WeatherInfoActionCallback) invocation.getArguments()[2]).onSuccess(weatherSummaryInfo);

            return null;
        }).when(weatherInteractor).getWeatherInformation(not(eq(EMPTY_VALUE)),
                and(not(eq(INVALID_CITY)), not(eq(EMPTY_VALUE))),
                any(WeatherInfoActionCallback.class));
    }

    private void mockErrorFlow(WeatherInteractor weatherInteractor) {
        mockInvalidCityErrorFlow(weatherInteractor);
        mockEmptyUserErrorFlow(weatherInteractor);
        mockEmptyCityErrorFlow(weatherInteractor);
    }

    private void mockEmptyCityErrorFlow(WeatherInteractor weatherInteractor) {
        doAnswer((invocation) -> {
            ((WeatherInfoActionCallback) invocation.getArguments()[2]).onCityValidationError(R.string.city_empty_message);
            return null;
        }).when(weatherInteractor).getWeatherInformation(anyString(), eq(EMPTY_VALUE), any(WeatherInfoActionCallback.class));
    }

    private void mockEmptyUserErrorFlow(WeatherInteractor weatherInteractor) {
        doAnswer((invocation) -> {
            ((WeatherInfoActionCallback) invocation.getArguments()[2]).onUserNameValidationError(R.string.username_empty_message);
            return null;
        }).when(weatherInteractor).getWeatherInformation(eq(EMPTY_VALUE), eq(VALID_CITY), any(WeatherInfoActionCallback.class));
    }

    private void mockInvalidCityErrorFlow(WeatherInteractor weatherInteractor) {
        doAnswer((invocation) -> {
            ((WeatherInfoActionCallback) invocation.getArguments()[2]).onFailure(R.string.weather_error);
            return null;
        }).when(weatherInteractor).getWeatherInformation(anyString(), eq(INVALID_CITY), any(WeatherInfoActionCallback.class));
    }

    static final String EMPTY_VALUE = "";
    static final String INVALID_CITY = "INVALID";
    static final String VALID_CITY = "New York, USA";
    static final String USER_NAME = "hazems";
    static final String INTRO_MESSAGE_SAMPLE = "Hello Test";
    static final int TEMPERATURE_SAMPLE = 100;
    static final WeatherSummaryInfo weatherSummaryInfo = new WeatherSummaryInfo(VALID_CITY, INTRO_MESSAGE_SAMPLE, TEMPERATURE_SAMPLE);
}
