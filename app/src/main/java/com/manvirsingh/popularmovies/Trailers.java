package com.manvirsingh.popularmovies;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.manvirsingh.popularmovies.MovieReviews.MovieReview;
import com.manvirsingh.popularmovies.MovieReviews.MovieReviewsAPI;
import com.manvirsingh.popularmovies.MovieReviews.MovieReviewsAdapter;
import com.manvirsingh.popularmovies.MovieReviews.ResultReviews;
import com.manvirsingh.popularmovies.MovieTrailers.MovieTrailer;
import com.manvirsingh.popularmovies.MovieTrailers.MovieTrailerAPI;
import com.manvirsingh.popularmovies.MovieTrailers.MovieTrailerAdapter;
import com.manvirsingh.popularmovies.MovieTrailers.ResultTrailers;

import java.security.Key;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Trailers extends AppCompatActivity {

    private static final String TAG = "Trailers";

    private final static String BASE_URL = "https://api.themoviedb.org/3/movie/";

    private RecyclerView tRecylerView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trailers);

        tRecylerView = (RecyclerView) findViewById(R.id.recyclerViewTrailer);



        FetchJSONDataTrailers();

    }

    public void FetchJSONDataTrailers() {



        Log.d(TAG, "FetchJSONDataReview: BKP -FetchJSONDataReview ");

        Intent incoming = getIntent();

        if (incoming == null) {
            return;
        }

        String movie_ID = incoming.getStringExtra("mID");
        Log.d(TAG, "FetchJSONDataReview: MSP- Movie Id" + movie_ID);

        Uri uriID = Uri.parse(BASE_URL).buildUpon().appendEncodedPath(movie_ID).build();

        final String Review1 = uriID.toString() + "/";
        Log.d(TAG, "FetchJSONDataReview: MSP - " + Review1);


        Retrofit retrofit = new Retrofit.Builder().baseUrl(Review1).addConverterFactory(GsonConverterFactory.create()).build();
        Log.d(TAG, "FetchJSONDataReview: MSP- " + retrofit);

        final MovieTrailerAPI movieTrailerAPI = retrofit.create(MovieTrailerAPI.class);

        Call<ResultTrailers> call = movieTrailerAPI.getPopularmovieTrailers();




        call.enqueue(new Callback<ResultTrailers>() {
            @Override
            public void onResponse(Call<ResultTrailers> call, Response<ResultTrailers> response) {
                //Log for checking Server Response

                try {
                    Log.d(TAG, "onResponse: MSP-On Server Responce:" + response.toString());

                    //Log for checking the data obtained upon sucessfull connection
                    Log.d(TAG, "onResponse: MSP- On Server Response:" + response.body().toString());

                } catch (NullPointerException e) {

                    Log.d(TAG, "onResponse: MSP- Null Pointer Exception:" + e.getMessage());
                }




                final ArrayList<MovieTrailer> list = response.body().getTrailers();


                for (int i = 0; i < list.size(); i++) {
                    Log.d(TAG, "onResponse: \n" + "MSP-id:" + list.get(i).getId() + "\n"
                            + "MSP-Key:" + list.get(i).getKey() + "\n"
                            + "MSP-SIZE" + list.get(i).getSize() + "\n"
                            + "MSP- Name" + list.get(i).getName() + "\n\n\n"
                    );


                    tRecylerView.setAdapter(new MovieTrailerAdapter(list,getApplicationContext()));
                    Log.d(TAG, "onResponse: MSP- Set Adapter");
                    tRecylerView.setHasFixedSize(true);
                    Log.d(TAG, "onResponse: MSP- Fas fixed Size");
                    tRecylerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                    Log.d(TAG, "onResponse: MSP- Linear Layout Manager");


                }

            }

            @Override
            public void onFailure(Call<ResultTrailers> call, Throwable t) {

                Toast.makeText(Trailers.this, "Something Went Wrong", Toast.LENGTH_LONG).show();

            }
        });
    }


}

