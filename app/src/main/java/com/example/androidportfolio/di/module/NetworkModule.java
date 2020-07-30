package com.example.androidportfolio.di.module;

import com.example.androidportfolio.data.rest.remote.GitHubRetrofitService;
import com.example.androidportfolio.data.rest.remote.MovieRetrofitService;
import com.example.androidportfolio.data.rest.remote.WeatherRetrofitService;
import com.example.androidportfolio.utilities.Constants;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module(includes = ViewModelModule.class)
public abstract class NetworkModule {

    /*@Singleton
    @Provides
    static Retrofit provideRetrofit() {
        return new Retrofit.Builder()
                .baseUrl(Constants.FORECAST_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }

    @Provides
    @Singleton
    static SunshineRetrofitService provideSunshineRetrofitService(Retrofit retrofit) {
        return retrofit.create(SunshineRetrofitService.class);
    }*/

    @Provides
    static WeatherRetrofitService provideSunshineRetrofitService() {
        return new Retrofit.Builder()
                .baseUrl(Constants.FORECAST_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
                .create(WeatherRetrofitService.class);
    }

    @Provides
    static MovieRetrofitService provideMovieRetrofitService() {
        return new Retrofit.Builder()
                .baseUrl(Constants.TMDB_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
                .create(MovieRetrofitService.class);
    }

    @Provides
    static GitHubRetrofitService provideGitHubRetrofitService() {
        return new Retrofit.Builder()
                .baseUrl(Constants.GITHUB_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
                .create(GitHubRetrofitService.class);
    }
}
