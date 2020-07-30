package com.example.androidportfolio.data.rest.remote;

import com.example.androidportfolio.utilities.Constants;
import com.google.gson.JsonElement;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface GitHubRetrofitService {
    @GET("search/repositories")
    Call<JsonElement> getRepositories(@Query(Constants.QUERY_PARAM) String githubQuery,
                                      @Query(Constants.SORT_PARAM) String sort);
}
