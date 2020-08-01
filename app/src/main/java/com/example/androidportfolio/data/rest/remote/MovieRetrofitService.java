package com.example.androidportfolio.data.rest.remote;

import com.example.androidportfolio.data.model.Movies;
import com.example.androidportfolio.data.model.Reviews;
import com.example.androidportfolio.data.model.Trailers;

import io.reactivex.Single;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface MovieRetrofitService {
    @GET("movie/{searchCriteria}")
    Single<Movies> getMovies(@Path("searchCriteria") String searchCriteriaQuery,
                             @Query("api_key") String api_key);

    @GET("movie/{movieId}/videos")
    Single<Trailers> getTrailers(@Path("movieId") int movieId,
                               @Query("api_key") String api_key);

    @GET("movie/{movieId}/reviews")
    Single<Reviews> getReviews(@Path("movieId") int movieId,
                             @Query("api_key") String api_key);
}
