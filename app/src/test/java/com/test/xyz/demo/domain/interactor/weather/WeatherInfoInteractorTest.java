package com.test.xyz.demo.domain.interactor.weather;

import com.test.xyz.demo.domain.model.weather.WeatherRawResponse;
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
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.when;

public class WeatherInfoInteractorTest {
    private static final String USER_NAME = "hazems";
    private static final String CITY = "New York, USA";
    private static final String INVALID_CITY = "INVALID_CITY";

    @Mock GreetRepository greetRepository;
    @Mock WeatherRepository weatherRepository;
    @Mock
    WeatherQueryBuilder weatherQueryBuilder;

    WeatherInteractor testSubject;

    @Before
    public void setup() {
        initializeTest();
        mockWeatherInfoAPI();
        testSubject = new WeatherInteractorImpl(greetRepository, weatherRepository, weatherQueryBuilder);
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
        doAnswer((invocation) -> invocation.getArguments()[0]).when(weatherQueryBuilder).createWeatherQuery(anyString());

        WeatherRawResponse weatherRawResponse = WeatherRawResponse.Companion.createWeatherSuccessRawResponse("10");
        Observable<WeatherRawResponse> observable = Observable.just(weatherRawResponse);

        when(weatherRepository.getWeatherInfo(not(eq(INVALID_CITY)))).thenReturn(observable);

        when(weatherRepository.getWeatherInfo(eq(INVALID_CITY))).thenReturn(
                Observable.error(new Exception("Invalid city provided")).cast(WeatherRawResponse.class));
    }

    private void initializeTest() {
        MockitoAnnotations.initMocks(this);
        RxJavaPlugins.setIoSchedulerHandler(scheduler -> Schedulers.trampoline());
    }
}
