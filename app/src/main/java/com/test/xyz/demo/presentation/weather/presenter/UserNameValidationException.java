package com.test.xyz.demo.presentation.weather.presenter;

public class UserNameValidationException extends IllegalArgumentException {
    public UserNameValidationException(String message) {
        super(message);
    }
}
