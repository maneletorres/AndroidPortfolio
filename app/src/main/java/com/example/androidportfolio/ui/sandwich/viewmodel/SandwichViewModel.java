package com.example.androidportfolio.ui.sandwich.viewmodel;

import android.content.Context;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.androidportfolio.R;
import com.example.androidportfolio.utils.Resource;

import static com.example.androidportfolio.utils.Status.ERROR;
import static com.example.androidportfolio.utils.Status.LOADING;
import static com.example.androidportfolio.utils.Status.SUCCESS;

public class SandwichViewModel extends ViewModel {

    // Observables:
    private final MutableLiveData<Resource<String[]>> _loadingSandwichesObservable = new MutableLiveData<>();

    public LiveData<Resource<String[]>> loadingSandwichesObservable() {
        return _loadingSandwichesObservable;
    }

    public void start(Context context) {
        new FetchSandwichTask().execute(context);
    }

    public class FetchSandwichTask extends AsyncTask<Context, Void, String[]> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            _loadingSandwichesObservable.postValue(new Resource<>(LOADING, null, null));
        }

        @Override
        protected String[] doInBackground(Context... contexts) {
            if (contexts == null) return null;
            return contexts[0].getResources().getStringArray(R.array.sandwich_names);
        }

        @Override
        protected void onPostExecute(String[] sandwichListData) {
            if (sandwichListData == null)
                _loadingSandwichesObservable.postValue(new Resource<>(ERROR, null, null));
            else
                _loadingSandwichesObservable.postValue(new Resource<>(SUCCESS, sandwichListData, null));
        }
    }
}
