package com.example.androidportfolio.ui.favoritetoys.viewmodel;

import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.androidportfolio.data.model.Toy;
import com.example.androidportfolio.utilities.ToyBox;
import com.example.androidportfolio.utils.Resource;

import java.util.List;

import static com.example.androidportfolio.utils.Status.ERROR;
import static com.example.androidportfolio.utils.Status.LOADING;
import static com.example.androidportfolio.utils.Status.SUCCESS;

public class FavoriteToysViewModel extends ViewModel {

    // Observables:
    private final MutableLiveData<Resource<String[]>> _loadingToysObservable = new MutableLiveData<>();
    public LiveData<Resource<String[]>> loadingToysObservable() {
        return _loadingToysObservable;
    }

    public void start() {
        new FetchToyTask().execute();
    }

    public class FetchToyTask extends AsyncTask<Void, Void, String[]> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            _loadingToysObservable.postValue(new Resource<>(LOADING, null, null));
        }

        @Override
        protected String[] doInBackground(Void... params) {
            return ToyBox.getToyNames();
        }

        @Override
        protected void onPostExecute(String[] toyData) {
            if (toyData != null)
                _loadingToysObservable.postValue(new Resource<>(SUCCESS, toyData, null));
            else _loadingToysObservable.postValue(new Resource<>(ERROR, null, null));
        }
    }
}
