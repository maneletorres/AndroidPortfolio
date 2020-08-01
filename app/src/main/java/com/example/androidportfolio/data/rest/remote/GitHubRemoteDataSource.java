package com.example.androidportfolio.data.rest.remote;

import com.example.androidportfolio.utils.Constants;
import com.google.gson.JsonElement;

import javax.inject.Inject;

import io.reactivex.Single;
import retrofit2.Call;

public class GitHubRemoteDataSource {
    private final GitHubRetrofitService gitHubRetrofitService;

    @Inject
    public GitHubRemoteDataSource(GitHubRetrofitService gitHubRetrofitService) {
        this.gitHubRetrofitService = gitHubRetrofitService;
    }

    public Single<JsonElement> getRepositories(String gitHubQuery) {
        return gitHubRetrofitService.getRepositories(gitHubQuery, Constants.sortBy);
    }
}
