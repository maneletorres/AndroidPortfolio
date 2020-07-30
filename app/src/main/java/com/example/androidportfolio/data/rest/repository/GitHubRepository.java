package com.example.androidportfolio.data.rest.repository;

import com.example.androidportfolio.data.rest.remote.GitHubRemoteDataSource;
import com.google.gson.JsonElement;

import javax.inject.Inject;

import retrofit2.Call;

public class GitHubRepository {
    private GitHubRemoteDataSource gitHubRemoteDataSource;

    @Inject
    public GitHubRepository(GitHubRemoteDataSource gitHubRemoteDataSource) {
        this.gitHubRemoteDataSource = gitHubRemoteDataSource;
    }

    public Call<JsonElement> getRepositories(String gitHubQuery) {
        return gitHubRemoteDataSource.getRepositories(gitHubQuery);
    }
}
