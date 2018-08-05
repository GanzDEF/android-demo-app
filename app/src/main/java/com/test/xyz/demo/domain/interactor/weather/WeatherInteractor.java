package com.test.xyz.demo.domain.interactor.weather;

import com.test.xyz.demo.domain.model.weather.WeatherSummaryInfo;

import io.reactivex.disposables.Disposable;

public interface WeatherInteractor {
    interface WeatherInfoActionCallback {
        void onSuccess(WeatherSummaryInfo weatherSummaryInfo);
        void onFailure();
        void onUserNameValidationError();
        void onCityValidationError();
    }

    Disposable getWeatherInformation(String userName, String cityName, WeatherInfoActionCallback listener);
}
