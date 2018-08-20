package com.test.xyz.demo.presentation.weather.presenter

internal object WeatherDegreeConverter {
    fun convertFahrenheitToCelsius(temperature: Float): Int {
        return ((temperature - 32) * 5 / 9).toInt()
    }
}
