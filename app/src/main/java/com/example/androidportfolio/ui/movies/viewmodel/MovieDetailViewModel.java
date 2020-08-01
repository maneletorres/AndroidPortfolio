package com.example.androidportfolio.ui.movies.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.androidportfolio.data.model.Review;
import com.example.androidportfolio.data.model.Reviews;
import com.example.androidportfolio.data.model.Trailer;
import com.example.androidportfolio.data.model.Trailers;
import com.example.androidportfolio.data.rest.repository.MovieRepository;
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

public class MovieDetailViewModel extends ViewModel {
    private final MutableLiveData<Resource<List<Trailer>>> _loadingTrailersObservable = new MutableLiveData<>();
    private final MutableLiveData<Resource<List<Review>>> _loadingReviewsObservable = new MutableLiveData<>();
    private final MovieRepository movieRepository;
    private CompositeDisposable disposable = new CompositeDisposable();

    @Inject
    public MovieDetailViewModel(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    public LiveData<Resource<List<Trailer>>> loadingTrailersObservable() {
        return _loadingTrailersObservable;
    }

    public LiveData<Resource<List<Review>>> loadingReviewsObservable() {
        return _loadingReviewsObservable;
    }

    public void loadMovieData(int movieId) {
        // TODO: Should I chain API calls?
        fetchTrailers(movieId);
        fetchReviews(movieId);
    }

    public void fetchTrailers(int movieId) {
        _loadingTrailersObservable.postValue(new Resource<>(LOADING, null, null));

        disposable.add(movieRepository.getTrailers(movieId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<Trailers>() {
                    @Override
                    public void onSuccess(Trailers trailers) {
                        List<Trailer> trailerList = trailers.getTrailers();
                        if (trailerList != null)
                            _loadingTrailersObservable.postValue(new Resource<>(SUCCESS, trailerList, null));
                        else
                            _loadingTrailersObservable.postValue(new Resource<>(ERROR, null, null));
                    }

                    @Override
                    public void onError(Throwable e) {
                        _loadingTrailersObservable.postValue(new Resource<>(ERROR, null, null));
                    }
                }));
    }

    public void fetchReviews(int movieId) {
        _loadingReviewsObservable.postValue(new Resource<>(LOADING, null, null));

        disposable.add(movieRepository.getReviews(movieId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<Reviews>() {
                    @Override
                    public void onSuccess(Reviews trailers) {
                        List<Review> reviewList = trailers.getReviews();
                        if (reviewList != null)
                            _loadingReviewsObservable.postValue(new Resource<>(SUCCESS, reviewList, null));
                        else
                            _loadingReviewsObservable.postValue(new Resource<>(ERROR, null, null));
                    }

                    @Override
                    public void onError(Throwable e) {
                        _loadingReviewsObservable.postValue(new Resource<>(ERROR, null, null));
                    }
                }));
    }
}
