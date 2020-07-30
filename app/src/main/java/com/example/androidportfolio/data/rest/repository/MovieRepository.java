package com.example.androidportfolio.data.rest.repository;

import com.example.androidportfolio.data.model.Movies;
import com.example.androidportfolio.data.model.Reviews;
import com.example.androidportfolio.data.model.Trailers;
import com.example.androidportfolio.data.rest.remote.MovieRemoteDataSource;

import javax.inject.Inject;

import retrofit2.Call;

public class MovieRepository {

    private MovieRemoteDataSource movieRemoteDataSource;

    @Inject
    public MovieRepository(MovieRemoteDataSource movieRemoteDataSource) {
        this.movieRemoteDataSource = movieRemoteDataSource;
    }

    public Call<Movies> getMovies(String searchCriteria) {
        return movieRemoteDataSource.getMovies(searchCriteria);
    }

    public Call<Trailers> getTrailers(int movieId) {
        return movieRemoteDataSource.getTrailers(movieId);
    }

    public Call<Reviews> getReviews(int movieId) {
        return movieRemoteDataSource.getReviews(movieId);
    }
}
