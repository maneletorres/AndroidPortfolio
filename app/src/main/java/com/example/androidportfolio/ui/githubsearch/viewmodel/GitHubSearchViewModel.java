package com.example.androidportfolio.ui.githubsearch.viewmodel;

import android.util.Pair;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.androidportfolio.data.rest.repository.GitHubRepository;
import com.example.androidportfolio.utils.NetworkUtils;
import com.example.androidportfolio.utils.Resource;
import com.google.gson.JsonElement;

import java.net.URL;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

import static com.example.androidportfolio.utils.Status.ERROR;
import static com.example.androidportfolio.utils.Status.LOADING;
import static com.example.androidportfolio.utils.Status.SUCCESS;

public class GitHubSearchViewModel extends ViewModel {
    private final MutableLiveData<Resource<Pair<String, String>>> _loadingGitHubSearchResultObservable = new MutableLiveData<>();
    private final GitHubRepository gitHubRepository;
    private CompositeDisposable disposable = new CompositeDisposable();

    @Inject
    public GitHubSearchViewModel(GitHubRepository gitHubRepository) {
        this.gitHubRepository = gitHubRepository;
    }

    public LiveData<Resource<Pair<String, String>>> loadingGitHubSearchResultObservable() {
        return _loadingGitHubSearchResultObservable;
    }

    public void loadGitHubSearch(String gitHubQuery) {
        _loadingGitHubSearchResultObservable.postValue(new Resource<>(LOADING, null, null));

        disposable.add(gitHubRepository.getRepositories(gitHubQuery)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<JsonElement>() {
                    @Override
                    public void onSuccess(JsonElement jsonElement) {
                        URL searchUrl = NetworkUtils.buildGithubUrl(gitHubQuery);
                        Pair<String, String> stringPair = new Pair<>(searchUrl.toString(), jsonElement.toString());

                        _loadingGitHubSearchResultObservable.postValue(new Resource<>(SUCCESS, stringPair, null));
                    }

                    @Override
                    public void onError(Throwable e) {
                        _loadingGitHubSearchResultObservable.postValue(new Resource<>(ERROR, null, null));
                    }
                }));
    }
}