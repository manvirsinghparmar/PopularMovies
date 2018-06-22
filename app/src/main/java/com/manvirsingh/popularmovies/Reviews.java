package com.manvirsingh.popularmovies;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.manvirsingh.popularmovies.MovieReviews.MovieReview;
import com.manvirsingh.popularmovies.MovieReviews.MovieReviewsAPI;
import com.manvirsingh.popularmovies.MovieReviews.MovieReviewsAdapter;
import com.manvirsingh.popularmovies.MovieReviews.ResultReviews;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Reviews extends AppCompatActivity {
    private static final String TAG = "Reviews";

    private final static String BASE_URL = "https://api.themoviedb.org/3/movie/";

    private RecyclerView mRecylerView;

    private ProgressBar progressBar;

    private TextView Errormessage;

    private TextView EmptyReview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate: MSP- On Create Started");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reviews);

        mRecylerView = (RecyclerView) findViewById(R.id.recyclerView);
        progressBar = (ProgressBar) findViewById(R.id.review_progressbar);
        Errormessage = (TextView) findViewById(R.id.error_message_review);
        EmptyReview=(TextView)findViewById(R.id.Review_empty_message);

        FetchJSONDataReview();
    }


    public void FetchJSONDataReview() {
        progressBar.setVisibility(View.VISIBLE);


        Log.d(TAG, "FetchJSONDataReview: MSP -FetchJSONDataReview ");

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

        final MovieReviewsAPI moviereviewAPI = retrofit.create(MovieReviewsAPI.class);

        Call<ResultReviews> call = moviereviewAPI.getPopularmovieReviews();


        call.enqueue(new Callback<ResultReviews>() {
            @Override
            public void onResponse(Call<ResultReviews> call, Response<ResultReviews> response) {
                //Log for checking Server Response

                try {
                    Log.d(TAG, "onResponse: MSP-On Server Responce:" + response.toString());

                    //Log for checking the data obtained upon sucessfull connection
                    Log.d(TAG, "onResponse: MSP- On Server Response:" + response.body().toString());

                } catch (NullPointerException e) {

                    Log.d(TAG, "onResponse: MSP- Null Pointer Exception:" + e.getMessage());
                }

                final ArrayList<MovieReview> list = response.body().getReview();

                progressBar.setVisibility(View.GONE);

                for (int i = 0; i < list.size(); i++) {
                    Log.d(TAG, "onResponse: \n" + "MSP-Author:" + list.get(i).getAuthor() + "\n"
                            + "MSP-Content:" + list.get(i).getContent() + "\n"

                    );

                }

                if (list.isEmpty()) {
                    Log.d(TAG, "onResponse: ");
                    EmptyReview.setVisibility(View.VISIBLE);
                    EmptyReview.setText("No Reviews Available for this movie");


                } else {
                    mRecylerView.setAdapter(new MovieReviewsAdapter(list, getApplicationContext()));
                    Log.d(TAG, "onResponse: MSP -Set Adapter");
                    mRecylerView.setHasFixedSize(true);
                    Log.d(TAG, "onResponse: MSP- Set Size");
                    mRecylerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                    Log.d(TAG, "onResponse: MSP- Set Layout Manager");
                }
            }

            @Override
            public void onFailure(Call<ResultReviews> call, Throwable t) {
                Errormessage.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.INVISIBLE);
                mRecylerView.setVisibility(View.INVISIBLE);

                Toast.makeText(Reviews.this, "Something Went Wrong", Toast.LENGTH_LONG).show();

            }
        });


    }
}
