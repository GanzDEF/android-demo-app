package com.test.xyz.demo.presentation.weather.presenter;

class WeatherDegreeConverter {
    public static int convertFahrenheitToCelsius(float temperature) {
        return (int) (((temperature - 32) * 5 ) / 9);
    }
}
