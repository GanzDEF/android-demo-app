package com.test.xyz.demo.domain.common.di

import android.app.Application
import dagger.Module
import dagger.Provides
import okhttp3.Cache
import okhttp3.OkHttpClient
import javax.inject.Singleton

@Module
class CommonModule {

    @Provides
    @Singleton
    internal fun provideOkHttpClient(application: Application): OkHttpClient {
        val cacheSize = 15 * 1024 * 1024 // 15 MB
        val cache = Cache(application.cacheDir, cacheSize.toLong())

        return OkHttpClient.Builder()
                .cache(cache)
                .build()
    }
}
