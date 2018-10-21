package com.test.xyz.demo.presentation.weather.presenter;

import com.test.xyz.demo.R;
import com.test.xyz.demo.domain.interactor.weather.CityValidationException;
import com.test.xyz.demo.domain.interactor.weather.UserNameValidationException;
import com.test.xyz.demo.domain.interactor.weather.WeatherInteractor;
import com.test.xyz.demo.domain.model.weather.WeatherSummaryInfo;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.plugins.RxAndroidPlugins;
import io.reactivex.schedulers.Schedulers;

import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(PowerMockRunner.class)
@PrepareForTest({WeatherDataFormatter.class, WeatherDegreeConverter.class})
public class WeatherPresenterTest {
    // Sequence of calls:
    // WeatherPresenter -> WeatherInteractor returns WeatherSummaryInfo
    // -> WeatherDegreeConverter -> WeatherDataFormatter -> WeatherView

    WeatherSummaryInfo weatherSummaryInfoSuccessResult;
    WeatherDataFormatter weatherDataFormatter;

    @Mock WeatherInteractor weatherInteractor;
    @Mock WeatherView weatherView;

    WeatherPresenter weatherPresenter;

    @Before
    public void setup() {
        initializeTest();
        weatherDataFormatter = PowerMockito.mock(WeatherDataFormatter.class);
        mockWeatherInteractorBehavior(weatherInteractor);
        weatherPresenter = new WeatherPresenterImpl(weatherView, weatherInteractor,
                                                    weatherDataFormatter);
    }

    @Test
    public void requestWeatherInformation_shouldReturnInfo() {
        //GIVEN
        String output = "NYC weather is 25°C";
        int fahrenheitTemp = weatherSummaryInfoSuccessResult.temperature();

        PowerMockito.mockStatic(WeatherDegreeConverter.class);
        when(weatherView.getUserNameText()).thenReturn(VALID_USER_NAME);
        when(weatherView.getCityText()).thenReturn(VALID_CITY);
        when(weatherDataFormatter.format(weatherSummaryInfoSuccessResult)).thenReturn(output);

        //WHEN
        weatherPresenter.requestWeatherInformation();

        //THEN
        verify(weatherInteractor).getWeatherInformation(eq(VALID_USER_NAME), eq(VALID_CITY));

        verify(weatherDataFormatter).format(weatherSummaryInfoSuccessResult);
        verify(weatherView).showResult(output);

        PowerMockito.verifyStatic(times(1));
        WeatherDegreeConverter.convertFahrenheitToCelsius(fahrenheitTemp);
    }

    @Test
    public void requestWeatherInformation_whenUserNameIsEmpty_shouldReturnError() {
        //GIVEN
        when(weatherView.getUserNameText()).thenReturn(EMPTY_VALUE);
        when(weatherView.getCityText()).thenReturn(VALID_CITY);

        //WHEN
        weatherPresenter.requestWeatherInformation();

        //THEN
        verify(weatherInteractor).getWeatherInformation(eq(EMPTY_VALUE), eq(VALID_CITY));
        verify(weatherView).showUserNameError(R.string.username_validation_error_message);
    }

    @Test
    public void requestWeatherInformation_whenCityIsEmpty_shouldReturnError() {
        //GIVEN
        when(weatherView.getUserNameText()).thenReturn(VALID_USER_NAME);
        when(weatherView.getCityText()).thenReturn(EMPTY_VALUE);

        //WHEN
        weatherPresenter.requestWeatherInformation();

        //THEN
        verify(weatherInteractor).getWeatherInformation(eq(VALID_USER_NAME), eq(EMPTY_VALUE));
        verify(weatherView).showCityNameError(R.string.cityname_validation_error_message);
    }

    @Test
    public void requestWeatherInformation_whenCityIsInvalid_shouldReturnError() {
        //GIVEN
        when(weatherView.getUserNameText()).thenReturn(VALID_USER_NAME);
        when(weatherView.getCityText()).thenReturn(INVALID_CITY);

        //WHEN
        weatherPresenter.requestWeatherInformation();

        //THEN
        verify(weatherInteractor).getWeatherInformation(eq(VALID_USER_NAME), eq(INVALID_CITY));
        verify(weatherView).showGenericError(R.string.weather_error);
    }

    //region Helper mocks
    private void mockWeatherInteractorBehavior(WeatherInteractor weatherInteractor) {
        Observable<WeatherSummaryInfo> observable = Observable.just(weatherSummaryInfoSuccessResult);

        when(weatherInteractor.getWeatherInformation(eq(VALID_USER_NAME), eq(VALID_CITY))).thenReturn(observable);

        when(weatherInteractor.getWeatherInformation(anyString(), eq(EMPTY_VALUE))).thenReturn(
                (Observable<WeatherSummaryInfo>) Observable.error(new CityValidationException("City must be provided!")).cast((Class) List.class));
        when(weatherInteractor.getWeatherInformation(eq(EMPTY_VALUE), eq(VALID_CITY))).thenReturn(
                (Observable<WeatherSummaryInfo>) Observable.error(new UserNameValidationException("User must be provided!")).cast((Class) List.class));
        when(weatherInteractor.getWeatherInformation(anyString(), eq(INVALID_CITY))).thenReturn(
                (Observable<WeatherSummaryInfo>) Observable.error(new Exception("City is invalid!")).cast((Class) List.class));
    }

    private void initializeTest() {
        MockitoAnnotations.initMocks(this);
        RxAndroidPlugins.setInitMainThreadSchedulerHandler(scheduler ->  Schedulers.trampoline());

        weatherSummaryInfoSuccessResult = new WeatherSummaryInfo(VALID_CITY, INTRO_MESSAGE_SAMPLE, FAHRENHEIT_TEMPERATURE_SAMPLE);
    }

    static final String EMPTY_VALUE = "";
    static final String INVALID_CITY = "INVALID";
    static final String VALID_CITY = "New York, USA";
    static final String VALID_USER_NAME = "hazems";
    static final String INTRO_MESSAGE_SAMPLE = "Hello Test";
    static final int FAHRENHEIT_TEMPERATURE_SAMPLE = 77;
    //endregion
}
