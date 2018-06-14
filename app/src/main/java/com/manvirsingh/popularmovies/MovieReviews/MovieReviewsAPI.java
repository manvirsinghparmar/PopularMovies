package com.manvirsingh.popularmovies.MovieReviews;


import retrofit2.Call;
import retrofit2.http.GET;

public interface MovieReviewsAPI {

    String BASE_URL = "https://api.themoviedb.org/";



    //Popular movies Reviews
    @GET("reviews?api_key=652f215200fe98dd39d7259b5be7deac")
    Call<ResultReviews> getPopularmovieReviews();
}


