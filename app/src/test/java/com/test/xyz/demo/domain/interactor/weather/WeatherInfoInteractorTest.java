package com.test.xyz.demo.domain.interactor.weather;

import com.test.xyz.demo.domain.model.weather.Result;
import com.test.xyz.demo.domain.model.weather.WeatherSummaryInfo;
import com.test.xyz.demo.domain.repository.api.GreetRepository;
import com.test.xyz.demo.domain.repository.api.WeatherRepository;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import io.reactivex.Observable;
import io.reactivex.plugins.RxJavaPlugins;
import io.reactivex.schedulers.Schedulers;

import static org.mockito.AdditionalMatchers.not;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

public class WeatherInfoInteractorTest {
    private static final String USER_NAME = "hazems";
    private static final String CITY = "New York, USA";
    private static final String INVALID_CITY = "INVALID_CITY";

    @Mock GreetRepository greetRepository;
    @Mock WeatherRepository weatherRepository;

    WeatherInteractor testSubject;

    @Before
    public void setup() {
        initializeTest();
        mockWeatherInfoAPI();
        testSubject = new WeatherInteractorImpl(greetRepository, weatherRepository);
    }

    @Test
    public void getWeatherInformation_whenUserNameAndCityAreCorrect_shouldReturnWeatherInfo() {
        //WHEN
        Observable<WeatherSummaryInfo> weatherSummaryInfoObservable = testSubject.getWeatherInformation(USER_NAME, CITY);

        //THEN
        weatherSummaryInfoObservable.test()
                .assertComplete()
                .assertNoErrors();
    }

    @Test
    public void getWeatherInformation_whenCityIsInvalid_shouldReturnFailure() {
        //WHEN
        Observable<WeatherSummaryInfo> weatherSummaryInfoObservable = testSubject.getWeatherInformation(USER_NAME, INVALID_CITY);

        //THEN
        weatherSummaryInfoObservable.test()
                .assertError(Exception.class);
    }

    @Test
    public void getWeatherInformation_whenUserNameIsEmpty_shouldReturnUserValidationError() {
        //WHEN
        Observable<WeatherSummaryInfo> weatherSummaryInfoObservable = testSubject.getWeatherInformation("", CITY);

        //THEN
        weatherSummaryInfoObservable.test()
                .assertError(UserNameValidationException.class);
    }

    @Test
    public void getWeatherInformation_whenCityIsEmpty_shouldReturnCityValidationError() {
        //WHEN
        Observable<WeatherSummaryInfo> weatherSummaryInfoObservable = testSubject.getWeatherInformation(USER_NAME, "");

        //THEN
        weatherSummaryInfoObservable.test()
                .assertError(CityValidationException.class);
    }

    private void mockWeatherInfoAPI() {
        Result weatherResult = Result.createWeatherResultSuccessResponse(10.0d);
        Observable<Result> observable = Observable.just(weatherResult);

        when(weatherRepository.getWeatherInfo(not(eq(INVALID_CITY)), anyString(), anyString())).thenReturn(observable);

        when(weatherRepository.getWeatherInfo(eq(INVALID_CITY), anyString(), anyString())).thenReturn(
                Observable.error(new Exception("Invalid city provided")).cast(Result.class));
    }

    private void initializeTest() {
        MockitoAnnotations.initMocks(this);
        RxJavaPlugins.setIoSchedulerHandler(scheduler -> Schedulers.trampoline());
    }
}
