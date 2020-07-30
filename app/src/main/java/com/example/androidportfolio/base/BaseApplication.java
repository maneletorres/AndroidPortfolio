package com.example.androidportfolio.base;

import android.app.Application;

import com.example.androidportfolio.di.component.AppComponent;
import com.example.androidportfolio.di.component.DaggerAppComponent;

public class BaseApplication extends Application {
    private AppComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        appComponent = DaggerAppComponent.create();
    }

    public AppComponent getAppComponent() {
        return appComponent;
    }
}
