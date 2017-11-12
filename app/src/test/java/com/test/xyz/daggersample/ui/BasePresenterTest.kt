package com.test.xyz.daggersample.ui

import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.doAnswer
import com.nhaarman.mockito_kotlin.eq
import com.nhaarman.mockito_kotlin.whenever
import com.test.xyz.daggersample.R
import com.test.xyz.daggersample.domain.interactor.MainInteractor
import com.test.xyz.daggersample.domain.repository.api.model.Repo
import com.test.xyz.daggersample.ui.repodetails.mvp.OnRepoDetailsCompletedListener
import com.test.xyz.daggersample.ui.repolist.mvp.OnRepoListCompletedListener
import com.test.xyz.daggersample.ui.weather.mvp.OnWeatherInfoCompletedListener
import org.mockito.AdditionalMatchers.and
import org.mockito.AdditionalMatchers.not

abstract class BasePresenterTest {

    protected fun mockInteractor(mainInteractor: MainInteractor) {
        mockGetInformationAPI(mainInteractor)
        mockGetRepoListAPI(mainInteractor)
        mockGetRepoItemsAPI(mainInteractor)
    }

    private fun mockGetRepoItemsAPI(mainInteractor: MainInteractor) {
        doAnswer({
            val args = it.arguments
            (args[2] as OnRepoDetailsCompletedListener).onRepoDetailsRetrievalFailure("Error happens!!!")
            null
        }).whenever(mainInteractor).getRepoItemDetails(eq(EMPTY_VALUE),
                any< String>(),
                any<OnRepoDetailsCompletedListener>())

        doAnswer{
            val args = it.arguments
            (args[2] as OnRepoDetailsCompletedListener).onRepoDetailsRetrievalSuccess(Repo())
            null
        }.whenever(mainInteractor).getRepoItemDetails(not<String>(eq(EMPTY_VALUE)),
                any<String>(),
                any<OnRepoDetailsCompletedListener>())
    }

    private fun mockGetRepoListAPI(mainInteractor: MainInteractor) {
        doAnswer {
            val args = it.arguments
            (args[1] as OnRepoListCompletedListener).onRepoListRetrievalFailure("Error")
            null
        }.whenever(mainInteractor).getRepoList(eq(EMPTY_VALUE),
                any<OnRepoListCompletedListener>())

        doAnswer({
            val args = it.arguments
            (args[1] as OnRepoListCompletedListener).onRepoListRetrievalSuccess(listOf(Repo()))
            null
        }).whenever(mainInteractor).getRepoList(not<String>(eq(EMPTY_VALUE)),
                any<OnRepoListCompletedListener>())
    }

    private fun mockGetInformationAPI(mainInteractor: MainInteractor) {
        doAnswer {
            val args = it.arguments
            (args[2] as OnWeatherInfoCompletedListener).onFailure("Invalid city provided!!!")
             null
        }.whenever(mainInteractor).getWeatherInformation(any<String>(),
                eq(INVALID_CITY),
                any<OnWeatherInfoCompletedListener>())

        doAnswer {
            val args = it.arguments
            (args[2] as OnWeatherInfoCompletedListener).onUserNameValidationError(R.string.username_empty_message)
             null
        }.whenever(mainInteractor).getWeatherInformation(eq(EMPTY_VALUE),
                eq(VALID_CITY),
                any<OnWeatherInfoCompletedListener>())

        doAnswer {
            val args = it.arguments
            (args[2] as OnWeatherInfoCompletedListener).onCityValidationError(R.string.city_empty_message)
            null
        }.whenever(mainInteractor).getWeatherInformation(any<String>(),
                eq(EMPTY_VALUE),
                any<OnWeatherInfoCompletedListener>())

        doAnswer {
            val args = it.arguments
            (args[2] as OnWeatherInfoCompletedListener).onSuccess(MOCK_INFO_SUCCESS_MSG)
            null
        }.whenever(mainInteractor).getWeatherInformation(not<String>(eq(EMPTY_VALUE)),
                and(not<String>(eq(INVALID_CITY)), not<String>(eq(EMPTY_VALUE))),
                any<OnWeatherInfoCompletedListener>())
    }

    companion object {
        val MOCK_INFO_SUCCESS_MSG = "MOCK INFO SUCCESS MSG"
        val INVALID_CITY = "INVALID"
        val VALID_CITY = "New York, USA"
        val EMPTY_VALUE = ""
    }
}
