package com.test.xyz.demo.domain.interactor.weather;

import com.test.xyz.demo.R;
import com.test.xyz.demo.domain.model.weather.WeatherRawResponse;
import com.test.xyz.demo.domain.model.weather.WeatherSummaryInfo;
import com.test.xyz.demo.domain.repository.api.GreetRepository;
import com.test.xyz.demo.domain.repository.api.WeatherRepository;
import com.test.xyz.demo.domain.repository.exception.InvalidCityException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import rx.Observable;
import rx.Scheduler;
import rx.android.plugins.RxAndroidPlugins;
import rx.android.plugins.RxAndroidSchedulersHook;
import rx.plugins.RxJavaPlugins;
import rx.plugins.RxJavaSchedulersHook;
import rx.schedulers.Schedulers;

import static com.test.xyz.demo.domain.interactor.weather.WeatherInteractor.WeatherInfoActionCallback;
import static junit.framework.Assert.fail;
import static org.mockito.AdditionalMatchers.and;
import static org.mockito.AdditionalMatchers.not;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class WeatherInfoInteractorTest {
    static final String USER_NAME = "hazems";
    static final String CITY = "New York, USA";
    static final String INVALID_CITY = "INVALID_CITY";
    static final String EMPTY_VALUE = "";

    @Mock WeatherInfoActionCallback weatherInfoActionCallback;
    @Mock GreetRepository greetRepository;
    @Mock WeatherRepository weatherRepository;
    @Mock WeatherQueryBuilder weatherQueryBuilder;

    WeatherInteractor testSubject;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        setupRxSchedulers();
        mockWeatherQueryBuilder();

        testSubject = new WeatherInteractorImpl(greetRepository, weatherRepository, weatherQueryBuilder);
    }

    @Test
    public void getWeatherInformation_whenUserNameAndCityAreCorrect_shouldReturnWeatherInfo() {
        try {
            //GIVEN
            mockWeatherInfoAPI();

            //WHEN
            testSubject.getWeatherInformation(USER_NAME, CITY, weatherInfoActionCallback);

            //THEN
            verify(weatherInfoActionCallback).onSuccess(any(WeatherSummaryInfo.class));
        } catch (Exception exception) {
            exception.printStackTrace();
            fail("Unable to getWeatherInfo !!!");
        }
    }

    @Test
    public void getWeatherInformation_whenCityIsInvalid_shouldReturnFailure() {
        try {
            //GIVEN
            mockWeatherInfoAPI();

            //WHEN
            testSubject.getWeatherInformation(USER_NAME, INVALID_CITY, weatherInfoActionCallback);

            //THEN
            verify(weatherInfoActionCallback).onFailure(R.string.weather_error);
        } catch (Exception exception) {
            exception.printStackTrace();
            fail("Unable to getWeatherInfo !!!");
        }
    }

    @Test
    public void getWeatherInformation_whenUserNameIsEmpty_shouldReturnValidationError() {
        try {
            //GIVEN
            mockWeatherInfoAPI();

            //WHEN
            testSubject.getWeatherInformation("", CITY, weatherInfoActionCallback);

            //THEN
            verify(weatherInfoActionCallback).onUserNameValidationError(R.string.username_empty_message);
        } catch (Exception exception) {
            fail("Unable to getWeatherInfo !!!");
        }
    }

    @Test
    public void getWeatherInformation_whenCityIsEmpty_shouldReturnValidationError() {
        try {
            //GIVEN
            mockWeatherInfoAPI();

            //WHEN
            testSubject.getWeatherInformation(USER_NAME, "", weatherInfoActionCallback);

            //THEN
            verify(weatherInfoActionCallback).onCityValidationError(R.string.city_empty_message);
        } catch (Exception exception) {
            fail("Unable to getWeatherInfo !!!");
        }
    }

    @After
    public void tearDown() {
        RxAndroidPlugins.getInstance().reset();
        RxJavaPlugins.getInstance().reset();
    }

    private void mockWeatherQueryBuilder() {
        doAnswer((invocation) -> invocation.getArguments()[0]).when(weatherQueryBuilder).createWeatherQuery(anyString());
    }

    private void mockWeatherInfoAPI() {
        // Happy Path Scenario ...
        WeatherRawResponse weatherRawResponse = WeatherRawResponse.createWeatherSuccessRawResponse("10");
        Observable<WeatherRawResponse> observable = Observable.just(weatherRawResponse);

        when(weatherRepository.getWeatherInfo(and(not(eq(EMPTY_VALUE)), not(eq(INVALID_CITY))))).thenReturn(observable);

        // Empty City ...
        when(weatherRepository.getWeatherInfo(eq(EMPTY_VALUE)))
                .thenReturn(Observable.error(
                        new RuntimeException("City is required"))
                        .cast(WeatherRawResponse.class));

        // Invalid City ...
        when(weatherRepository.getWeatherInfo(eq(INVALID_CITY)))
                .thenReturn(Observable.error(
                        new InvalidCityException("Invalid city provided"))
                        .cast(WeatherRawResponse.class));
    }

    private void setupRxSchedulers() {
        RxJavaPlugins.getInstance().reset();
        RxJavaPlugins.getInstance().registerSchedulersHook(new RxJavaSchedulersHook() {
            @Override
            public Scheduler getIOScheduler() {
                return Schedulers.immediate();
            }
        });
        RxAndroidPlugins.getInstance().reset();
        RxAndroidPlugins.getInstance().registerSchedulersHook(new RxAndroidSchedulersHook() {
            @Override
            public Scheduler getMainThreadScheduler() {
                return Schedulers.immediate();
            }
        });
    }
}
