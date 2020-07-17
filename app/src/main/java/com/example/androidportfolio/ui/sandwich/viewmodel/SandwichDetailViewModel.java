package com.example.androidportfolio.ui.sandwich.viewmodel;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Pair;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.androidportfolio.R;
import com.example.androidportfolio.data.model.Sandwich;
import com.example.androidportfolio.utilities.JsonUtils;
import com.example.androidportfolio.utils.Resource;

import static com.example.androidportfolio.utils.Status.ERROR;
import static com.example.androidportfolio.utils.Status.LOADING;
import static com.example.androidportfolio.utils.Status.SUCCESS;

public class SandwichDetailViewModel extends ViewModel {

    // Observables:
    private final MutableLiveData<Resource<Sandwich>> _loadingSandwichDetailObservable = new MutableLiveData<>();

    public LiveData<Resource<Sandwich>> loadingSandwichDetailObservable() {
        return _loadingSandwichDetailObservable;
    }

    public void loadSandwichDetail(Context context, int position) {
        Pair<Context, Integer> pair = new Pair<>(context, position);
        new FetchSandwichDetailTask().execute(pair);
    }

    public class FetchSandwichDetailTask extends AsyncTask<Pair<Context, Integer>, Void, Sandwich> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            _loadingSandwichDetailObservable.postValue(new Resource<>(LOADING, null, null));

        }

        @SafeVarargs
        @Override
        protected final Sandwich doInBackground(Pair<Context, Integer>... params) {
            if (params == null) return null;

            Pair<Context, Integer> pair = params[0];
            Context context = pair.first;
            int position = pair.second;

            String[] sandwiches = context.getResources().getStringArray(R.array.sandwich_details);
            String json = sandwiches[position];
            return JsonUtils.parseSandwichJson(json);
        }

        @Override
        protected void onPostExecute(Sandwich sandwich) {
            if (sandwich != null)
                _loadingSandwichDetailObservable.postValue(new Resource<>(SUCCESS, sandwich, null));
            else _loadingSandwichDetailObservable.postValue(new Resource<>(ERROR, null, null));
        }
    }
}
