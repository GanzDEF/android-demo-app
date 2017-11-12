package com.test.xyz.daggersample.ui

import com.nhaarman.mockito_kotlin.whenever
import com.test.xyz.daggersample.R
import com.test.xyz.daggersample.domain.interactor.MainInteractor
import com.test.xyz.daggersample.domain.repository.api.model.Repo
import com.test.xyz.daggersample.ui.repodetails.mvp.OnRepoDetailsCompletedListener
import com.test.xyz.daggersample.ui.repolist.mvp.OnRepoListCompletedListener
import com.test.xyz.daggersample.ui.weather.mvp.OnWeatherInfoCompletedListener
import org.mockito.AdditionalMatchers.and
import org.mockito.AdditionalMatchers.not
import org.mockito.ArgumentMatchers
import org.mockito.Mockito.doAnswer

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
        }).whenever(mainInteractor).getRepoItemDetails(ArgumentMatchers.eq(EMPTY_VALUE),
                ArgumentMatchers.any(String::class.java),
                ArgumentMatchers.any(OnRepoDetailsCompletedListener::class.java))

        doAnswer{
            val args = it.arguments
            (args[2] as OnRepoDetailsCompletedListener).onRepoDetailsRetrievalSuccess(Repo())
            null
        }.whenever(mainInteractor).getRepoItemDetails(not<String>(ArgumentMatchers.eq(EMPTY_VALUE)),
                ArgumentMatchers.any(String::class.java),
                ArgumentMatchers.any(OnRepoDetailsCompletedListener::class.java))
    }

    private fun mockGetRepoListAPI(mainInteractor: MainInteractor) {
        doAnswer {
            val args = it.arguments
            (args[1] as OnRepoListCompletedListener).onRepoListRetrievalFailure("Error")
            null
        }.whenever(mainInteractor).getRepoList(ArgumentMatchers.eq(EMPTY_VALUE),
                ArgumentMatchers.any(OnRepoListCompletedListener::class.java))

        doAnswer({
            val args = it.arguments
            (args[1] as OnRepoListCompletedListener).onRepoListRetrievalSuccess(listOf(Repo()))
            null
        }).whenever(mainInteractor).getRepoList(not<String>(ArgumentMatchers.eq(EMPTY_VALUE)), ArgumentMatchers.any(OnRepoListCompletedListener::class.java))
    }

    private fun mockGetInformationAPI(mainInteractor: MainInteractor) {
        doAnswer {
            val args = it.arguments
            (args[2] as OnWeatherInfoCompletedListener).onFailure("Invalid city provided!!!")
             null
        }.whenever(mainInteractor).getWeatherInformation(ArgumentMatchers.anyString(),
                ArgumentMatchers.eq(INVALID_CITY),
                ArgumentMatchers.any(OnWeatherInfoCompletedListener::class.java))

        doAnswer {
            val args = it.arguments
            (args[2] as OnWeatherInfoCompletedListener).onUserNameValidationError(R.string.username_empty_message)
             null
        }.whenever(mainInteractor).getWeatherInformation(ArgumentMatchers.eq(EMPTY_VALUE),
                ArgumentMatchers.eq(VALID_CITY),
                ArgumentMatchers.any(OnWeatherInfoCompletedListener::class.java))

        doAnswer {
            val args = it.arguments
            (args[2] as OnWeatherInfoCompletedListener).onCityValidationError(R.string.city_empty_message)
            null
        }.whenever(mainInteractor).getWeatherInformation(ArgumentMatchers.anyString(),
                ArgumentMatchers.eq(EMPTY_VALUE),
                ArgumentMatchers.any(OnWeatherInfoCompletedListener::class.java))

        doAnswer {
            val args = it.arguments
            (args[2] as OnWeatherInfoCompletedListener).onSuccess(MOCK_INFO_SUCCESS_MSG)
            null
        }.whenever(mainInteractor).getWeatherInformation(not<String>(ArgumentMatchers.eq(EMPTY_VALUE)),
                and(not<String>(ArgumentMatchers.eq(INVALID_CITY)), not<String>(ArgumentMatchers.eq(EMPTY_VALUE))),
                ArgumentMatchers.any(OnWeatherInfoCompletedListener::class.java))
    }

    companion object {
        val MOCK_INFO_SUCCESS_MSG = "MOCK INFO SUCCESS MSG"
        val INVALID_CITY = "INVALID"
        val VALID_CITY = "New York, USA"
        val EMPTY_VALUE = ""
    }
}
