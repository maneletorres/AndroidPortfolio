package com.example.androidportfolio.ui.movies.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.androidportfolio.data.model.Sandwich;
import com.example.androidportfolio.utils.Resource;

public class MovieDetailViewModel extends ViewModel {

    // Observables:
    private final MutableLiveData<Resource<Sandwich>> _loadingSandwichDetailObservable = new MutableLiveData<>();

    public LiveData<Resource<Sandwich>> loadingSandwichDetailObservable() {
        return _loadingSandwichDetailObservable;
    }
}
