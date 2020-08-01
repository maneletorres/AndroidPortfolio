package com.example.androidportfolio.ui.movies.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.androidportfolio.data.model.Movie;
import com.example.androidportfolio.data.model.Movies;
import com.example.androidportfolio.data.rest.repository.MovieRepository;
import com.example.androidportfolio.utils.NetworkUtils;
import com.example.androidportfolio.utils.Resource;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

import static com.example.androidportfolio.utils.Status.ERROR;
import static com.example.androidportfolio.utils.Status.LOADING;
import static com.example.androidportfolio.utils.Status.SUCCESS;

public class MoviesViewModel extends ViewModel {
    private final MutableLiveData<Resource<List<Movie>>> _loadingMoviesObservable = new MutableLiveData<>();
    private final MovieRepository movieRepository;
    private CompositeDisposable disposable = new CompositeDisposable();
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

        disposable.add(movieRepository.getMovies(searchCriteria)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<Movies>() {
                    @Override
                    public void onSuccess(Movies movies) {
                        List<Movie> movieList = movies.getMovies();
                        if (movieList != null)
                            _loadingMoviesObservable.postValue(new Resource<>(SUCCESS, movieList, null));
                        else
                            _loadingMoviesObservable.postValue(new Resource<>(ERROR, null, null));
                    }

                    @Override
                    public void onError(Throwable e) {
                        _loadingMoviesObservable.postValue(new Resource<>(ERROR, null, null));
                    }
                }));
    }
}
