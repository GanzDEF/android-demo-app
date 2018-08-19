package com.test.xyz.demo.domain.interactor.weather;

public class CityValidationException extends IllegalArgumentException {
    public CityValidationException(String message) {
        super(message);
    }
}
