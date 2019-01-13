package com.test.xyz.demo.domain.interactor.weather;

import com.test.xyz.demo.domain.common.di.CommonModule;
import com.test.xyz.demo.domain.repository.api.GreetRepository;
import com.test.xyz.demo.domain.repository.api.WeatherRepository;
import com.test.xyz.demo.domain.repository.impl.GreetRepositoryManager;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module(includes = CommonModule.class)
public class WeatherModule {

    @Provides
    @Singleton
    WeatherRepository provideWeatherRepository(OkHttpClient client) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(WeatherRepository.HTTPS_API_WEATHER_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();

        return retrofit.create(WeatherRepository.class);
    }

    @Provides
    @Singleton
    GreetRepository provideHelloService() {
        return new GreetRepositoryManager();
    }

    @Provides
    @Singleton
    WeatherInteractor provideWeatherInteractor(GreetRepository greetRepository, WeatherRepository weatherRepository2) {
        return new WeatherInteractorImpl(greetRepository, weatherRepository2);
    }
}
