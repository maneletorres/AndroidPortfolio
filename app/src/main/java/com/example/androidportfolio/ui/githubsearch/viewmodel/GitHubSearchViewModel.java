package com.example.androidportfolio.ui.githubsearch.viewmodel;

import android.os.AsyncTask;
import android.util.Pair;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.androidportfolio.utilities.NetworkUtils;
import com.example.androidportfolio.utils.Resource;

import java.io.IOException;
import java.net.URL;

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
        new GitHubSearchTask().execute(githubQuery);
    }

    private class GitHubSearchTask extends AsyncTask<String, Void, Pair<String, String>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            _loadingGitHubSearchResultObservable.postValue(new Resource<>(LOADING, null, null));
        }

        @Override
        protected Pair<String, String> doInBackground(String... params) {
            URL searchUrl = NetworkUtils.buildGithubUrl(params[0]);
            Pair<String, String> stringPair = null;
            try {
                String githubSearchResults = NetworkUtils.getResponseFromHttpUrl(searchUrl);
                stringPair = new Pair<>(searchUrl.toString(), githubSearchResults);
            } catch (IOException ex) {
                ex.printStackTrace();
            }

            return stringPair;
        }

        @Override
        protected void onPostExecute(Pair<String, String> gitHubSearchResult) {
            if (gitHubSearchResult != null)
                _loadingGitHubSearchResultObservable.postValue(new Resource<>(SUCCESS, gitHubSearchResult, null));
            else _loadingGitHubSearchResultObservable.postValue(new Resource<>(ERROR, null, null));
        }
    }
}
