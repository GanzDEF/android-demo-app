package com.test.xyz.demo.presentation.weather.presenter;

import com.test.xyz.demo.domain.model.weather.WeatherSummaryInfo;

public final class WeatherDataFormatter {

    public final String format(WeatherSummaryInfo weatherSummaryInfo) {
        StringBuffer output = new StringBuffer(weatherSummaryInfo.introMessage())
                .append("\n").append("Current weather in ")
                .append(weatherSummaryInfo.city())
                .append(" is ")
                .append(weatherSummaryInfo.temperature())
                .append("°F");

        return output.toString();
    }
}

