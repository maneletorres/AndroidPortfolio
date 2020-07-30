package com.example.androidportfolio.data.rest.remote;

import com.example.androidportfolio.data.model.Movies;
import com.example.androidportfolio.data.model.Reviews;
import com.example.androidportfolio.data.model.Trailers;
import com.example.androidportfolio.utilities.Constants;

import javax.inject.Inject;

import retrofit2.Call;

public class MovieRemoteDataSource {
    private final MovieRetrofitService movieRetrofitService;

    @Inject
    public MovieRemoteDataSource(MovieRetrofitService movieRetrofitService) {
        this.movieRetrofitService = movieRetrofitService;
    }

    public Call<Movies> getMovies(String searchCriteria) {
        return movieRetrofitService.getMovies(searchCriteria, Constants.API_KEY);
    }

    public Call<Trailers> getTrailers(int movieId) {
        return movieRetrofitService.getTrailers(movieId, Constants.API_KEY);
    }

    public Call<Reviews> getReviews(int movieId) {
        return movieRetrofitService.getReviews(movieId, Constants.API_KEY);
    }
}
