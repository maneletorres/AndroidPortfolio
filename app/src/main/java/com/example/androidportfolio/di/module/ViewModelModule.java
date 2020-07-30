package com.example.androidportfolio.di.module;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.androidportfolio.di.ViewModelFactory;
import com.example.androidportfolio.di.ViewModelKey;
import com.example.androidportfolio.ui.movies.viewmodel.MovieDetailViewModel;
import com.example.androidportfolio.ui.movies.viewmodel.MoviesViewModel;
import com.example.androidportfolio.ui.sunshine.viewmodel.SunshineViewModel;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
public abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(SunshineViewModel.class)
    abstract ViewModel bindSunshineViewModel(SunshineViewModel sunshineViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(MoviesViewModel.class)
    abstract ViewModel bindMoviesViewModel(MoviesViewModel moviesViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(MovieDetailViewModel.class)
    abstract ViewModel bindMovieDetailViewModel(MovieDetailViewModel movieDetailViewModel);

    @Binds
    abstract ViewModelProvider.Factory bindViewModelFactory(ViewModelFactory factory);
}
