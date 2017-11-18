package com.test.xyz.demo.ui.weather.mvp;

public interface OnWeatherInfoCompletedListener {
    void onUserNameValidationError(int messageID);

    void onCityValidationError(int messageID);

    void onSuccess(String data);

    void onFailure(String errorMessage);
}
