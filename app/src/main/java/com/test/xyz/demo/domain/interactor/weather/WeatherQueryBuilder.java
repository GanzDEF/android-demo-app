package com.test.xyz.demo.domain.interactor.weather;

class WeatherQueryBuilder {
    public final String createWeatherQuery(String cityName) {
        return "select * from weather.forecast where woeid in (select woeid from geo.places(1) where text=\"" + cityName + "\")";
    }
}
