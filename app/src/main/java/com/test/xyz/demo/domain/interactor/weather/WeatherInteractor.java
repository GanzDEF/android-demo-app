package com.test.xyz.demo.domain.interactor.weather;

import com.test.xyz.demo.domain.model.weather.WeatherSummaryInfo;

public interface WeatherInteractor {
    interface WeatherInfoActionCallback {
        void onSuccess(WeatherSummaryInfo weatherSummaryInfo);
        void onFailure(int messageID);
        void onUserNameValidationError(int messageID);
        void onCityValidationError(int messageID);
    }

    void getWeatherInformation(String userName, String cityName, WeatherInfoActionCallback listener);
}
