package com.test.xyz.demo.ui.weather.vp;

public interface OnWeatherInfoCompletedListener {
    void onSuccess(String data);

    void onUserNameValidationError(int messageID);
    void onCityValidationError(int messageID);
    void onFailure(int messageID);
}
