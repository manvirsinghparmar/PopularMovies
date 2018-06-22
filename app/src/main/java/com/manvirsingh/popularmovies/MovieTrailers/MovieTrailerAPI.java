package com.manvirsingh.popularmovies.MovieTrailers;

import com.manvirsingh.popularmovies.MovieReviews.ResultReviews;

import retrofit2.Call;
import retrofit2.http.GET;

public interface MovieTrailerAPI {

    String BASE_URL = "https://api.themoviedb.org/";



    //Popular movies Trailers
    @GET("videos?api_key=652f215200fe98dd39d7259b5be7deac")
    Call<ResultTrailers> getPopularmovieTrailers();
}
