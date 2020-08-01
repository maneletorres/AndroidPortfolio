package com.example.androidportfolio.ui.sunshine.viewmodel;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.androidportfolio.data.rest.repository.WeatherRepository;
import com.example.androidportfolio.utils.OpenWeatherJsonUtils;
import com.example.androidportfolio.utils.Resource;
import com.example.androidportfolio.utils.SunshinePreferences;
import com.google.gson.JsonElement;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

import static com.example.androidportfolio.utils.Status.ERROR;
import static com.example.androidportfolio.utils.Status.LOADING;
import static com.example.androidportfolio.utils.Status.SUCCESS;

public class SunshineViewModel extends ViewModel {
    private final WeatherRepository weatherRepository;
    private final MutableLiveData<Resource<String[]>> _loadingWeathersObservable = new MutableLiveData<>();
    private CompositeDisposable disposable = new CompositeDisposable();

    @Inject
    public SunshineViewModel(WeatherRepository weatherRepository) {
        this.weatherRepository = weatherRepository;
    }

    public LiveData<Resource<String[]>> loadingWeathersObservable() {
        return _loadingWeathersObservable;
    }

    public void fetchForecasts(Context context) {
        _loadingWeathersObservable.postValue(new Resource<>(LOADING, null, null));

        disposable.add(weatherRepository.getForecast(SunshinePreferences.getPreferredWeatherLocation())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<JsonElement>() {
                    @Override
                    public void onSuccess(JsonElement jsonElement) {
                        try {
                            String[] simpleJsonWeatherData = OpenWeatherJsonUtils.getSimpleWeatherStringsFromJson(context, jsonElement.toString());
                            _loadingWeathersObservable.postValue(new Resource<>(SUCCESS, simpleJsonWeatherData, null));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        _loadingWeathersObservable.postValue(new Resource<>(ERROR, null, null));
                    }
                }));
    }
}
