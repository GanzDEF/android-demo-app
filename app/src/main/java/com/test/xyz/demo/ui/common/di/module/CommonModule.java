package com.test.xyz.demo.ui.common.di.module;

import com.test.xyz.demo.domain.repository.api.HelloRepository;
import com.test.xyz.demo.domain.repository.api.RepoListRepository;
import com.test.xyz.demo.domain.repository.api.WeatherRepository;
import com.test.xyz.demo.domain.repository.impl.HelloRepositoryReleaseManager;
import com.test.xyz.demo.domain.repository.impl.WeatherRepositoryManager;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class CommonModule {

    @Provides
    @Singleton
    WeatherRepository provideWeatherService() {
        return new WeatherRepositoryManager();
    }

    @Provides
    @Singleton
    RepoListRepository provideRepoListService() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(RepoListRepository.HTTPS_API_GITHUB_URL)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return retrofit.create(RepoListRepository.class);
    }

    @Provides
    @Singleton
    HelloRepository provideHelloService() {
        return new HelloRepositoryReleaseManager();
    }
}
