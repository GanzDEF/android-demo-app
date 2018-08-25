package com.test.xyz.demo.domain.model.weather

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class WeatherRawResponse {

    @SerializedName("query")
    @Expose
    var query: Query? = null

    fun temperature(): String? {
        return query?.results?.channel?.item?.condition?.temp;
    }

    companion object {

        fun createWeatherSuccessRawResponse(temp: String): WeatherRawResponse {
            val weatherRawResponse = WeatherRawResponse()

            weatherRawResponse.query = Query()

            weatherRawResponse.query?.count = 1
            weatherRawResponse.query?.results = Results()
            weatherRawResponse.query?.results?.channel = Channel()
            weatherRawResponse.query?.results?.channel?.item = Item()
            weatherRawResponse.query?.results?.channel?.item?.condition = Condition()
            weatherRawResponse.query?.results?.channel?.item?.condition?.temp = temp

            return weatherRawResponse
        }
    }
}
