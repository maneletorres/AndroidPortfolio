package com.example.androidportfolio;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.androidportfolio.utilities.NetworkUtils;

import java.io.IOException;
import java.net.URL;

public class GitHubSearchFragment extends Fragment {
    private EditText mSearchBoxEditText;
    private TextView mUrlDisplayTextView;
    private TextView mSearchResultsTextView;
    private TextView mErrorMessageTextView;
    private ProgressBar mLoadingIndicator;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_github_search, container, false);
        setHasOptionsMenu(true);

        mSearchBoxEditText = view.findViewById(R.id.et_search_box);
        mUrlDisplayTextView = view.findViewById(R.id.tv_url_display);
        mSearchResultsTextView = view.findViewById(R.id.tv_github_search_results_json);
        mErrorMessageTextView = view.findViewById(R.id.tv_error_message_display);
        mLoadingIndicator = view.findViewById(R.id.pb_loading_indicator);

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
        URL githubSearchUrl = NetworkUtils.buildUrl(githubQuery);
        mUrlDisplayTextView.setText(githubSearchUrl.toString());
        new GithubQuerySearch().execute(githubSearchUrl);
    }

    private void showJsonDataView() {
        mErrorMessageTextView.setVisibility(View.INVISIBLE);
        mSearchResultsTextView.setVisibility(View.VISIBLE);
    }

    private void showErrorMessage() {
        mSearchResultsTextView.setVisibility(View.INVISIBLE);
        mErrorMessageTextView.setVisibility(View.VISIBLE);
    }

    private class GithubQuerySearch extends AsyncTask<URL, Void, String> {

        @Override
        protected void onPreExecute() {
            mLoadingIndicator.setVisibility(View.VISIBLE);
        }

        @Override
        protected String doInBackground(URL... urls) {
            URL searchUrl = urls[0];
            String githubSearchResults = null;
            try {
                githubSearchResults = NetworkUtils.getResponseFromHttpUrl(searchUrl);
            } catch (IOException ex) {
                ex.printStackTrace();
            }

            return githubSearchResults;
        }

        @Override
        protected void onPostExecute(String s) {
            mLoadingIndicator.setVisibility(View.INVISIBLE);

            if (s != null && !s.equals("")) {
                showJsonDataView();
                mSearchResultsTextView.setText(s);
            } else {
                showErrorMessage();
            }
        }
    }
}