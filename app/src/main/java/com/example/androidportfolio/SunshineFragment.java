package com.example.androidportfolio;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.androidportfolio.data.SunshinePreferences;
import com.example.androidportfolio.utilities.NetworkUtils;
import com.example.androidportfolio.utilities.OpenWeatherJsonUtils;

import org.json.JSONException;

import java.io.IOException;
import java.net.URL;

public class SunshineFragment extends Fragment {
    private TextView mWeatherTextView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sunshine, container, false);
        setHasOptionsMenu(true);

        /*
         * Using findViewById, we get a reference to our TextView from xml. This allows us to
         * do things like set the text of the TextView.
         */
        mWeatherTextView = view.findViewById(R.id.tv_weather_data);

        loadWeatherData();

        return view;
    }

    public void loadWeatherData() {
        String preferredWeatherLocation = SunshinePreferences.getPreferredWeatherLocation(getContext());
        new FetchWeatherTask().execute(preferredWeatherLocation);
    }

    public class FetchWeatherTask extends AsyncTask<String, Void, String[]> {

        @Override
        protected String[] doInBackground(String... strings) {
            if (strings.length == 0) {
                return null;
            }

            String preferredWeatherLocation = strings[0];
            URL url = NetworkUtils.buildSunshineUrl(preferredWeatherLocation);
            String[] simpleJsonWeatherData = null;
            try {
                String jsonWeatherResponse = NetworkUtils.getResponseFromHttpUrl(url);
                simpleJsonWeatherData = OpenWeatherJsonUtils.getSimpleWeatherStringsFromJson(getContext(), jsonWeatherResponse);
            } catch (IOException | JSONException ex) {
                ex.printStackTrace();
            }

            return simpleJsonWeatherData;
        }

        @Override
        protected void onPostExecute(String[] strings) {
            if (strings != null) {
                for (int i = 0; i < strings.length; i++) {
                    mWeatherTextView.append(strings[i] + "\n\n\n");
                }
            }

        }
    }
}
