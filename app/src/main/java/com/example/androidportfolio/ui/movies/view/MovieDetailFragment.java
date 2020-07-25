package com.example.androidportfolio.ui.movies.view;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.androidportfolio.R;
import com.example.androidportfolio.data.model.Movie;
import com.example.androidportfolio.data.model.Review;
import com.example.androidportfolio.data.model.Trailer;
import com.example.androidportfolio.databinding.FragmentMovieDetailBinding;
import com.example.androidportfolio.ui.MainActivity;
import com.example.androidportfolio.ui.movies.adapter.ReviewAdapter;
import com.example.androidportfolio.ui.movies.adapter.TrailerAdapter;
import com.example.androidportfolio.ui.movies.viewmodel.MovieDetailViewModel;
import com.example.androidportfolio.utilities.NetworkUtils;
import com.squareup.picasso.Picasso;

import java.net.URL;
import java.util.List;

import static android.view.View.GONE;
import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;

public class MovieDetailFragment extends Fragment implements TrailerAdapter.TrailerAdapterOnClickHandler, ReviewAdapter.ReviewAdapterOnClickHandler {

    private MovieDetailViewModel mViewModel;
    private FragmentMovieDetailBinding mBinding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // ViewModel:
        mViewModel = new ViewModelProvider(this).get(MovieDetailViewModel.class);

        // DataBinding:
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_movie_detail, container, false);
        mBinding.setViewModel(mViewModel);

        Bundle bundle = getArguments();
        if (bundle != null) {
            Movie movie = (Movie) bundle.getParcelable(getString(R.string.clicked_movie_key));
            if (movie != null) {
                setupToolbar(movie.getTitle());
                setupUI(movie);
                setupObservers();

                mViewModel.loadMovieData(movie.getId());
            }
        } else showLoadMovieErrorMessage();

        return mBinding.getRoot();
    }

    @Override
    public void onTrailerClick(Trailer trailer) {
        URL youtubeUrl = NetworkUtils.buildYouTubeUrl(trailer.getKey());
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(youtubeUrl.toString()));

        if (intent.resolveActivity(requireActivity().getPackageManager()) != null) startActivity(intent);
        else Toast.makeText(requireContext(), getString(R.string.load_trailer_error_message), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onReviewClick(Review review) {
        // TODO: Should the review press do something?
    }

    private void setupToolbar(String movieTitle) {
        MainActivity mainActivity = (MainActivity) getActivity();
        if (mainActivity != null) mainActivity.setToolbarTitle(movieTitle);
    }

    private void setupUI(Movie movie) {
        Picasso.get()
                .load(NetworkUtils.IMAGE_TMDB_URL + movie.getPosterPath())
                .into(mBinding.coverImageView);

        mBinding.releaseDateTextView.setText(movie.getReleaseDate());
        // TODO: mBinding.durationTextView.setText("");
        mBinding.voteAverageTextView.setText(movie.getVoteAverage() + "/10");
        mBinding.overviewTextView.setText(movie.getOverview());
    }

    private void setupObservers() {
        mViewModel.loadingTrailersObservable().observe(getViewLifecycleOwner(), loadingStatus -> {
            switch (loadingStatus.getStatus()) {
                case SUCCESS:
                    showTrailers(loadingStatus.getData());
                    break;
                case LOADING:
                    // TODO: hideTrailersRecyclerView();
                    break;
                case ERROR:
                    hideTrailers();
                    break;
            }
        });

        mViewModel.loadingReviewsObservable().observe(getViewLifecycleOwner(), loadingStatus -> {
            switch (loadingStatus.getStatus()) {
                case SUCCESS:
                    showReviews(loadingStatus.getData());
                    break;
                case LOADING:
                    // TODO: hideReviewsRecyclerView();
                    break;
                case ERROR:
                    hideReviews();
                    break;
            }
        });
    }

    private void showTrailers(List<Trailer> trailers) {
        if (trailers != null && trailers.size() > 0) {
            // Trailer recyclerView configuration:
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
            mBinding.trailerRecyclerView.setLayoutManager(linearLayoutManager);
            mBinding.trailerRecyclerView.setHasFixedSize(true);
            DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(mBinding.trailerRecyclerView.getContext(), linearLayoutManager.getOrientation());
            mBinding.trailerRecyclerView.addItemDecoration(dividerItemDecoration);

            // TrailerAdapter configuration:
            TrailerAdapter mTrailerAdapter = new TrailerAdapter(this);
            mBinding.trailerRecyclerView.setAdapter(mTrailerAdapter);

            mBinding.trailersLinearLayout.setVisibility(VISIBLE);
            mTrailerAdapter.setTrailersData(trailers);
        }
    }

    private void hideTrailers() {
        mBinding.trailersLinearLayout.setVisibility(GONE);
    }

    private void showReviews(List<Review> reviews) {
        if (reviews != null && reviews.size() > 0) {
            // Review recyclerView configuration:
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
            mBinding.reviewRecyclerView.setLayoutManager(linearLayoutManager);
            mBinding.reviewRecyclerView.setHasFixedSize(true);
            DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(mBinding.reviewRecyclerView.getContext(), linearLayoutManager.getOrientation());
            mBinding.reviewRecyclerView.addItemDecoration(dividerItemDecoration);

            // ReviewAdapter configuration:
            ReviewAdapter mReviewAdapter = new ReviewAdapter(this);
            mBinding.reviewRecyclerView.setAdapter(mReviewAdapter);

            mBinding.reviewsLinearLayout.setVisibility(VISIBLE);
            mReviewAdapter.setReviewsData(reviews);
        }
    }

    private void hideReviews() {
        mBinding.reviewsLinearLayout.setVisibility(GONE);
    }

    private void showLoadMovieErrorMessage() {
        // TODO - Center error message TextView.
        mBinding.mainConstraintLayout.setVisibility(INVISIBLE);
        mBinding.movieErrorTextView.setVisibility(VISIBLE);
    }
}
