package com.example.androidportfolio.di.component;

import com.example.androidportfolio.di.module.ContextModule;
import com.example.androidportfolio.di.module.NetworkModule;
import com.example.androidportfolio.ui.sunshine.view.SunshineFragment;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {NetworkModule.class, ContextModule.class})
public interface AppComponent {
    void inject(SunshineFragment sunshineFragment);
}
