package com.example.androidportfolio.ui.movies.viewmodel;

import androidx.annotation.NonNull;
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

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.androidportfolio.utils.Status.ERROR;
import static com.example.androidportfolio.utils.Status.LOADING;
import static com.example.androidportfolio.utils.Status.SUCCESS;

public class MovieDetailViewModel extends ViewModel {

    // Observables:
    private final MutableLiveData<Resource<List<Trailer>>> _loadingTrailersObservable = new MutableLiveData<>();
    private final MutableLiveData<Resource<List<Review>>> _loadingReviewsObservable = new MutableLiveData<>();

    // Variables:
    private final MovieRepository movieRepository;

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

        // TODO:
        movieRepository.getTrailers(movieId)
                .enqueue(new Callback<Trailers>() {
                    @Override
                    public void onResponse(@NonNull Call<Trailers> call, @NonNull Response<Trailers> response) {
                        Trailers trailersResponse = response.body();
                        if (trailersResponse != null) {
                            List<Trailer> trailers = trailersResponse.getTrailers();
                            if (trailers != null)
                                _loadingTrailersObservable.postValue(new Resource<>(SUCCESS, trailers, null));
                            else
                                _loadingTrailersObservable.postValue(new Resource<>(ERROR, null, null));
                        } else
                            _loadingTrailersObservable.postValue(new Resource<>(ERROR, null, null));
                    }

                    @Override
                    public void onFailure(@NonNull Call<Trailers> call, @NonNull Throwable t) {
                        _loadingTrailersObservable.postValue(new Resource<>(ERROR, null, null));
                    }
                });
    }

    public void fetchReviews(int movieId) {
        _loadingReviewsObservable.postValue(new Resource<>(LOADING, null, null));

        movieRepository.getReviews(movieId)
                .enqueue(new Callback<Reviews>() {
                    @Override
                    public void onResponse(@NonNull Call<Reviews> call, @NonNull Response<Reviews> response) {
                        Reviews reviewsResponse = response.body();
                        if (reviewsResponse != null) {
                            List<Review> reviews = reviewsResponse.getReviews();
                            if (reviews != null)
                                _loadingReviewsObservable.postValue(new Resource<>(SUCCESS, reviews, null));
                            else
                                _loadingReviewsObservable.postValue(new Resource<>(ERROR, null, null));
                        } else
                            _loadingReviewsObservable.postValue(new Resource<>(ERROR, null, null));
                    }

                    @Override
                    public void onFailure(@NonNull Call<Reviews> call, @NonNull Throwable t) {
                        _loadingReviewsObservable.postValue(new Resource<>(ERROR, null, null));
                    }
                });
    }
}
