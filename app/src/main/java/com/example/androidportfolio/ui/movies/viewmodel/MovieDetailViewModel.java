package com.example.androidportfolio.ui.movies.viewmodel;

import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.androidportfolio.data.model.Review;
import com.example.androidportfolio.data.model.Trailer;
import com.example.androidportfolio.utilities.MovieParser;
import com.example.androidportfolio.utilities.NetworkUtils;
import com.example.androidportfolio.utils.Resource;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static com.example.androidportfolio.utils.Status.ERROR;
import static com.example.androidportfolio.utils.Status.LOADING;
import static com.example.androidportfolio.utils.Status.SUCCESS;

public class MovieDetailViewModel extends ViewModel {
    private final MutableLiveData<Resource<List<Trailer>>> _loadingTrailersObservable = new MutableLiveData<>();
    private final MutableLiveData<Resource<List<Review>>> _loadingReviewsObservable = new MutableLiveData<>();

    public LiveData<Resource<List<Trailer>>> loadingTrailersObservable() {
        return _loadingTrailersObservable;
    }

    public LiveData<Resource<List<Review>>> loadingReviewsObservable() {
        return _loadingReviewsObservable;
    }

    public void loadMovieData(int movieId) {
        // TODO: Should I somehow bind API calls?
        loadTrailers(movieId);
        loadReviews(movieId);
    }

    public void loadTrailers(int movieId) {
        new FetchTrailersTask().execute(movieId);
    }

    public void loadReviews(int movieId) {
        new FetchReviewsTask().execute(movieId);
    }

    public class FetchTrailersTask extends AsyncTask<Integer, Void, List<Trailer>> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            _loadingTrailersObservable.postValue(new Resource<>(LOADING, null, null));
        }

        @Override
        protected List<Trailer> doInBackground(Integer... ids) {
            if (ids == null) return null;

            URL trailersRequestUrl = NetworkUtils.buildTMDBTrailersUrl(ids[0]);

            List<Trailer> trailerDataJson = new ArrayList<>();
            try {
                String jsonWeatherResponse = NetworkUtils.getResponseFromHttpUrl(trailersRequestUrl);
                trailerDataJson = MovieParser.getMovieTrailersFromJson(jsonWeatherResponse);
            } catch (Exception e) {
                e.printStackTrace();
            }

            return trailerDataJson;
        }

        @Override
        protected void onPostExecute(List<Trailer> trailers) {
            if (trailers != null)
                _loadingTrailersObservable.postValue(new Resource<>(SUCCESS, trailers, null));
            else _loadingTrailersObservable.postValue(new Resource<>(ERROR, null, null));
        }
    }

    public class FetchReviewsTask extends AsyncTask<Integer, Void, List<Review>> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            _loadingReviewsObservable.postValue(new Resource<>(LOADING, null, null));
        }

        @Override
        protected List<Review> doInBackground(Integer... ids) {
            if (ids == null) return null;

            URL reviewsRequestUrl = NetworkUtils.buildTMDBReviewsUrl(ids[0]);

            List<Review> reviewDataJson = new ArrayList<>();
            try {
                String jsonWeatherResponse = NetworkUtils.getResponseFromHttpUrl(reviewsRequestUrl);
                reviewDataJson = MovieParser.getMovieReviewsFromJson(jsonWeatherResponse);
            } catch (Exception e) {
                e.printStackTrace();
            }

            return reviewDataJson;
        }

        @Override
        protected void onPostExecute(List<Review> reviews) {
            if (reviews != null)
                _loadingReviewsObservable.postValue(new Resource<>(SUCCESS, reviews, null));
            else _loadingReviewsObservable.postValue(new Resource<>(ERROR, null, null));
        }
    }
}
