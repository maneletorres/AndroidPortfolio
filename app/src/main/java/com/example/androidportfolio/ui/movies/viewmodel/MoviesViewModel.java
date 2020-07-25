package com.example.androidportfolio.ui.movies.viewmodel;

import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.androidportfolio.data.model.Movie;
import com.example.androidportfolio.utilities.MovieParser;
import com.example.androidportfolio.utilities.NetworkUtils;
import com.example.androidportfolio.utils.Resource;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static com.example.androidportfolio.utils.Status.ERROR;
import static com.example.androidportfolio.utils.Status.LOADING;
import static com.example.androidportfolio.utils.Status.SUCCESS;

public class MoviesViewModel extends ViewModel {
    private final MutableLiveData<Resource<List<Movie>>> _loadingMoviesObservable = new MutableLiveData<>();
    private String searchCriteria = NetworkUtils.POPULAR_PARAM;

    public LiveData<Resource<List<Movie>>> loadingMoviesObservable() {
        return _loadingMoviesObservable;
    }

    public void start() {
        new FetchMoviesTask().execute(searchCriteria);
    }

    public void loadMovies(String searchParameter) {
        searchCriteria = searchParameter;
        new FetchMoviesTask().execute(searchCriteria);
    }

    // TODO - Apply coroutine to do the API call.
    public class FetchMoviesTask extends AsyncTask<String, Void, List<Movie>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            _loadingMoviesObservable.postValue(new Resource<>(LOADING, null, null));
        }

        @Override
        protected List<Movie> doInBackground(String... params) {
            if (params == null) return null;

            URL moviesRequestUrl = NetworkUtils.buildTMDBUrl(params[0]);

            List<Movie> movieDataJson = new ArrayList<>();
            try {
                String jsonMoviesResponse = NetworkUtils.getResponseFromHttpUrl(moviesRequestUrl);
                movieDataJson = MovieParser.getMoviesFromJson(jsonMoviesResponse);
            } catch (Exception e) {
                e.printStackTrace();
            }

            return movieDataJson;
        }

        @Override
        protected void onPostExecute(List<Movie> movies) {
            if (movies != null)
                _loadingMoviesObservable.postValue(new Resource<>(SUCCESS, movies, null));
            else _loadingMoviesObservable.postValue(new Resource<>(ERROR, null, null));
        }
    }
}
