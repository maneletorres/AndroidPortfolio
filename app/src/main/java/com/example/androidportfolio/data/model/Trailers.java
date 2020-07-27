package com.example.androidportfolio.data.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Trailers {
    @SerializedName("id")
    private int id;

    @SerializedName("results")
    private List<Trailer> trailers;

    public List<Trailer> getTrailers() {
        return trailers;
    }
}
