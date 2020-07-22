package com.example.androidportfolio.ui.movies.view;

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

import com.example.androidportfolio.R;
import com.example.androidportfolio.data.model.Movie;
import com.example.androidportfolio.databinding.FragmentMovieDetailBinding;
import com.example.androidportfolio.ui.MainActivity;
import com.example.androidportfolio.ui.movies.viewmodel.MovieDetailViewModel;

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
            }
        } else closeOnError();

        return mBinding.getRoot();
    }

    private void setupToolbar(String movieTitle) {
        MainActivity mainActivity = (MainActivity) getActivity();
        if (mainActivity != null) mainActivity.setToolbarTitle(movieTitle);
    }

    private void setupUI(Movie movie) {
        mBinding.releaseDateTv.setText(movie.getReleaseDate());
        mBinding.voteAverageTv.setText(String.valueOf(movie.getVoteAverage() + "/10"));
        mBinding.overviewTv.setText(movie.getOverview());
    }

    private void setupObservers() {
        // TODO
    }

    private void closeOnError() {
        // Toast.makeText(requireContext(), getString(R.string.movie_detail_error_message), Toast.LENGTH_SHORT).show();
    }
}
