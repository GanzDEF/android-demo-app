package com.test.xyz.demo.domain.interactor.weather;

import com.test.xyz.demo.domain.repository.api.GreetRepository;
import com.test.xyz.demo.domain.repository.api.WeatherRepository;
import com.test.xyz.demo.domain.repository.impl.GreetRepositoryManager;
import com.test.xyz.demo.domain.repository.impl.WeatherRepositoryManager;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class WeatherModule {
    @Provides
    @Singleton
    WeatherRepository provideWeatherService() {
        return new WeatherRepositoryManager();
    }

    @Provides
    @Singleton
    GreetRepository provideHelloService() {
        return new GreetRepositoryManager();
    }

    @Provides
    @Singleton
    WeatherInteractor provideWeatherInteractor(GreetRepository greetRepository, WeatherRepository weatherRepository) {
        return new WeatherInteractorImpl(greetRepository, weatherRepository);
    }
}
