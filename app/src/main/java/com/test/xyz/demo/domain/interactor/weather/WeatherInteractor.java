package com.test.xyz.demo.domain.interactor.weather;

public interface WeatherInteractor {
    interface WeatherInfoActionCallback {
        void onSuccess(String data);
        void onFailure(int messageID);
        void onUserNameValidationError(int messageID);
        void onCityValidationError(int messageID);
    }

    void getWeatherInformation(String userName, String cityName, WeatherInfoActionCallback listener);
}
