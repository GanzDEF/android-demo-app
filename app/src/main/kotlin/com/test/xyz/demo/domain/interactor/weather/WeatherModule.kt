package com.test.xyz.demo.domain.interactor.weather

import com.test.xyz.demo.domain.common.di.CommonModule
import com.test.xyz.demo.domain.repository.api.GreetRepository
import com.test.xyz.demo.domain.repository.api.WeatherRepository
import com.test.xyz.demo.domain.repository.impl.GreetRepositoryManager
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module(includes = arrayOf(CommonModule::class))
class WeatherModule {

    @Provides
    @Singleton
    internal fun provideWeatherRepository(client: OkHttpClient): WeatherRepository {
        val retrofit = Retrofit.Builder()
                .baseUrl(WeatherRepository.HTTPS_API_WEATHER_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()

        return retrofit.create(WeatherRepository::class.java)
    }

    @Provides
    @Singleton
    internal fun provideGreetRepositoryManager(): GreetRepository {
        return GreetRepositoryManager()
    }

    @Provides
    @Singleton
    internal fun provideWeatherInteractor(greetRepository: GreetRepository, weatherRepository: WeatherRepository): WeatherInteractor {
        return WeatherInteractorImpl(greetRepository, weatherRepository, WeatherQueryBuilder())
    }
}
