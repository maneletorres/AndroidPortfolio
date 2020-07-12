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

import com.example.androidportfolio.R;
import com.example.androidportfolio.databinding.FragmentGithubSearchBinding;
import com.example.androidportfolio.ui.githubsearch.viewmodel.GitHubSearchViewModel;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

public class GitHubSearchFragment extends Fragment {

    // Variables:
    private GitHubSearchViewModel mViewModel;
    private FragmentGithubSearchBinding mBinding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // ViewModel:
        mViewModel = new ViewModelProvider(requireActivity()).get(GitHubSearchViewModel.class);

        // DataBinding:
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_github_search, container, false);
        mBinding.setViewModel(mViewModel);

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

        mBinding.pbLoadingIndicator.setVisibility(GONE);
        mBinding.tvErrorMessageDisplay.setVisibility(GONE);
        mBinding.tvUrlDisplay.setVisibility(VISIBLE);
        mBinding.tvGithubSearchResultsJson.setVisibility(VISIBLE);

        mBinding.tvUrlDisplay.setText(gitHubQuery);
        mBinding.tvGithubSearchResultsJson.setText(gitHubSearchResult);
    }

    private void showProgressBar() {
        mBinding.tvUrlDisplay.setVisibility(GONE);
        mBinding.tvGithubSearchResultsJson.setVisibility(GONE);
        mBinding.tvErrorMessageDisplay.setVisibility(GONE);
        mBinding.pbLoadingIndicator.setVisibility(VISIBLE);
    }

    private void showLoadGithubSearchErrorMessage() {
        mBinding.pbLoadingIndicator.setVisibility(GONE);
        mBinding.tvUrlDisplay.setVisibility(GONE);
        mBinding.tvGithubSearchResultsJson.setVisibility(View.GONE);
        mBinding.tvErrorMessageDisplay.setVisibility(VISIBLE);
    }
}