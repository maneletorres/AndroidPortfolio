package com.example.androidportfolio.data.repository;

import com.google.gson.JsonElement;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface GitHubSearchApi {

    @GET("search/repositories")
    Call<JsonElement> getRepositories(@Query("q") String githubQuery,
                                      @Query("sort") String sort);
}
