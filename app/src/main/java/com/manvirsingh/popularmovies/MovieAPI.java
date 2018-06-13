package com.manvirsingh.popularmovies;

import com.manvirsingh.popularmovies.MovieAttributes.MovieAttributes;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface MovieAPI {


    String BASE_URL = "https://api.themoviedb.org/";

    //Popular movies
    @GET("/3/movie/popular?api_key=652f215200fe98dd39d7259b5be7deac")
    Call<MovieAttributes> getPopularmovieData();

    //Top Rated Movies
    @GET("/3/movie/top_rated?api_key=652f215200fe98dd39d7259b5be7deac")
    Call<MovieAttributes> getTopRatedmovieData();

}