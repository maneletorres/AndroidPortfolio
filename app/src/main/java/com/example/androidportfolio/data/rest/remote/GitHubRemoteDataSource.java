package com.example.androidportfolio.data.rest.remote;

import com.example.androidportfolio.utilities.Constants;
import com.google.gson.JsonElement;

import javax.inject.Inject;

import retrofit2.Call;

public class GitHubRemoteDataSource {
    private final GitHubRetrofitService gitHubRetrofitService;

    @Inject
    public GitHubRemoteDataSource(GitHubRetrofitService gitHubRetrofitService) {
        this.gitHubRetrofitService = gitHubRetrofitService;
    }

    public Call<JsonElement> getRepositories(String gitHubQuery) {
        return gitHubRetrofitService.getRepositories(gitHubQuery, Constants.sortBy);
    }
}
