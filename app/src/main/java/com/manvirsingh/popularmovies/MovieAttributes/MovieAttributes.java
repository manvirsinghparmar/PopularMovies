package com.manvirsingh.popularmovies.MovieAttributes;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.security.PrivateKey;
import java.util.ArrayList;

public class MovieAttributes {

    @SerializedName("results")
    @Expose
    private ArrayList<Results> result;

    public ArrayList<Results> getResult() {
        return result;
    }

    public void setResult(ArrayList<Results> result) {
        this.result = result;
    }

    public MovieAttributes(ArrayList<Results> result) {
        this.result = result;


    }

    @Override
    public String toString() {
        return "MovieAttributes{" +
                "result=" + result +
                '}';
    }
}

