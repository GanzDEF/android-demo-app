package com.test.xyz.demo.presentation.weather.presenter;

import com.test.xyz.demo.domain.model.WeatherInfo;

public final class WeatherDataFormatter {

    public final String format(WeatherInfo weatherInfo) {
        StringBuffer output = new StringBuffer(weatherInfo.introMessage())
                .append("\n").append("Current weatherInfo in ")
                .append(weatherInfo.city())
                .append(" is ")
                .append(weatherInfo.temperature())
                .append("°F");

        return output.toString();
    }
}

