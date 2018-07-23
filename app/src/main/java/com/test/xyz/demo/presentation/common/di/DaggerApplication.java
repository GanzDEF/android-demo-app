package com.test.xyz.demo.presentation.common.di;

import android.app.Application;
import android.content.Context;
import android.support.annotation.VisibleForTesting;

import com.test.xyz.demo.presentation.common.di.component.AppComponent;
import com.test.xyz.demo.presentation.common.di.component.DaggerAppComponent;
import com.test.xyz.demo.presentation.common.di.module.AppModule;

public class DaggerApplication extends Application {
    private static AppComponent appComponent;
    private static DaggerApplication instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        initAppComponents();
    }

    public static DaggerApplication get(Context context) {
        return (DaggerApplication) context.getApplicationContext();
    }

    public AppComponent getAppComponent() {
        return appComponent;
    }

    public void initAppComponents() {
        appComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .build();

    }

    /**
     * Visible only for testing purposes.
     */
    @VisibleForTesting
    public void setTestComponent(AppComponent testingComponent) {
        appComponent = testingComponent;
    }
}
