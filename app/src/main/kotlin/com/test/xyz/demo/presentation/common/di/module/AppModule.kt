package com.test.xyz.demo.presentation.common.di.module

import android.app.Application
import android.content.res.Resources
import com.test.xyz.demo.presentation.common.di.DaggerApplication
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule(internal var app: DaggerApplication) {

    @Provides
    @Singleton
    fun provideApplication(): Application {
        return app
    }

    @Provides
    @Singleton
    fun provideResources(): Resources {
        return app.resources
    }
}
