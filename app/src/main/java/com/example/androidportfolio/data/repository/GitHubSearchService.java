package com.example.androidportfolio.data.repository;

import com.example.androidportfolio.utilities.Constants;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class GitHubSearchService {
    private GitHubSearchApi gitHubSearchApi;

    public GitHubSearchApi getGitHubSearchApi() {
        if (gitHubSearchApi == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(Constants.GITHUB_BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build();

            gitHubSearchApi = retrofit.create(GitHubSearchApi.class);
        }

        return gitHubSearchApi;
    }
}
