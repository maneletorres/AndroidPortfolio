package com.example.androidportfolio;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidportfolio.adapters.ForecastAdapter;
import com.example.androidportfolio.data.SunshinePreferences;
import com.example.androidportfolio.utilities.NetworkUtils;
import com.example.androidportfolio.utilities.OpenWeatherJsonUtils;

import java.net.URL;

public class SunshineFragment extends Fragment implements ForecastAdapter.ForecastAdapterOnClickHandler {

    // Variables:
    private RecyclerView mWeatherRecyclerView;
    private ForecastAdapter mForecastAdapter;
    private TextView mErrorMessageDisplay;
    private ProgressBar mLoadingIndicator;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sunshine, container, false);

        // Toolbar configuration:
        setHasOptionsMenu(true);

        // RecyclerView configuration:
        mWeatherRecyclerView = view.findViewById(R.id.weatherRecyclerView);
        LinearLayoutManager linearLayout = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        mWeatherRecyclerView.setLayoutManager(linearLayout);
        mWeatherRecyclerView.setHasFixedSize(true);

        // Adapter configuration:
        mForecastAdapter = new ForecastAdapter(this);
        mWeatherRecyclerView.setAdapter(mForecastAdapter);

        // Error message TextView configuration:
        mErrorMessageDisplay = view.findViewById(R.id.tv_error_message_display);

        // ProgressBar configuration:
        mLoadingIndicator = view.findViewById(R.id.pb_loading_indicator);

        loadWeatherData();

        return view;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.forecast, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_refresh) {
            mForecastAdapter.setWeatherData(null);
            loadWeatherData();
            return true;
        } else if (id == R.id.action_map) {
            openLocationMap();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void loadWeatherData() {
        String preferredWeatherLocation = SunshinePreferences.getPreferredWeatherLocation(getContext());
        new FetchWeatherTask().execute(preferredWeatherLocation);
    }

    public void showWeatherData() {
        mErrorMessageDisplay.setVisibility(View.GONE);
        mWeatherRecyclerView.setVisibility(View.VISIBLE);
    }

    private void showErrorMessage() {
        mWeatherRecyclerView.setVisibility(View.GONE);
        mErrorMessageDisplay.setVisibility(View.VISIBLE);
    }

    private void openLocationMap() {
        String addressString = "1600 Ampitheatre Parkway, CA";
        Uri gmmIntentUri = Uri.parse("geo:0,0?q=" + addressString);
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);

        if (mapIntent.resolveActivity(getActivity().getPackageManager()) != null) {
            mapIntent.setPackage("com.google.android.apps.maps");
            startActivity(mapIntent);
        }
    }

    @Override
    public void onClick(String weatherForDay) {
        Intent intent = new Intent(getContext(), DetailActivity.class);
        intent.putExtra(Intent.EXTRA_TEXT, weatherForDay);
        startActivity(intent);
    }

    public class FetchWeatherTask extends AsyncTask<String, Void, String[]> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mLoadingIndicator.setVisibility(View.VISIBLE);
        }

        @Override
        protected String[] doInBackground(String... params) {
            if (params.length == 0) {
                return null;
            }

            String preferredWeatherLocation = params[0];
            URL weatherRequestUrl = NetworkUtils.buildSunshineUrl(preferredWeatherLocation);

            String[] simpleJsonWeatherData = null;
            try {
                String jsonWeatherResponse = NetworkUtils.getResponseFromHttpUrl(weatherRequestUrl);

                simpleJsonWeatherData = OpenWeatherJsonUtils.getSimpleWeatherStringsFromJson(getContext(), jsonWeatherResponse);
            } catch (Exception e) {
                e.printStackTrace();
            }

            return simpleJsonWeatherData;
        }

        @Override
        protected void onPostExecute(String[] weatherData) {
            mLoadingIndicator.setVisibility(View.GONE);
            if (weatherData != null) {
                showWeatherData();
                mForecastAdapter.setWeatherData(weatherData);
            } else showErrorMessage();
        }
    }
}
