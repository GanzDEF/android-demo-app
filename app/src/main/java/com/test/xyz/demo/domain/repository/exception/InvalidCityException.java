package com.test.xyz.demo.domain.repository.exception;

public class InvalidCityException extends Exception {
    private String message;

    public InvalidCityException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
