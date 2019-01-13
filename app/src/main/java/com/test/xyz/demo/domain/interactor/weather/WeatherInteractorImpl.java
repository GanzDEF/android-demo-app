package com.test.xyz.demo.domain.interactor.weather;

import android.util.Log;

import com.google.common.base.Strings;
import com.test.xyz.demo.BuildConfig;
import com.test.xyz.demo.domain.model.weather.WeatherSummaryInfo;
import com.test.xyz.demo.domain.repository.api.GreetRepository;
import com.test.xyz.demo.domain.repository.api.WeatherRepository;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

public class WeatherInteractorImpl implements WeatherInteractor {
    private static final String TAG = WeatherInteractorImpl.class.getName();

    private final GreetRepository greetRepository;
    private final WeatherRepository weatherRepository;

    public WeatherInteractorImpl(GreetRepository greetRepository,
                                 WeatherRepository weatherRepository) {

        this.greetRepository = greetRepository;
        this.weatherRepository = weatherRepository;
    }

    @Override
    public Observable<WeatherSummaryInfo> getWeatherInformation(String userName, String cityName) {
        final String greeting = greetRepository.greet(userName) + "\n";

        if (Strings.isNullOrEmpty(userName)) {
            return Observable.error(new UserNameValidationException("Username must be provided!"));
        }

        if (Strings.isNullOrEmpty(cityName)) {
            return Observable.error(new CityValidationException("City must be provided!"));
        }

        return weatherRepository.getWeatherInfo(cityName, BuildConfig.OPEN_WEATHER_APP_KEY, "imperial")
                .map((result) -> {
                    int temperature = result.getMain().getTemp().intValue();
                    return new WeatherSummaryInfo(cityName, greeting, temperature);
                })
                .doOnError((error) -> {
                    error.printStackTrace();
                    Log.e(TAG, "error = " + error.getMessage());
                })
                .subscribeOn(Schedulers.io());
    }
}
