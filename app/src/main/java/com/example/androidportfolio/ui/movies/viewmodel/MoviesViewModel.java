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
    // Observables:
    private final MutableLiveData<Resource<List<Movie>>> _loadingMoviesObservable = new MutableLiveData<>();

    public LiveData<Resource<List<Movie>>> loadingMoviesObservable() {
        return _loadingMoviesObservable;
    }

    public void start() {
        new FetchMoviesTask().execute();
    }

    public class FetchMoviesTask extends AsyncTask<Void, Void, List<Movie>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            _loadingMoviesObservable.postValue(new Resource<>(LOADING, null, null));
        }

        @Override
        protected List<Movie> doInBackground(Void... params) {
            //if (params == null) return null;

            URL weatherRequestUrl = NetworkUtils.buildTMDBUrl();

            List<Movie> simpleJsonWeatherData = new ArrayList<>();
            try {
                String jsonWeatherResponse = NetworkUtils.getResponseFromHttpUrl(weatherRequestUrl);

                simpleJsonWeatherData = MovieParser.getMoviesFromJson(jsonWeatherResponse);
            } catch (Exception e) {
                e.printStackTrace();
            }

            return simpleJsonWeatherData;
        }

        @Override
        protected void onPostExecute(List<Movie> movies) {
            if (movies != null)
                _loadingMoviesObservable.postValue(new Resource<>(SUCCESS, movies, null));
            else _loadingMoviesObservable.postValue(new Resource<>(ERROR, null, null));
        }
    }
}
