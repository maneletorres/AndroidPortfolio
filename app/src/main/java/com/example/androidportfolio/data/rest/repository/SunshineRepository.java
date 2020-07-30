package com.example.androidportfolio.data.rest.repository;

import com.example.androidportfolio.data.rest.remote.SunshineRemoteDataSource;
import com.google.gson.JsonElement;

import javax.inject.Inject;

import retrofit2.Call;

public class SunshineRepository {
    private SunshineRemoteDataSource sunshineRemoteDataSource;

    @Inject
    public SunshineRepository(SunshineRemoteDataSource sunshineRemoteDataSource) {
        this.sunshineRemoteDataSource = sunshineRemoteDataSource;
    }

    public Call<JsonElement> getForecast(String preferredWeatherLocation) {
        return sunshineRemoteDataSource.getWeather(preferredWeatherLocation);
    }
}
