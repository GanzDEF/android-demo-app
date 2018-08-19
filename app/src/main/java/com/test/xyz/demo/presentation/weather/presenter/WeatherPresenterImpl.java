package com.test.xyz.demo.presentation.weather.presenter;

import com.test.xyz.demo.R;
import com.test.xyz.demo.domain.interactor.weather.CityValidationException;
import com.test.xyz.demo.domain.interactor.weather.UserNameValidationException;
import com.test.xyz.demo.domain.interactor.weather.WeatherInteractor;
import com.test.xyz.demo.domain.model.weather.WeatherSummaryInfo;
import com.test.xyz.demo.presentation.common.DisposableManager;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;

public class WeatherPresenterImpl implements WeatherPresenter {
    private final WeatherView mainView;
    private final WeatherInteractor weatherInteractor;
    private final WeatherDataFormatter weatherDataFormatter;
    private final WeatherDegreeConverterProxy weatherDegreeConverterProxy;
    private final DisposableManager disposableManager;

    @Inject
    public WeatherPresenterImpl(WeatherView mainView, WeatherInteractor weatherInteractor,
                                WeatherDataFormatter weatherDataFormatter,
                                WeatherDegreeConverterProxy weatherDegreeConverterProxy) {

        this.mainView = mainView;
        this.weatherInteractor = weatherInteractor;
        this.weatherDataFormatter = weatherDataFormatter;
        this.weatherDegreeConverterProxy = weatherDegreeConverterProxy;
        this.disposableManager = new DisposableManager();
    }

    @Override
    public void requestWeatherInformation() {
        mainView.showBusyIndicator();

        Disposable disposable = weatherInteractor.getWeatherInformation(mainView.getUserNameText(), mainView.getCityText())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<WeatherSummaryInfo>() {
                    @Override
                    public void onNext(WeatherSummaryInfo weatherSummaryInfo) {
                        mainView.hideBusyIndicator();

                        weatherSummaryInfo.setTemperature(
                                weatherDegreeConverterProxy.convertFahrenheitToCelsius(weatherSummaryInfo.temperature())
                        );

                        mainView.showResult(weatherDataFormatter.format(weatherSummaryInfo));
                    }

                    @Override
                    public void onError(Throwable e) {
                        mainView.hideBusyIndicator();

                        if (e instanceof UserNameValidationException) {
                            mainView.showUserNameError(R.string.username_validation_error_message);
                            return;
                        }

                        if (e instanceof CityValidationException) {
                            mainView.showCityNameError(R.string.cityname_validation_error_message);
                            return;
                        }

                        mainView.showGenericError(R.string.weather_error);
                    }

                    @Override
                    public void onComplete() {
                    }
                });

        disposableManager.add(disposable);
    }

    @Override
    public void onStop() {
        disposableManager.dispose();
    }
}
