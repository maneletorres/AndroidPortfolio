package com.example.androidportfolio.ui.movies.viewmodel;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.androidportfolio.data.model.Movie;
import com.example.androidportfolio.data.model.Movies;
import com.example.androidportfolio.data.rest.repository.MovieRepository;
import com.example.androidportfolio.utilities.NetworkUtils;
import com.example.androidportfolio.utils.Resource;

import java.util.List;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.androidportfolio.utils.Status.ERROR;
import static com.example.androidportfolio.utils.Status.LOADING;
import static com.example.androidportfolio.utils.Status.SUCCESS;

public class MoviesViewModel extends ViewModel {

    // Observable:
    private final MutableLiveData<Resource<List<Movie>>> _loadingMoviesObservable = new MutableLiveData<>();

    // Variables:
    private final MovieRepository movieRepository;
    private String searchCriteria = NetworkUtils.POPULAR_PARAM;

    @Inject
    public MoviesViewModel(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    public LiveData<Resource<List<Movie>>> loadingMoviesObservable() {
        return _loadingMoviesObservable;
    }

    public void start() {
        fetchMovies();
    }

    public void loadMovies(String searchParameter) {
        searchCriteria = searchParameter;
        fetchMovies();
    }

    public void fetchMovies() {
        _loadingMoviesObservable.postValue(new Resource<>(LOADING, null, null));

        // TODO:
        movieRepository.getMovies(searchCriteria)
                .enqueue(new Callback<Movies>() {
                    @Override
                    public void onResponse(@NonNull Call<Movies> call, @NonNull Response<Movies> response) {
                        Movies moviesResponse = response.body();
                        if (moviesResponse != null) {
                            List<Movie> movies = moviesResponse.getMovies();
                            if (movies != null)
                                _loadingMoviesObservable.postValue(new Resource<>(SUCCESS, movies, null));
                            else
                                _loadingMoviesObservable.postValue(new Resource<>(ERROR, null, null));
                        } else
                            _loadingMoviesObservable.postValue(new Resource<>(ERROR, null, null));
                    }

                    @Override
                    public void onFailure(@NonNull Call<Movies> call, @NonNull Throwable t) {
                        _loadingMoviesObservable.postValue(new Resource<>(ERROR, null, null));
                    }
                });
    }
}
