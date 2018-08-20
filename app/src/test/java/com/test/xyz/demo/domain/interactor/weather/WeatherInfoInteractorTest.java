package com.test.xyz.demo.domain.interactor.weather;

import com.test.xyz.demo.domain.model.weather.WeatherRawResponse;
import com.test.xyz.demo.domain.model.weather.WeatherSummaryInfo;
import com.test.xyz.demo.domain.repository.api.GreetRepository;
import com.test.xyz.demo.domain.repository.api.WeatherRepository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import io.reactivex.Observable;
import io.reactivex.plugins.RxJavaPlugins;
import io.reactivex.schedulers.Schedulers;

import static org.mockito.AdditionalMatchers.not;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.when;

@RunWith(PowerMockRunner.class)
@PrepareForTest(WeatherQueryBuilder.class)
public class WeatherInfoInteractorTest {
    private static final String USER_NAME = "hazems";
    private static final String CITY = "New York, USA";
    private static final String INVALID_CITY = "INVALID_CITY";
    private WeatherQueryBuilder weatherQueryBuilder;

    @Mock GreetRepository greetRepository;
    @Mock WeatherRepository weatherRepository;

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
        //FIXME See here before Mockito 2.x, In order to mock final method, you need to mock instance using PowerMockito.mock() API.
        weatherQueryBuilder = PowerMockito.mock(WeatherQueryBuilder.class);

        doAnswer((invocation) -> invocation.getArguments()[0]).when(weatherQueryBuilder).createWeatherQuery(anyString());

        WeatherRawResponse weatherRawResponse = WeatherRawResponse.createWeatherSuccessRawResponse("10");
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
