package com.test.xyz.demo.domain.model.weather

class WeatherSummaryInfo(private val city: String?, private val introMessage: String?, private var temperature: Int) {

    fun setTemperature(temperature: Int) {
        this.temperature = temperature
    }

    fun city(): String? {
        return city
    }

    fun temperature(): Int {
        return temperature
    }

    fun introMessage(): String? {
        return introMessage
    }
}
