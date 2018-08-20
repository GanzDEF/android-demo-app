package com.test.xyz.demo.presentation.weather.presenter

import com.test.xyz.demo.domain.model.weather.WeatherSummaryInfo

class WeatherDataFormatter {

    fun format(weatherSummaryInfo: WeatherSummaryInfo): String {
        val output = StringBuffer(weatherSummaryInfo.introMessage())
                .append("\n").append("Current weather in ")
                .append(weatherSummaryInfo.city())
                .append(" is ")
                .append(weatherSummaryInfo.temperature())
                .append("°C")

        return output.toString()
    }
}

