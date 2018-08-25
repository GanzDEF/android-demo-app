package com.test.xyz.demo.domain.interactor.weather

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
import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mockito.doAnswer
import org.mockito.MockitoAnnotations

class WeatherInfoInteractorTest {

    private val greetRepository = mock<GreetRepository>()
    private val weatherRepository = mock<WeatherRepository>()
    private val weatherQueryBuilder = mock<WeatherQueryBuilder>()

    private lateinit var testSubject: WeatherInteractor

    @Before
    fun setup() {
        initializeTest()
        testSubject = WeatherInteractorImpl(greetRepository, weatherRepository, weatherQueryBuilder)
    }

    @Test
    fun getWeatherInformation_whenUserNameAndCityAreCorrect_shouldReturnWeatherInfo() {
        //WHEN
        val weatherSummaryInfoObservable = testSubject.getWeatherInformation(USER_NAME, CITY)

        //THEN
        weatherSummaryInfoObservable.test()
                .assertComplete()
                .assertNoErrors()
    }

    @Test
    fun getWeatherInformation_whenCityIsInvalid_shouldReturnFailure() {
        //WHEN
        val weatherSummaryInfoObservable = testSubject.getWeatherInformation(USER_NAME, INVALID_CITY)

        //THEN
        weatherSummaryInfoObservable.test()
                .assertError(Exception::class.java)
    }

    @Test
    fun getWeatherInformation_whenUserNameIsEmpty_shouldReturnUserValidationError() {
        //WHEN
        val weatherSummaryInfoObservable = testSubject.getWeatherInformation("", CITY)

        //THEN
        weatherSummaryInfoObservable.test()
                .assertError(UserNameValidationException::class.java)
    }

    @Test
    fun getWeatherInformation_whenCityIsEmpty_shouldReturnCityValidationError() {
        //WHEN
        val weatherSummaryInfoObservable = testSubject.getWeatherInformation(USER_NAME, "")

        //THEN
        weatherSummaryInfoObservable.test()
                .assertError(CityValidationException::class.java)
    }

    private fun initializeTest() {
        MockitoAnnotations.initMocks(this)
        RxJavaPlugins.setIoSchedulerHandler { scheduler -> Schedulers.trampoline() }
        mockWeatherInfoAPI()
    }

    private fun mockWeatherInfoAPI() {
        doAnswer { invocation -> invocation.arguments[0] }.whenever(weatherQueryBuilder).createWeatherQuery(anyString())

        val weatherRawResponse = WeatherRawResponse.createWeatherSuccessRawResponse("10")
        val observable = Observable.just(weatherRawResponse)

        whenever(weatherRepository.getWeatherInfo(CITY)).thenReturn(observable)

        whenever(weatherRepository.getWeatherInfo(eq(INVALID_CITY))).thenReturn(
                Observable.error<WeatherRawResponse>(Exception("Invalid city provided")))
    }

    companion object {
        private val USER_NAME = "hazems"
        private val CITY = "New York, USA"
        private val INVALID_CITY = "INVALID_CITY"
    }
}
