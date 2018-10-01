package com.test.xyz.demo.domain.interactor.weather

import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.eq
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import com.test.xyz.demo.domain.model.weather.WeatherRawResponse
import com.test.xyz.demo.domain.repository.api.GreetRepository
import com.test.xyz.demo.domain.repository.api.WeatherRepository
import io.reactivex.Observable
import io.reactivex.plugins.RxJavaPlugins
import io.reactivex.schedulers.Schedulers
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class WeatherInfoInteractorTest {

    val greetRepository = mock<GreetRepository>()

    val weatherRepository = mock<WeatherRepository> {
        val successObservable = Observable.just(WeatherRawResponse.createWeatherSuccessRawResponse("10"))
        val requiredCityErrorObservable = Observable.error<WeatherRawResponse>(Exception("Invalid city provided"))

        on {getWeatherInfo(CITY)}             doReturn successObservable
        on {getWeatherInfo(eq(INVALID_CITY))} doReturn requiredCityErrorObservable
    }

    val weatherQueryBuilder = mock<WeatherQueryBuilder>()

    lateinit var testSubject: WeatherInteractor

    @Before
    fun setup() {
        init()
        testSubject = WeatherInteractorImpl(greetRepository, weatherRepository, weatherQueryBuilder)
    }

    @Test
    fun `getWeatherInformation() should return weather information`() {
        //WHEN
        val weatherSummaryInfoObservable = testSubject.getWeatherInformation(USER_NAME, CITY)

        //THEN
        weatherSummaryInfoObservable.test()
                .assertComplete()
                .assertNoErrors()
    }

    @Test
    fun `getWeatherInformation(), when city is invalid,it should return an error`() {
        //WHEN
        val weatherSummaryInfoObservable = testSubject.getWeatherInformation(USER_NAME, INVALID_CITY)

        //THEN
        weatherSummaryInfoObservable.test()
                .assertError(Exception::class.java)
    }

    @Test
    fun `getWeatherInformation(), when user name is empty, it should return a user validation error`() {
        //WHEN
        val weatherSummaryInfoObservable = testSubject.getWeatherInformation("", CITY)

        //THEN
        weatherSummaryInfoObservable.test()
                .assertError(UserNameValidationException::class.java)
    }

    @Test
    fun `getWeatherInformation(), when city is empty, it should return a city validation error`() {
        //WHEN
        val weatherSummaryInfoObservable = testSubject.getWeatherInformation(USER_NAME, "")

        //THEN
        weatherSummaryInfoObservable.test()
                .assertError(CityValidationException::class.java)
    }

    private fun init() {
        MockitoAnnotations.initMocks(this)
        RxJavaPlugins.setIoSchedulerHandler { scheduler -> Schedulers.trampoline() }
        Mockito.doAnswer { invocation -> invocation.arguments[0] }.whenever(weatherQueryBuilder).createWeatherQuery(ArgumentMatchers.anyString())
    }

    companion object {
        private val USER_NAME = "hazems"
        private val CITY = "New York, USA"
        private val INVALID_CITY = "INVALID_CITY"
    }
}
