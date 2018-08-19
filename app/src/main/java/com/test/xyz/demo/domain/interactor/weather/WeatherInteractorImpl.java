package com.test.xyz.demo.domain.interactor.weather;

import com.google.common.base.Strings;
import com.test.xyz.demo.domain.model.weather.WeatherSummaryInfo;
import com.test.xyz.demo.domain.repository.api.GreetRepository;
import com.test.xyz.demo.domain.repository.api.WeatherRepository;

import io.reactivex.Observable;
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
    public Observable<WeatherSummaryInfo> getWeatherInformation(String userName, String cityName) {
        final String greeting = greetRepository.greet(userName) + "\n";

        if (Strings.isNullOrEmpty(userName)) {
            return Observable.error(new UserNameValidationException("Username must be provided!"));
        }

        if (Strings.isNullOrEmpty(cityName)) {
            return Observable.error(new CityValidationException("City must be provided!"));
        }

        return weatherRepository.getWeatherInfo(weatherQueryBuilder.createWeatherQuery(cityName))
                .map((weatherRawResponse) -> {
                    int temperature = Integer.parseInt(weatherRawResponse.query.results.channel.item.condition.temp);
                    return new WeatherSummaryInfo(cityName, greeting, temperature);
                })
                .subscribeOn(Schedulers.io());
    }
}
