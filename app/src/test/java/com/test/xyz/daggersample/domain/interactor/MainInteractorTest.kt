@file:Suppress("IllegalIdentifier")

package com.test.xyz.daggersample.domain.interactor

import com.nhaarman.mockito_kotlin.eq
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.whenever
import com.test.xyz.daggersample.R
import com.test.xyz.daggersample.domain.repository.api.ErrorMessages
import com.test.xyz.daggersample.domain.repository.api.HelloRepository
import com.test.xyz.daggersample.domain.repository.api.RepoListRepository
import com.test.xyz.daggersample.domain.repository.api.WeatherRepository
import com.test.xyz.daggersample.domain.repository.api.model.Repo
import com.test.xyz.daggersample.domain.repository.exception.InvalidCityException
import com.test.xyz.daggersample.ui.repodetails.mvp.OnRepoDetailsCompletedListener
import com.test.xyz.daggersample.ui.repolist.mvp.OnRepoListCompletedListener
import com.test.xyz.daggersample.ui.weather.mvp.OnWeatherInfoCompletedListener
import junit.framework.Assert.fail
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.AdditionalMatchers.and
import org.mockito.AdditionalMatchers.not
import org.mockito.ArgumentMatchers
import org.mockito.Matchers.any
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import rx.Observable
import rx.Scheduler
import rx.android.plugins.RxAndroidPlugins
import rx.android.plugins.RxAndroidSchedulersHook
import rx.plugins.RxJavaPlugins
import rx.plugins.RxJavaSchedulersHook
import rx.schedulers.Schedulers
import java.util.*

class MainInteractorTest {

    lateinit var testSubject: MainInteractorImpl

    val onInfoCompletedListener: OnWeatherInfoCompletedListener = mock()
    val onRepoListCompletedListener: OnRepoListCompletedListener = mock()
    val onRepoDetailsCompletedListener: OnRepoDetailsCompletedListener = mock()
    val helloRepository: HelloRepository = mock()
    val weatherRepository: WeatherRepository = mock()
    val repoListRepository: RepoListRepository = mock()

    @Before
    fun setup() {
        setupRxSchedulers()
        testSubject = MainInteractorImpl(helloRepository, weatherRepository, repoListRepository)
    }

    @After
    fun tearDown() {
        RxAndroidPlugins.getInstance().reset()
        RxJavaPlugins.getInstance().reset()
    }

    @Test
    fun `getInformation shouldReturnWeatherInfo`() {
        try {
            //GIVEN
            mockWeatherServiceAPIs()

            //WHEN
            testSubject.getWeatherInformation(USER_NAME, CITY, onInfoCompletedListener)

            //THEN
            verify(onInfoCompletedListener).onSuccess(ArgumentMatchers.any(String::class.java))
        } catch (exception: Exception) {
            exception.printStackTrace()
            fail("Unable to getWeatherInfo !!!")
        }

    }

    @Test
    fun `getInformation whenCityIsInvalid shouldReturnFailure`() {
        try {
            //GIVEN
            mockWeatherServiceAPIs()

            //WHEN
            testSubject.getWeatherInformation(USER_NAME, INVALID_CITY, onInfoCompletedListener)

            //THEN
            verify(onInfoCompletedListener).onFailure(ArgumentMatchers.any(String::class.java))
        } catch (exception: Exception) {
            fail("Unable to getWeatherInfo !!!")
        }

    }

    @Test
    fun `getInformation whenUserNameIsEmpty shouldReturnValidationError`() {
        try {
            //GIVEN
            mockWeatherServiceAPIs()

            //WHEN
            testSubject.getWeatherInformation("", CITY, onInfoCompletedListener)

            //THEN
            verify(onInfoCompletedListener).onUserNameValidationError(R.string.username_empty_message)
        } catch (exception: Exception) {
            fail("Unable to getWeatherInfo !!!")
        }

    }

    @Test
    fun `getInformation whenCityIsEmpty shouldReturnValidationError`() {
        try {
            //GIVEN
            mockWeatherServiceAPIs()

            //WHEN
            testSubject.getWeatherInformation(USER_NAME, "", onInfoCompletedListener)

            //THEN
            verify(onInfoCompletedListener).onCityValidationError(R.string.city_empty_message)
        } catch (exception: Exception) {
            fail("Unable to getWeatherInfo !!!")
        }

    }

    @Test
    @Throws(Exception::class)
    fun `getRepoList whenUserNameIsCorrect shouldReturnRepoListInfo`() {
        //GIVEN
        mockGetRepoListAPI()

        //WHEN
        testSubject.getRepoList(USER_NAME, onRepoListCompletedListener)

        //THEN
        verify(onRepoListCompletedListener).onRepoListRetrievalSuccess(ArgumentMatchers.anyList())
    }

    @Test
    @Throws(Exception::class)
    fun `getRepoList whenUserNameIsEmpty shouldReturnValidationError`() {
        //GIVEN
        mockGetRepoListAPI()

        //WHEN
        testSubject.getRepoList("", onRepoListCompletedListener)

        //THEN
        verify(onRepoListCompletedListener).onRepoListRetrievalFailure(ArgumentMatchers.anyString())
    }

    @Test
    @Throws(Exception::class)
    fun `getRepoList whenNetworkErrorHappen shouldReturnFailureError`() {
        //GIVEN
        mockGetRepoListAPI()

        //WHEN
        testSubject.getRepoList(UNLUCKY_ACCOUNT, onRepoListCompletedListener)

        //THEN
        verify(onRepoListCompletedListener).onRepoListRetrievalFailure(ArgumentMatchers.anyString())
    }

    @Test
    @Throws(Exception::class)
    fun `getRepoItemDetails whenUserNameAndProjectIDAreCorrect shouldReturnRepoItemInfo`() {
        //GIVEN
        mockGetRepoItemDetailsAPI()

        //WHEN
        testSubject.getRepoItemDetails(USER_NAME, PROJECT_ID, onRepoDetailsCompletedListener)

        //THEN
        verify(onRepoDetailsCompletedListener).onRepoDetailsRetrievalSuccess(any())
    }

    @Test
    @Throws(Exception::class)
    fun `getRepoItemDetails whenUserNameIsEmpty shouldReturnValidationError`() {
        //GIVEN
        mockGetRepoItemDetailsAPI()

        // WHEN
        testSubject.getRepoItemDetails("", PROJECT_ID, onRepoDetailsCompletedListener)

        //THEN
        verify(onRepoDetailsCompletedListener).onRepoDetailsRetrievalFailure(ArgumentMatchers.anyString())
    }

    @Test
    @Throws(Exception::class)
    fun `getRepoItemDetails whenProjectIDIsEmpty shouldReturnValidationError`() {
        //GIVEN
        mockGetRepoItemDetailsAPI()

        // WHEN
        testSubject.getRepoItemDetails("", PROJECT_ID, onRepoDetailsCompletedListener)

        //THEN
        verify(onRepoDetailsCompletedListener).onRepoDetailsRetrievalFailure(ArgumentMatchers.anyString())
    }

    @Test
    @Throws(Exception::class)
    fun `getRepoItemDetails whenNetworkErrorHappen shouldReturnFailureError`() {
        //GIVEN
        mockGetRepoItemDetailsAPI()

        // WHEN
        testSubject.getRepoItemDetails(UNLUCKY_ACCOUNT, PROJECT_ID, onRepoDetailsCompletedListener)

        //THEN
        verify(onRepoDetailsCompletedListener).onRepoDetailsRetrievalFailure(ArgumentMatchers.anyString())
    }

    private fun mockWeatherServiceAPIs() {
        // Happy Path Scenario ...
        val observable = Observable.just(10)

        `when`(weatherRepository.getWeatherInfo(and(not<String>(ArgumentMatchers.eq(EMPTY_VALUE)), not<String>(ArgumentMatchers.eq(INVALID_CITY))))).thenReturn(observable)

        // Empty City ...
        `when`(weatherRepository.getWeatherInfo(ArgumentMatchers.eq(EMPTY_VALUE)))
                .thenReturn(Observable.error<Any>(
                        RuntimeException(ErrorMessages.CITY_REQUIRED))
                        .cast(Int::class.java))

        // Invalid City ...
        `when`(weatherRepository.getWeatherInfo(ArgumentMatchers.eq(INVALID_CITY)))
                .thenReturn(Observable.error<Any>(
                        InvalidCityException(ErrorMessages.INVALID_CITY_PROVIDED))
                        .cast(Int::class.java))
    }

    private fun mockGetRepoListAPI() {
        // Happy Path Scenario ...
        val repoList = ArrayList<Repo>()
        val observable = Observable.just<List<Repo>>(repoList)

        whenever(repoListRepository.getRepoList(not<String>(ArgumentMatchers.eq(UNLUCKY_ACCOUNT)))).thenReturn(observable)

        // Error Scenario ...
        whenever(repoListRepository.getRepoList(ArgumentMatchers.eq(UNLUCKY_ACCOUNT)))
                .thenReturn(Observable.error(Exception()))
    }

    private fun mockGetRepoItemDetailsAPI() {
        // Happy Path Scenario ...
        val observable = Observable.just(Repo("SampleRepoItem"))

        whenever(repoListRepository.getRepoItemDetails(not(eq(UNLUCKY_ACCOUNT)),
                any())).thenReturn(observable)

        // Error Scenario ...
        whenever(repoListRepository.getRepoItemDetails(eq(UNLUCKY_ACCOUNT), any()))
                .thenReturn(Observable.error(Exception()))
    }

    private fun setupRxSchedulers() {
        RxJavaPlugins.getInstance().reset()
        RxJavaPlugins.getInstance().registerSchedulersHook(object : RxJavaSchedulersHook() {
            override fun getIOScheduler(): Scheduler {
                return Schedulers.immediate()
            }
        })
        RxAndroidPlugins.getInstance().reset()
        RxAndroidPlugins.getInstance().registerSchedulersHook(object : RxAndroidSchedulersHook() {
            override fun getMainThreadScheduler(): Scheduler {
                return Schedulers.immediate()
            }
        })
    }

    companion object {
        private val USER_NAME = "hazems"
        private val CITY = "New York, USA"
        private val PROJECT_ID = "Test"
        private val INVALID_CITY = "INVALID_CITY"
        private val UNLUCKY_ACCOUNT = "UNLUCKY_ACCOUNT"
        private val EMPTY_VALUE = ""
    }
}
