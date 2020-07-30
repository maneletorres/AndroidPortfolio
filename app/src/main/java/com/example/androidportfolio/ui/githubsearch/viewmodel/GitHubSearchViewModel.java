package com.example.androidportfolio.ui.githubsearch.viewmodel;

import android.util.Pair;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.androidportfolio.data.repository.GitHubSearchService;
import com.example.androidportfolio.utilities.NetworkUtils;
import com.example.androidportfolio.utils.Resource;
import com.google.gson.JsonElement;

import java.net.URL;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.androidportfolio.utils.Status.ERROR;
import static com.example.androidportfolio.utils.Status.LOADING;
import static com.example.androidportfolio.utils.Status.SUCCESS;

public class GitHubSearchViewModel extends ViewModel {

    // Observables:
    private final MutableLiveData<Resource<Pair<String, String>>> _loadingGitHubSearchResultObservable = new MutableLiveData<>();

    public LiveData<Resource<Pair<String, String>>> loadingGitHubSearchResultObservable() {
        return _loadingGitHubSearchResultObservable;
    }

    public void loadGitHubSearch(String githubQuery) {
        _loadingGitHubSearchResultObservable.postValue(new Resource<>(LOADING, null, null));

        new GitHubSearchService().getGitHubSearchApi().getRepositories(githubQuery, "stars")
                .enqueue(new Callback<JsonElement>() {
                    @Override
                    public void onResponse(@NonNull Call<JsonElement> call, @NonNull Response<JsonElement> response) {
                        JsonElement gitHubResponse = response.body();
                        if (gitHubResponse != null) {
                            URL searchUrl = NetworkUtils.buildGithubUrl(githubQuery);
                            Pair<String, String> stringPair = new Pair<>(searchUrl.toString(), gitHubResponse.toString());

                            _loadingGitHubSearchResultObservable.postValue(new Resource<>(SUCCESS, stringPair, null));
                        } else
                            _loadingGitHubSearchResultObservable.postValue(new Resource<>(ERROR, null, null));
                    }

                    @Override
                    public void onFailure(@NonNull Call<JsonElement> call, @NonNull Throwable t) {
                        _loadingGitHubSearchResultObservable.postValue(new Resource<>(ERROR, null, null));
                    }
                });
    }
}
