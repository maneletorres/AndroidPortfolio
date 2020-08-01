package com.example.androidportfolio.data.rest.remote;

import com.example.androidportfolio.utils.Constants;
import com.google.gson.JsonElement;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface WeatherRetrofitService {
    @GET("staticweather")
    Single<JsonElement> getWeather(@Query(Constants.QUERY_PARAM) String query,
                                   @Query(Constants.FORMAT_PARAM) String mode,
                                   @Query(Constants.UNITS_PARAM) String units,
                                   @Query(Constants.DAYS_PARAM) String cnt);
}