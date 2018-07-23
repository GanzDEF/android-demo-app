package com.test.xyz.demo.presentation.weather.presenter;

public interface WeatherView {
    String getUserNameText();
    String getCityText();

    void showBusyIndicator();
    void hideBusyIndicator();

    void showResult(String result);

    void showUserNameError(int messageId);
    void showCityNameError(int messageId);
    void showGenericError(int messageId);
}
