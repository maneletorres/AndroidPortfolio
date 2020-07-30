package com.example.androidportfolio.di.component;

import com.example.androidportfolio.di.module.ContextModule;
import com.example.androidportfolio.di.module.NetworkModule;
import com.example.androidportfolio.ui.githubsearch.view.GitHubSearchFragment;
import com.example.androidportfolio.ui.movies.view.MovieDetailFragment;
import com.example.androidportfolio.ui.movies.view.MoviesFragment;
import com.example.androidportfolio.ui.sunshine.view.SunshineFragment;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {NetworkModule.class, ContextModule.class})
public interface AppComponent {
    void inject(SunshineFragment sunshineFragment);

    void inject(MoviesFragment moviesFragment);

    void inject(MovieDetailFragment movieDetailFragment);

    void inject(GitHubSearchFragment gitHubSearchFragment);
}
