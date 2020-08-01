package com.example.androidportfolio.data.rest.remote;

import com.example.androidportfolio.data.model.Movies;
import com.example.androidportfolio.data.model.Reviews;
import com.example.androidportfolio.data.model.Trailers;
import com.example.androidportfolio.utils.Constants;

import javax.inject.Inject;

import io.reactivex.Single;

public class MovieRemoteDataSource {
    private final MovieRetrofitService movieRetrofitService;

    @Inject
    public MovieRemoteDataSource(MovieRetrofitService movieRetrofitService) {
        this.movieRetrofitService = movieRetrofitService;
    }

    public Single<Movies> getMovies(String searchCriteria) {
        return movieRetrofitService.getMovies(searchCriteria, Constants.API_KEY);
    }

    public Single<Trailers> getTrailers(int movieId) {
        return movieRetrofitService.getTrailers(movieId, Constants.API_KEY);
    }

    public Single<Reviews> getReviews(int movieId) {
        return movieRetrofitService.getReviews(movieId, Constants.API_KEY);
    }
}
