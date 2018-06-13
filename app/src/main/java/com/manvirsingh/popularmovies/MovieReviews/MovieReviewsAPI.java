package com.manvirsingh.popularmovies.MovieReviews;


import retrofit2.Call;
import retrofit2.http.GET;

public interface MovieReviewsAPI {

    String BASE_URL = "https://api.themoviedb.org/";



    //Popular movies Reviews
    @GET("reviews?api_key=Enter Your API Key")
    Call<ResultReviews> getPopularmovieReviews();
}


