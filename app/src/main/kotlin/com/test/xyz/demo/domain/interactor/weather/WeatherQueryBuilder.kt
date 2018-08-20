package com.test.xyz.demo.domain.interactor.weather

class WeatherQueryBuilder {
    fun createWeatherQuery(cityName: String): String {
        return "select * from weather.forecast where woeid in (select woeid from geo.places(1) where text=\"$cityName\")"
    }
}
