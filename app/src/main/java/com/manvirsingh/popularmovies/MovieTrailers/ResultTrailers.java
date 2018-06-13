package com.manvirsingh.popularmovies.MovieTrailers;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.manvirsingh.popularmovies.MovieReviews.MovieReview;

import java.util.ArrayList;

public class ResultTrailers {

    @SerializedName("results")
    @Expose
    private ArrayList<MovieTrailer> trailers;

    public ArrayList<MovieTrailer> getTrailers() {
        return trailers;
    }

    public void setTrailers(ArrayList<MovieTrailer> trailers) {
        this.trailers = trailers;
    }

    @Override
    public String toString() {
        return "ResultTrailers{" +
                "trailers=" + trailers +
                '}';
    }
}
