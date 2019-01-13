package com.test.xyz.demo.domain.common.di;

import android.app.Application;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.Cache;
import okhttp3.OkHttpClient;

@Module
public class CommonModule {

    @Provides
    @Singleton
    OkHttpClient provideOkHttpClient(Application application) {
        int cacheSize = 15 * 1024 * 1024; // 15 MB
        Cache cache = new Cache(application.getCacheDir(), cacheSize);

        //HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        //interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .cache(cache)
                //.addInterceptor(interceptor)
                .build();

        return okHttpClient;
    }
}
