package com.example.androidportfolio.data.rest.remote;

import com.example.androidportfolio.utils.Constants;
import com.google.gson.JsonElement;

import javax.inject.Inject;

import io.reactivex.Single;

public class WeatherRemoteDataSource {
    private final WeatherRetrofitService weatherRetrofitService;

    @Inject
    public WeatherRemoteDataSource(WeatherRetrofitService weatherRetrofitService) {
        this.weatherRetrofitService = weatherRetrofitService;
    }

    public Single<JsonElement> getWeather(String preferredWeatherLocation) {
        return weatherRetrofitService.getWeather(preferredWeatherLocation, Constants.format, Constants.units, Integer.toString(Constants.numDays));
    }
}
