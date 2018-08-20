package com.test.xyz.demo.presentation.weather.presenter

class WeatherDegreeConverterProxy {
    fun convertFahrenheitToCelsius(temperature: Float): Int {
        return WeatherDegreeConverter.convertFahrenheitToCelsius(temperature)
    }
}
