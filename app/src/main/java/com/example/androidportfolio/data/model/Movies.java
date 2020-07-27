package com.example.androidportfolio.data.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Movies {
    @SerializedName("page")
    private int page;
    @SerializedName("total_results")
    private int totalResults;
    @SerializedName("total_pages")
    private int totalPages;
    @SerializedName("results")
    private List<Movie> movies;

    public List<Movie> getMovies() {
        return movies;
    }
}
