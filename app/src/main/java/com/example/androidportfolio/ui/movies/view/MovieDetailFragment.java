package com.example.androidportfolio.ui.movies.view;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.androidportfolio.R;
import com.example.androidportfolio.data.model.Movie;
import com.example.androidportfolio.data.model.Trailer;
import com.example.androidportfolio.databinding.FragmentMovieDetailBinding;
import com.example.androidportfolio.ui.MainActivity;
import com.example.androidportfolio.ui.movies.viewmodel.MovieDetailViewModel;
import com.example.androidportfolio.utilities.NetworkUtils;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MovieDetailFragment extends Fragment {

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
            Movie movie = (Movie) bundle.getParcelable("clicked_movie");
            if (movie != null) {
                setupToolbar(movie.getTitle());
                setupUI(movie);
                setupObservers();

                mViewModel.loadMovieData(movie.getId());
            }
        } else closeOnError();

        return mBinding.getRoot();
    }

    private void setupToolbar(String movieTitle) {
        MainActivity mainActivity = (MainActivity) getActivity();
        if (mainActivity != null) mainActivity.setToolbarTitle(movieTitle);
    }

    private void setupUI(Movie movie) {
        Picasso.get()
                .load(NetworkUtils.IMAGE_TMDB_URL + movie.getPosterPath())
                .into(mBinding.coverIv);

        mBinding.releaseDateTv.setText(movie.getReleaseDate());
        mBinding.voteAverageTv.setText(String.valueOf(movie.getVoteAverage() + "/10"));
        mBinding.overviewTv.setText(movie.getOverview());
    }

    private void setupObservers() {
        mViewModel.loadingTrailersObservable().observe(getViewLifecycleOwner(), loadingStatus -> {
            switch (loadingStatus.getStatus()) {
                case SUCCESS:
                    showTrailers(loadingStatus.getData());
                    // Log.v("TRAILERS", loadingStatus.getData().toString());
                    break;
                case LOADING:
                    // showProgressBar();
                    break;
                case ERROR:
                    // showLoadToyErrorMessage();
                    break;
                default:
                    // TODO
            }
        });

        mViewModel.loadingReviewsObservable().observe(getViewLifecycleOwner(), loadingStatus -> {
            switch (loadingStatus.getStatus()) {
                case SUCCESS:
                    Log.v("REVIEWS", loadingStatus.getData().toString());
                    break;
                case LOADING:
                    // showProgressBar();
                    break;
                case ERROR:
                    // showLoadToyErrorMessage();
                    break;
                default:
                    // TODO
            }
        });
    }

    private void showTrailers(List<Trailer> trailers) {
        ConstraintSet set = new ConstraintSet();

        int previousId = -1;
        for (int i = 0; i < trailers.size(); i++) {
            TextView childView = new TextView(requireContext());
            childView.setText("Trailer " + i);

            int currentId = View.generateViewId();

            childView.setId(currentId);
            mBinding.trailersLl.addView(childView, i);

            set.clone(mBinding.trailersLl);
            set.connect(childView.getId(), ConstraintSet.START, mBinding.trailersLl.getId(), ConstraintSet.START, 0);
            set.connect(childView.getId(), ConstraintSet.END, mBinding.trailersLl.getId(), ConstraintSet.END, 0);

            if (i == 0) {
                set.connect(childView.getId(), ConstraintSet.TOP, mBinding.trailersLl.getId(), ConstraintSet.TOP, 0);
                // set.connect(childView.getId(), ConstraintSet.BOTTOM, mBinding.trailersLl.getId(), ConstraintSet.BOTTOM, 0);
            } else {
                set.connect(childView.getId(), ConstraintSet.TOP, previousId, ConstraintSet.BOTTOM, 0);
                //set.connect(childView.getId(), ConstraintSet.BOTTOM, mBinding.trailersLl.getId(), ConstraintSet.BOTTOM, 0);
            }

            previousId = currentId;

            set.applyTo(mBinding.trailersLl);
        }
    }

    private void closeOnError() {
        // Toast.makeText(requireContext(), getString(R.string.movie_detail_error_message), Toast.LENGTH_SHORT).show();
    }
}
