package com.example.androidportfolio.data.rest.remote;

import com.example.androidportfolio.utilities.Constants;
import com.google.gson.JsonElement;

import javax.inject.Inject;

import retrofit2.Call;

public class SunshineRemoteDataSource {
    private final SunshineRetrofitService sunshineRetrofitService;

    @Inject
    public SunshineRemoteDataSource(SunshineRetrofitService sunshineRetrofitService) {
        this.sunshineRetrofitService = sunshineRetrofitService;
    }

    public Call<JsonElement> getWeather(String preferredWeatherLocation) {
        return sunshineRetrofitService.getWeather(preferredWeatherLocation, Constants.format, Constants.units, Integer.toString(Constants.numDays));
    }
}
