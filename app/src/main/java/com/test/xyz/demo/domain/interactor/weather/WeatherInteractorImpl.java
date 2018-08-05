package com.test.xyz.demo.domain.interactor.weather;

import com.google.common.base.Strings;
import com.test.xyz.demo.R;
import com.test.xyz.demo.domain.model.weather.WeatherRawResponse;
import com.test.xyz.demo.domain.model.weather.WeatherSummaryInfo;
import com.test.xyz.demo.domain.repository.api.GreetRepository;
import com.test.xyz.demo.domain.repository.api.WeatherRepository;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class WeatherInteractorImpl implements WeatherInteractor {
    private static final String TAG = WeatherInteractorImpl.class.getName();

    private final GreetRepository greetRepository;
    private final WeatherRepository weatherRepository;
    private final WeatherQueryBuilder weatherQueryBuilder;

    public WeatherInteractorImpl(GreetRepository greetRepository,
                                 WeatherRepository weatherRepository,
                                 WeatherQueryBuilder weatherQueryBuilder) {

        this.greetRepository = greetRepository;
        this.weatherRepository = weatherRepository;
        this.weatherQueryBuilder = weatherQueryBuilder;
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

        weatherRepository.getWeatherInfo(weatherQueryBuilder.createWeatherQuery(cityName))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<WeatherRawResponse>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        listener.onFailure(R.string.weather_error);
                    }

                    @Override
                    public void onNext(WeatherRawResponse weatherRawResponse) {
                        if (weatherRawResponse.query.count == 0) {
                            throw new RuntimeException("No weather information available for this place ...");
                        }

                        try {
                            int temperature = Integer.parseInt(weatherRawResponse.query.results.channel.item.condition.temp);
                            listener.onSuccess(new WeatherSummaryInfo(cityName, greeting, temperature));
                        } catch (Exception exception) {
                            throw new RuntimeException("Unexpected error occurs ...");
                        }
                    }
                });
    }
}
