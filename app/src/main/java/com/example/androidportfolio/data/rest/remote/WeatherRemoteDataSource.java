package com.example.androidportfolio.data.rest.remote;

import com.example.androidportfolio.utilities.Constants;
import com.google.gson.JsonElement;

import javax.inject.Inject;

import retrofit2.Call;

public class WeatherRemoteDataSource {
    private final WeatherRetrofitService weatherRetrofitService;

    @Inject
    public WeatherRemoteDataSource(WeatherRetrofitService weatherRetrofitService) {
        this.weatherRetrofitService = weatherRetrofitService;
    }

    public Call<JsonElement> getWeather(String preferredWeatherLocation) {
        return weatherRetrofitService.getWeather(preferredWeatherLocation, Constants.format, Constants.units, Integer.toString(Constants.numDays));
    }
}
