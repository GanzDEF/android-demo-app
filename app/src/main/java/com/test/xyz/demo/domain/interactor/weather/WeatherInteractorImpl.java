package com.test.xyz.demo.domain.interactor.weather;

import com.google.common.base.Strings;
import com.test.xyz.demo.domain.model.weather.WeatherRawResponse;
import com.test.xyz.demo.domain.model.weather.WeatherSummaryInfo;
import com.test.xyz.demo.domain.repository.api.GreetRepository;
import com.test.xyz.demo.domain.repository.api.WeatherRepository;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

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
    public Disposable getWeatherInformation(final String userName, final String cityName, final WeatherInfoActionCallback listener) {
        final String greeting = greetRepository.greet(userName) + "\n";

        if (Strings.isNullOrEmpty(userName)) {
            listener.onUserNameValidationError();
            return null;
        }

        if (Strings.isNullOrEmpty(cityName)) {
            listener.onCityValidationError();
            return null;
        }

        return weatherRepository.getWeatherInfo(weatherQueryBuilder.createWeatherQuery(cityName))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<WeatherRawResponse>() {
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

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        listener.onFailure();
                    }

                    @Override
                    public void onComplete() {
                    }
                });
    }
}
