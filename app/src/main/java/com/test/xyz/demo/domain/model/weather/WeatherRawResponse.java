
package com.test.xyz.demo.domain.model.weather;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class WeatherRawResponse {

    @SerializedName("query")
    @Expose
    public Query query;

    public static WeatherRawResponse createWeatherSuccessRawResponse(String temp) {
        WeatherRawResponse weatherRawResponse = new WeatherRawResponse();
        weatherRawResponse.query = new Query();
        weatherRawResponse.query.count = 1;
        weatherRawResponse.query.results = new Results();
        weatherRawResponse.query.results.channel = new Channel();
        weatherRawResponse.query.results.channel.item = new Item();
        weatherRawResponse.query.results.channel.item.condition = new Condition();
        weatherRawResponse.query.results.channel.item.condition.temp = temp;

        return weatherRawResponse;
    }
}
