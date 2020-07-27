package com.example.androidportfolio.data.repository;

import com.example.androidportfolio.utilities.Constants;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class TheMovieDatabaseService {
    private TheMovieDatabaseApi theMovieDatabaseApi;

    public TheMovieDatabaseApi getApiService() {
        if (theMovieDatabaseApi == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(Constants.TMDB_BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build();

            theMovieDatabaseApi = retrofit.create(TheMovieDatabaseApi.class);
        }

        return theMovieDatabaseApi;
    }
}
