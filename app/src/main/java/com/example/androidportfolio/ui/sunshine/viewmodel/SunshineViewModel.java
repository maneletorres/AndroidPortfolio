package com.example.androidportfolio.ui.sunshine.viewmodel;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.androidportfolio.data.SunshinePreferences;
import com.example.androidportfolio.data.rest.repository.SunshineRepository;
import com.example.androidportfolio.utilities.OpenWeatherJsonUtils;
import com.example.androidportfolio.utils.Resource;
import com.google.gson.JsonElement;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.androidportfolio.utils.Status.ERROR;
import static com.example.androidportfolio.utils.Status.LOADING;
import static com.example.androidportfolio.utils.Status.SUCCESS;

public class SunshineViewModel extends ViewModel {

    // Observables:
    private final MutableLiveData<Resource<String[]>> _loadingWeathersObservable = new MutableLiveData<>();

    // Variables:
    private final SunshineRepository sunshineRepository;

    @Inject
    public SunshineViewModel(SunshineRepository sunshineRepository) {
        this.sunshineRepository = sunshineRepository;
    }

    public LiveData<Resource<String[]>> loadingWeathersObservable() {
        return _loadingWeathersObservable;
    }

    public void loadWeatherData(Context context) {
        _loadingWeathersObservable.postValue(new Resource<>(LOADING, null, null));

        // TODO:
        sunshineRepository.getForecast(SunshinePreferences.getPreferredWeatherLocation())
                .enqueue(new Callback<JsonElement>() {
                    @Override
                    public void onResponse(@NonNull Call<JsonElement> call, @NonNull Response<JsonElement> response) {
                        JsonElement jsonWeatherResponse = response.body();
                        if (jsonWeatherResponse != null) {
                            try {
                                String[] simpleJsonWeatherData = OpenWeatherJsonUtils.getSimpleWeatherStringsFromJson(context, jsonWeatherResponse.toString());
                                _loadingWeathersObservable.postValue(new Resource<>(SUCCESS, simpleJsonWeatherData, null));
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        } else
                            _loadingWeathersObservable.postValue(new Resource<>(ERROR, null, null));
                    }

                    @Override
                    public void onFailure(@NonNull Call<JsonElement> call, @NonNull Throwable t) {
                        _loadingWeathersObservable.postValue(new Resource<>(ERROR, null, null));
                    }
                });
    }
}
