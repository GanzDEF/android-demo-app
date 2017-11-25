package com.test.xyz.demo.ui.common.di.module;

import android.app.Application;

import com.test.xyz.demo.domain.repository.api.HelloRepository;
import com.test.xyz.demo.domain.repository.api.RepoListRepository;
import com.test.xyz.demo.domain.repository.api.WeatherRepository;
import com.test.xyz.demo.domain.repository.impl.HelloRepositoryManager;
import com.test.xyz.demo.domain.repository.impl.WeatherRepositoryManager;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.Cache;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class CommonModule {

    @Provides
    @Singleton
    OkHttpClient provideOkHttpClient(Application application) {
        int cacheSize = 15 * 1024 * 1024; // 15 MB
        Cache cache = new Cache(application.getCacheDir(), cacheSize);

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .cache(cache)
                .build();

        return okHttpClient;
    }

    @Provides
    @Singleton
    WeatherRepository provideWeatherService() {
        return new WeatherRepositoryManager();
    }

    @Provides
    @Singleton
    RepoListRepository provideRepoListService(OkHttpClient client) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(RepoListRepository.HTTPS_API_GITHUB_URL)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();

        return retrofit.create(RepoListRepository.class);
    }

    @Provides
    @Singleton
    HelloRepository provideHelloService() {
        return new HelloRepositoryManager();
    }
}
