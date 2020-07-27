package com.example.androidportfolio.data.repository;

import com.example.androidportfolio.data.model.Movies;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface TheMovieDatabaseApi {

    @GET("movie/{searchCriteria}")
    Call<Movies> getMovies(@Path("searchCriteria") String searchCriteriaQuery,
                           @Query("api_key") String api_key);
}
