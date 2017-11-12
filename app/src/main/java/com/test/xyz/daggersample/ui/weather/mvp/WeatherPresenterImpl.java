package com.test.xyz.daggersample.ui.weather.mvp;

import com.test.xyz.daggersample.domain.interactor.MainInteractor;

import javax.inject.Inject;

public class WeatherPresenterImpl implements WeatherPresenter {
    private WeatherView mainView;
    private MainInteractor mainInteractor;

    @Inject
    public WeatherPresenterImpl(WeatherView mainView, MainInteractor mainInteractor) {
        this.mainView = mainView;
        this.mainInteractor = mainInteractor;
    }

    @Override
    public void requestWeatherInformation() {
        mainView.showBusyIndicator();
        mainInteractor.getWeatherInformation(mainView.getUserNameText(), mainView.getCityText(), this);
    }

    @Override
    public void onUserNameValidationError(int messageID) {
        mainView.hideBusyIndicator();
        mainView.showUserNameError(messageID);
    }

    @Override
    public void onCityValidationError(int messageID) {
        mainView.hideBusyIndicator();
        mainView.showCityNameError(messageID);
    }

    @Override
    public void onSuccess(String data) {
        mainView.hideBusyIndicator();
        mainView.showResult(data);
    }

    @Override
    public void onFailure(String errorMessage) {
        mainView.hideBusyIndicator();
        mainView.showError(errorMessage);
    }
}
