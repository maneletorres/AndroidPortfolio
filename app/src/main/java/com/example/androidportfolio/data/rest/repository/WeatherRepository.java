package com.example.androidportfolio.data.rest.repository;

import com.example.androidportfolio.data.rest.remote.WeatherRemoteDataSource;
import com.google.gson.JsonElement;

import javax.inject.Inject;

import io.reactivex.Single;

public class WeatherRepository {
    private WeatherRemoteDataSource weatherRemoteDataSource;

    @Inject
    public WeatherRepository(WeatherRemoteDataSource weatherRemoteDataSource) {
        this.weatherRemoteDataSource = weatherRemoteDataSource;
    }

    public Single<JsonElement> getForecast(String preferredWeatherLocation) {
        return weatherRemoteDataSource.getWeather(preferredWeatherLocation);
    }
}
