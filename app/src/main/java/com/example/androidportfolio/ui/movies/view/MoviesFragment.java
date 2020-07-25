package com.example.androidportfolio.ui.movies.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.androidportfolio.R;
import com.example.androidportfolio.data.model.Movie;
import com.example.androidportfolio.databinding.FragmentMoviesBinding;
import com.example.androidportfolio.ui.movies.adapter.MovieAdapter;
import com.example.androidportfolio.ui.movies.viewmodel.MoviesViewModel;
import com.example.androidportfolio.utilities.NetworkUtils;

import java.util.List;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

public class MoviesFragment extends Fragment implements MovieAdapter.MovieAdapterOnClickHandler {

    private static final int SPAN_COUNT = 2;
    private MoviesViewModel mViewModel;
    private FragmentMoviesBinding mBinding;
    private MovieAdapter mMovieAdapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // ViewModel:
        mViewModel = new ViewModelProvider(requireActivity()).get(MoviesViewModel.class);

        // DataBinding:
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_movies, container, false);
        mBinding.setViewModel(mViewModel);

        setupToolbar();
        setupUI();
        setupObservers();

        mViewModel.start();

        return mBinding.getRoot();
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.movies_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_top_rated:
                mViewModel.loadMovies(NetworkUtils.TOP_RATED_PARAM);
                return true;
            case R.id.action_most_popular:
                mViewModel.loadMovies(NetworkUtils.POPULAR_PARAM);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onMovieClick(Movie movie) {
        Bundle bundle = new Bundle();
        bundle.putParcelable(getString(R.string.clicked_movie_key), movie);

        Fragment fragment = new MovieDetailFragment();
        fragment.setArguments(bundle);
        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.main_linear_layout, fragment).addToBackStack(getString(R.string.movies_fragment_title)).commit();
    }

    private void setupToolbar() {
        setHasOptionsMenu(true);
    }

    private void setupUI() {
        // Movies recyclerView configuration:
        LinearLayoutManager linearLayoutManager = new GridLayoutManager(getContext(), SPAN_COUNT);
        mBinding.moviesRecyclerView.setLayoutManager(linearLayoutManager);
        mBinding.moviesRecyclerView.setHasFixedSize(true);

        // MovieAdapter configuration:
        mMovieAdapter = new MovieAdapter(this);
        mBinding.moviesRecyclerView.setAdapter(mMovieAdapter);
    }

    private void setupObservers() {
        mViewModel.loadingMoviesObservable().observe(getViewLifecycleOwner(), loadingStatus -> {
            switch (loadingStatus.getStatus()) {
                case SUCCESS:
                    showToyData(loadingStatus.getData());
                    break;
                case LOADING:
                    showProgressBar();
                    break;
                case ERROR:
                    showLoadToyErrorMessage();
                    break;
            }
        });
    }

    private void showToyData(List<Movie> movieData) {
        mBinding.moviesProgressBar.setVisibility(GONE);
        mBinding.moviesErrorTextView.setVisibility(GONE);
        mBinding.moviesRecyclerView.setVisibility(VISIBLE);

        mMovieAdapter.setMoviesData(movieData);
    }

    private void showProgressBar() {
        mBinding.moviesRecyclerView.setVisibility(GONE);
        mBinding.moviesErrorTextView.setVisibility(GONE);
        mBinding.moviesProgressBar.setVisibility(VISIBLE);
    }

    private void showLoadToyErrorMessage() {
        mBinding.moviesProgressBar.setVisibility(GONE);
        mBinding.moviesRecyclerView.setVisibility(GONE);
        mBinding.moviesErrorTextView.setVisibility(VISIBLE);
    }
}
