package com.manvirsingh.popularmovies.MovieReviews;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


import java.util.ArrayList;

public class ResultReviews {

    @SerializedName("results")
    @Expose
    private ArrayList<MovieReview> review;

    public ArrayList<MovieReview> getReview() {
        return review;
    }

    public void setReview(ArrayList<MovieReview> review) {
        this.review = review;
    }

    @Override
    public String toString() {
        return "ResultReviews{" +
                "review=" + review +
                '}';
    }
}
