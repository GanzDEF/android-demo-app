package com.test.xyz.demo.domain.interactor.weather;

import com.google.common.base.Strings;
import com.test.xyz.demo.R;
import com.test.xyz.demo.domain.repository.api.GreetRepository;
import com.test.xyz.demo.domain.repository.api.WeatherRepository;

import rx.Observer;

public class WeatherInteractorImpl implements WeatherInteractor {
    private static final String TAG = WeatherInteractorImpl.class.getName();

    private final GreetRepository greetRepository;
    private final WeatherRepository weatherRepository;

    public WeatherInteractorImpl(GreetRepository greetRepository, WeatherRepository weatherRepository) {
        this.greetRepository = greetRepository;
        this.weatherRepository = weatherRepository;
    }

    @Override
    public void getWeatherInformation(final String userName, final String cityName, final WeatherInfoActionCallback listener) {
        final String greeting = greetRepository.greet(userName) + "\n";

        if (Strings.isNullOrEmpty(userName)) {
            listener.onUserNameValidationError(R.string.username_empty_message);
            return;
        }

        if (Strings.isNullOrEmpty(cityName)) {
            listener.onCityValidationError(R.string.city_empty_message);
            return;
        }

        weatherRepository.getWeatherInfo(cityName).subscribe(new Observer<Integer>() {
            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {
                listener.onFailure(R.string.weather_error);
            }

            @Override
            public void onNext(Integer temperature) {
                String temp = "Current weather in " + cityName + " is " + temperature + "°F";
                listener.onSuccess(greeting + temp);
            }
        });
    }}
