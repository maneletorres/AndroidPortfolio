package com.example.androidportfolio.data.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Reviews {
    @SerializedName("id")
    private int id;

    @SerializedName("page")
    private int page;

    @SerializedName("results")
    private List<Review> reviews;

    @SerializedName("total_pages")
    private int totalPages;

    @SerializedName("total_results")
    private int totalResults;

    public List<Review> getReviews() {
        return reviews;
    }
}
