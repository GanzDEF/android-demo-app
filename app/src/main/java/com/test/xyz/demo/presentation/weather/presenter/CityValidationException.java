package com.test.xyz.demo.presentation.weather.presenter;

public class CityValidationException extends IllegalArgumentException {
    public CityValidationException(String message) {
        super(message);
    }
}
