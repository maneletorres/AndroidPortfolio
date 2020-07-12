package com.example.androidportfolio.ui.sunshine.viewmodel;

import android.content.Context;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.androidportfolio.data.SunshinePreferences;
import com.example.androidportfolio.utilities.NetworkUtils;
import com.example.androidportfolio.utilities.OpenWeatherJsonUtils;
import com.example.androidportfolio.utils.Resource;

import java.net.URL;

import static com.example.androidportfolio.utils.Status.ERROR;
import static com.example.androidportfolio.utils.Status.LOADING;
import static com.example.androidportfolio.utils.Status.SUCCESS;

public class SunshineViewModel extends ViewModel {

    // Observables:
    private final MutableLiveData<Resource<String[]>> _loadingWeathersObservable = new MutableLiveData<>();

    public LiveData<Resource<String[]>> loadingWeathersObservable() {
        return _loadingWeathersObservable;
    }

    public void loadWeatherData(Context context) {
        new FetchWeatherTask().execute(context);
    }

    public class FetchWeatherTask extends AsyncTask<Context, Void, String[]> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            _loadingWeathersObservable.postValue(new Resource<>(LOADING, null, null));
        }

        @Override
        protected String[] doInBackground(Context... params) {
            if (params == null) return null;

            String preferredWeatherLocation = SunshinePreferences.getPreferredWeatherLocation();
            URL weatherRequestUrl = NetworkUtils.buildSunshineUrl(preferredWeatherLocation);

            String[] simpleJsonWeatherData = null;
            try {
                String jsonWeatherResponse = NetworkUtils.getResponseFromHttpUrl(weatherRequestUrl);

                simpleJsonWeatherData = OpenWeatherJsonUtils.getSimpleWeatherStringsFromJson(params[0], jsonWeatherResponse);
            } catch (Exception e) {
                e.printStackTrace();
            }

            return simpleJsonWeatherData;
        }

        @Override
        protected void onPostExecute(String[] weatherData) {
            if (weatherData != null)
                _loadingWeathersObservable.postValue(new Resource<>(SUCCESS, weatherData, null));
            else _loadingWeathersObservable.postValue(new Resource<>(ERROR, null, null));
        }
    }
}
