package com.test.xyz.demo.presentation.weather.presenter

interface WeatherView {
    fun getUserNameText(): String
    fun getCityText(): String

    fun showBusyIndicator()
    fun hideBusyIndicator()

    fun showResult(result: String)

    fun showUserNameError(messageId: Int)
    fun showCityNameError(messageId: Int)
    fun showGenericError(messageId: Int)
}
