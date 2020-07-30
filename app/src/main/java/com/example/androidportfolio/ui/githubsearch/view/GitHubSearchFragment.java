package com.example.androidportfolio.ui.githubsearch.view;

import android.os.Bundle;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import com.example.androidportfolio.R;
import com.example.androidportfolio.base.BaseApplication;
import com.example.androidportfolio.databinding.FragmentGithubSearchBinding;
import com.example.androidportfolio.di.ViewModelFactory;
import com.example.androidportfolio.ui.githubsearch.viewmodel.GitHubSearchViewModel;
import com.example.androidportfolio.ui.movies.viewmodel.MovieDetailViewModel;

import javax.inject.Inject;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

public class GitHubSearchFragment extends Fragment {

    // Variables:
    @Inject
    ViewModelFactory viewModelFactory;
    private GitHubSearchViewModel mViewModel;
    private FragmentGithubSearchBinding mBinding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Dependency injection (DI) with Dagger:
        ((BaseApplication) requireActivity().getApplication()).getAppComponent().inject(this);

        // ViewModel:
        mViewModel = ViewModelProviders.of(this, viewModelFactory).get(GitHubSearchViewModel.class);

        // DataBinding:
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_github_search, container, false);

        setupToolbar();
        setupObservers();

        return mBinding.getRoot();
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.github_search_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.action_search) {
            mViewModel.loadGitHubSearch(mBinding.etSearchBox.getText().toString());
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setupToolbar() {
        setHasOptionsMenu(true);
    }

    private void setupObservers() {
        mViewModel.loadingGitHubSearchResultObservable().observe(getViewLifecycleOwner(), loadingStatus -> {
            switch (loadingStatus.getStatus()) {
                case SUCCESS:
                    showGitHubSearchResult(loadingStatus.getData());
                    break;
                case LOADING:
                    showProgressBar();
                    break;
                case ERROR:
                    showLoadGithubSearchErrorMessage();
                    break;
                default:
                    // TODO
            }
        });
    }

    private void showGitHubSearchResult(Pair<String, String> responsePair) {
        String gitHubQuery = responsePair.first;
        String gitHubSearchResult = responsePair.second;

        mBinding.githubSearchProgressBar.setVisibility(GONE);
        mBinding.tvGithubSearchErrorMessage.setVisibility(GONE);
        mBinding.tvUrlDisplay.setVisibility(VISIBLE);
        mBinding.tvGithubSearchResultsJson.setVisibility(VISIBLE);

        mBinding.tvUrlDisplay.setText(gitHubQuery);
        mBinding.tvGithubSearchResultsJson.setText(gitHubSearchResult);
    }

    private void showProgressBar() {
        mBinding.tvUrlDisplay.setVisibility(GONE);
        mBinding.tvGithubSearchResultsJson.setVisibility(GONE);
        mBinding.tvGithubSearchErrorMessage.setVisibility(GONE);
        mBinding.githubSearchProgressBar.setVisibility(VISIBLE);
    }

    private void showLoadGithubSearchErrorMessage() {
        mBinding.githubSearchProgressBar.setVisibility(GONE);
        mBinding.tvUrlDisplay.setVisibility(GONE);
        mBinding.tvGithubSearchResultsJson.setVisibility(View.GONE);
        mBinding.tvGithubSearchErrorMessage.setVisibility(VISIBLE);
    }
}