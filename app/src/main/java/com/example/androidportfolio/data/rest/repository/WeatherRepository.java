package com.example.androidportfolio.data.rest.repository;

import com.example.androidportfolio.data.rest.remote.WeatherRemoteDataSource;
import com.google.gson.JsonElement;

import javax.inject.Inject;

import retrofit2.Call;

public class WeatherRepository {
    private WeatherRemoteDataSource weatherRemoteDataSource;

    @Inject
    public WeatherRepository(WeatherRemoteDataSource weatherRemoteDataSource) {
        this.weatherRemoteDataSource = weatherRemoteDataSource;
    }

    public Call<JsonElement> getForecast(String preferredWeatherLocation) {
        return weatherRemoteDataSource.getWeather(preferredWeatherLocation);
    }
}
