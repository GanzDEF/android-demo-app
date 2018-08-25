package com.test.xyz.demo.presentation.common.di

import android.app.Application
import android.content.Context
import com.test.xyz.demo.presentation.common.di.component.AppComponent
import com.test.xyz.demo.presentation.common.di.component.DaggerAppComponent
import com.test.xyz.demo.presentation.common.di.module.AppModule

class DaggerApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        instance = this
        initAppComponents()
    }

    fun getAppComponent(): AppComponent {
        return appComponent
    }

    fun initAppComponents() {
        appComponent = DaggerAppComponent.builder()
                .appModule(AppModule(this))
                .build()

    }

    companion object {
        private lateinit var appComponent: AppComponent
        private lateinit var instance: DaggerApplication

        operator fun get(context: Context): DaggerApplication {
            return context.applicationContext as DaggerApplication
        }
    }
}
