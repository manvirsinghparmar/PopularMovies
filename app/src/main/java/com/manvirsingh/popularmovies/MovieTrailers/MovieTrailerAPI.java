package com.manvirsingh.popularmovies.MovieTrailers;

import com.manvirsingh.popularmovies.MovieReviews.ResultReviews;

import retrofit2.Call;
import retrofit2.http.GET;

public interface MovieTrailerAPI {

    String BASE_URL = "https://api.themoviedb.org/";



    //Popular movies Trailers
    @GET("videos?api_key=Enter your API key")
    Call<ResultTrailers> getPopularmovieTrailers();
}
