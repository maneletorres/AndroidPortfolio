package com.example.androidportfolio.utilities;

import com.example.androidportfolio.data.model.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MovieParser {
    public static List<Movie> getMoviesFromJson(String moviesJsonStr) {
        try {
            List<Movie> movies = new ArrayList<>();

            JSONObject moviesJSON = new JSONObject(moviesJsonStr);
            JSONArray results = moviesJSON.getJSONArray("results");
            for (int i = 0; i < results.length(); i++) {
                JSONObject movieJSON = results.getJSONObject(i);

                double popularity = movieJSON.getDouble("popularity");
                int voteCount = movieJSON.getInt("vote_count");
                boolean video = movieJSON.getBoolean("video");
                String poster_path = movieJSON.getString("poster_path");
                int id = movieJSON.getInt("id");
                boolean adult = movieJSON.getBoolean("adult");
                String backdrop_path = movieJSON.getString("backdrop_path");
                String original_language = movieJSON.getString("original_language");
                String original_title = movieJSON.getString("original_title");
                String title = movieJSON.getString("title");
                double vote_average = movieJSON.getDouble("vote_average");
                String overview = movieJSON.getString("overview");
                String release_date = movieJSON.getString("release_date");

                Movie movie = new Movie(popularity, voteCount, video, poster_path, id, adult, backdrop_path, original_language, original_title, title, vote_average, overview, release_date);
                movies.add(movie);
            }

            return movies;
        } catch (JSONException ex) {
            ex.printStackTrace();
        }

        return null;
    }
}
