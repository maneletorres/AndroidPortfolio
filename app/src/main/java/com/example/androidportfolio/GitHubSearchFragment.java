package com.example.androidportfolio;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.androidportfolio.utilities.NetworkUtils;

import java.io.IOException;
import java.net.URL;

public class GitHubSearchFragment extends Fragment {
    EditText mSearchBoxEditText;
    TextView mUrlDisplayTextView;
    TextView mSearchResultsTextView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_github_search, container, false);
        setHasOptionsMenu(true);

        mSearchBoxEditText = view.findViewById(R.id.et_search_box);
        mUrlDisplayTextView = view.findViewById(R.id.tv_url_display);
        mSearchResultsTextView = view.findViewById(R.id.tv_github_search_results_json);

        return view;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.action_search) {
            makeGithubSearchQuery();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void makeGithubSearchQuery() {
        String githubQuery = mSearchBoxEditText.getText().toString();
        URL githubSearuchUrl = NetworkUtils.buildUrl(githubQuery);
        mUrlDisplayTextView.setText(githubSearuchUrl.toString());
        String githubSearchResults;
        try {
            githubSearchResults = NetworkUtils.getResponseFromHttpUrl(githubSearuchUrl);
            mSearchResultsTextView.setText(githubSearchResults);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}