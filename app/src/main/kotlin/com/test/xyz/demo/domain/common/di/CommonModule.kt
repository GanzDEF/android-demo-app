package com.test.xyz.demo.domain.common.di

import android.app.Application
import dagger.Module
import dagger.Provides
import okhttp3.Cache
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import javax.inject.Singleton

@Module
class CommonModule {

    private val interceptor = Interceptor { chain ->
        val original = chain.request()
        val originalHttpUrl = original.url()

        val url = originalHttpUrl.newBuilder()
                .addQueryParameter("format", "json")
                .addQueryParameter("env", "store://datatables.org/alltableswithkeys")
                .build()

        val requestBuilder = original.newBuilder().url(url)

        val request = requestBuilder.build()
        chain.proceed(request)
    }

    @Provides
    @Singleton
    internal fun provideOkHttpClient(application: Application): OkHttpClient {
        val cacheSize = 15 * 1024 * 1024 // 15 MB
        val cache = Cache(application.cacheDir, cacheSize.toLong())

        return OkHttpClient.Builder()
                .cache(cache)
                .addInterceptor(interceptor)
                .build()
    }
}
