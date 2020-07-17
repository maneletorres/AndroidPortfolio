package com.example.androidportfolio.ui.sunshine.viewmodel;

import android.content.Context;
import android.os.AsyncTask;

import androidx.lifecycle.ViewModel;

public class SunshineDetailViewModel extends ViewModel {

    // Observables:

    public class FetchSunshineDetailTask extends AsyncTask<Context, Void, String[]> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected String[] doInBackground(Context... contexts) {
            return new String[0];
        }

        @Override
        protected void onPostExecute(String[] strings) {
            super.onPostExecute(strings);
        }
    }
}
