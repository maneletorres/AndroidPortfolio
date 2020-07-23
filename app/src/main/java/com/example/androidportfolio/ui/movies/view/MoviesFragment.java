package com.example.androidportfolio.ui.movies.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.androidportfolio.R;
import com.example.androidportfolio.data.model.Movie;
import com.example.androidportfolio.databinding.FragmentMoviesBinding;
import com.example.androidportfolio.ui.movies.adapter.MovieAdapter;
import com.example.androidportfolio.ui.movies.viewmodel.MoviesViewModel;

import java.util.List;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

public class MoviesFragment extends Fragment implements MovieAdapter.MovieAdapterOnClickHandler {

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
    public void onClick(Movie movie) {
        Bundle bundle = new Bundle();
        bundle.putParcelable("clicked_movie", movie);

        Fragment fragment = new MovieDetailFragment();
        fragment.setArguments(bundle);
        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.main_linear_layout, fragment).addToBackStack(getString(R.string.movies_fragment_title)).commit();
    }

    private void setupToolbar() {

    }

    private void setupUI() {
        // RecyclerView configuration:
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        mBinding.moviesRecyclerView.setLayoutManager(linearLayoutManager);
        mBinding.moviesRecyclerView.setHasFixedSize(true);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(mBinding.moviesRecyclerView.getContext(), linearLayoutManager.getOrientation());
        mBinding.moviesRecyclerView.addItemDecoration(dividerItemDecoration);

        // ToyAdapter configuration:
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
                default:
                    // TODO
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
