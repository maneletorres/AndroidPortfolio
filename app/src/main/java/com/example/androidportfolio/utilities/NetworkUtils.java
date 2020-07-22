package com.example.androidportfolio.utilities;

/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import android.net.Uri;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

/**
 * These utilities will be used to communicate with the network.
 */
public class NetworkUtils {

    final static String GITHUB_BASE_URL =
            "https://api.github.com/search/repositories";

    /*
     * The sort field. One of stars, forks, or updated.
     * Default: results are sorted by best match if no field is specified.
     */
    final static String SORT_PARAM = "sort";
    final static String sortBy = "stars";

    final static String QUERY_PARAM = "q";
    final static String LAT_PARAM = "lat";
    final static String LON_PARAM = "lon";
    final static String FORMAT_PARAM = "mode";
    final static String API_KEY_PARAM = "api_key";

    /*

     */
    final static String TMDB_BASE_URL = "http://api.themoviedb.org/3/movie/popular?";

    final static String PARAM_API_KEY = "api_key";

    final static String TMDB_IMAGE_BASE_URL = "http://image.tmdb.org/t/p/";

    /*
     * NOTE: These values only effect responses from OpenWeatherMap, NOT from the fake weather
     * server. They are simply here to allow us to teach you how to build a URL if you were to use
     * a real API.If you want to connect your app to OpenWeatherMap's API, feel free to! However,
     * we are not going to show you how to do so in this course.
     */
    final static String UNITS_PARAM = "units";
    final static String DAYS_PARAM = "cnt";
    private static final String TAG = NetworkUtils.class.getSimpleName();
    private static final String DYNAMIC_WEATHER_URL =
            "https://andfun-weather.udacity.com/weather";
    private static final String STATIC_WEATHER_URL =
            "https://andfun-weather.udacity.com/staticweather";
    private static final String FORECAST_BASE_URL = STATIC_WEATHER_URL;
    /* The format we want our API to return */
    private static final String format = "json";
    /* The units we want our API to return */
    private static final String units = "metric";
    /* The number of days we want our API to return */
    private static final int numDays = 14;

    /**
     * Builds the URL used to query GitHub.
     *
     * @param githubSearchQuery The keyword that will be queried for.
     * @return The URL to use to query the GitHub server.
     */
    public static URL buildGithubUrl(String githubSearchQuery) {
        Uri uri = Uri.parse(GITHUB_BASE_URL)
                .buildUpon()
                .appendQueryParameter(QUERY_PARAM, githubSearchQuery)
                .appendQueryParameter(SORT_PARAM, sortBy)
                .build();
        URL url = null;
        try {
            url = new URL(uri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return url;
    }

    /**
     * Builds the URL used to talk to the weather server using a location. This location is based
     * on the query capabilities of the weather provider that we are using.
     *
     * @param locationQuery The location that will be queried for.
     * @return The URL to use to query the weather server.
     */
    public static URL buildSunshineUrl(String locationQuery) {
        Uri uri = Uri.parse(FORECAST_BASE_URL)
                .buildUpon()
                .appendQueryParameter(QUERY_PARAM, locationQuery)
                .appendQueryParameter(FORMAT_PARAM, format)
                .appendQueryParameter(UNITS_PARAM, units)
                .appendQueryParameter(DAYS_PARAM, Integer.toString(numDays))
                .build();
        URL url = null;
        try {
            url = new URL(uri.toString());
        } catch (MalformedURLException ex) {
            ex.printStackTrace();
        }

        Log.v(TAG, "Built URI " + url);

        return url;
    }

    /**
     *
     * @return
     */
    public static URL buildTMDBUrl() {
        Uri uri = Uri.parse(TMDB_BASE_URL)
                .buildUpon()
                .appendQueryParameter(API_KEY_PARAM, "53d8684e54311e305e04667194bbd1bb")
                //.appendQueryParameter(PARAM_SORT, sortBy)
                .build();
        URL url = null;
        try {
            url = new URL(uri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return url;
    }

    /**
     * This method returns the entire result from the HTTP response.
     *
     * @param url The URL to fetch the HTTP response from.
     * @return The contents of the HTTP response.
     * @throws IOException Related to network and stream reading
     */
    public static String getResponseFromHttpUrl(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            } else {
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }
    }
}
