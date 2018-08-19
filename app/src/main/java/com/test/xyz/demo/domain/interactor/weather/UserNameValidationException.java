package com.test.xyz.demo.domain.interactor.weather;

public class UserNameValidationException extends IllegalArgumentException {
    public UserNameValidationException(String message) {
        super(message);
    }
}
