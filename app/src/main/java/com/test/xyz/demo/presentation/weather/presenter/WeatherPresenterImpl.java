package com.test.xyz.demo.presentation.weather.presenter;

import com.test.xyz.demo.R;
import com.test.xyz.demo.domain.interactor.weather.WeatherInteractor;
import com.test.xyz.demo.domain.model.weather.WeatherSummaryInfo;
import com.test.xyz.demo.presentation.common.DisposableManager;

import javax.inject.Inject;

public class WeatherPresenterImpl implements WeatherPresenter, WeatherInteractor.WeatherInfoActionCallback {
    private final WeatherView mainView;
    private final WeatherInteractor weatherInteractor;
    private final WeatherDataFormatter weatherDataFormatter;
    private final DisposableManager disposableManager;

    @Inject
    public WeatherPresenterImpl(WeatherView mainView, WeatherInteractor weatherInteractor,
                                WeatherDataFormatter weatherDataFormatter) {

        this.mainView = mainView;
        this.weatherInteractor = weatherInteractor;
        this.weatherDataFormatter = weatherDataFormatter;
        this.disposableManager = new DisposableManager();
    }

    @Override
    public void requestWeatherInformation() {
        mainView.showBusyIndicator();

        disposableManager.add(
                weatherInteractor.getWeatherInformation(mainView.getUserNameText(), mainView.getCityText(), this)
        );
    }

    @Override
    public void onStop() {
        disposableManager.dispose();
    }

    @Override
    public void onUserNameValidationError() {
        mainView.hideBusyIndicator();
        mainView.showUserNameError(R.string.username_empty_message);
    }

    @Override
    public void onCityValidationError() {
        mainView.hideBusyIndicator();
        mainView.showCityNameError(R.string.city_empty_message);
    }

    @Override
    public void onSuccess(WeatherSummaryInfo weatherSummaryInfo) {
        mainView.hideBusyIndicator();
        mainView.showResult(weatherDataFormatter.format(weatherSummaryInfo));
    }

    @Override
    public void onFailure() {
        mainView.hideBusyIndicator();
        mainView.showGenericError(R.string.weather_error);
    }
}
