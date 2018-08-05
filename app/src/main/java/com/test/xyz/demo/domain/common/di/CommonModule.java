package com.test.xyz.demo.domain.common.di;

import android.app.Application;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.Cache;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;

@Module
public class CommonModule {

    @Provides
    @Singleton
    OkHttpClient provideOkHttpClient(Application application) {
        int cacheSize = 15 * 1024 * 1024; // 15 MB
        Cache cache = new Cache(application.getCacheDir(), cacheSize);

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .cache(cache)
                .addInterceptor(interceptor)
                .build();

        return okHttpClient;
    }

    private final Interceptor interceptor = (chain) -> {
        Request original = chain.request();
        HttpUrl originalHttpUrl = original.url();

        HttpUrl url = originalHttpUrl.newBuilder()
                .addQueryParameter("format", "json")
                .addQueryParameter("env", "store://datatables.org/alltableswithkeys")
                .build();

        // Request customization: add request headers
        Request.Builder requestBuilder = original.newBuilder()
                .url(url);

        Request request = requestBuilder.build();
        return chain.proceed(request);
    };

}
