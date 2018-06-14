package com.manvirsingh.popularmovies;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.manvirsingh.popularmovies.DataBase.MovieDBhelper;
import com.manvirsingh.popularmovies.DataBase.MoviesContract;
import com.manvirsingh.popularmovies.MovieAttributes.MovieAttributes;
import com.manvirsingh.popularmovies.MovieAttributes.Results;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private final static String BASE_URL = "https://api.themoviedb.org";


    private GridView gridView;
    private ProgressBar progressBar;
    private TextView ErrorMessageDisplay;
    private GridViewAdapter mGridAdapter;

    private ArrayList<Results> mMovies = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate: MSP- On create Started");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        gridView = (GridView) findViewById(R.id.main_grid_View);
        progressBar = (ProgressBar) findViewById(R.id.main_progressbar);
        ErrorMessageDisplay = (TextView) findViewById(R.id.error_message);


        //Checking Saved Instance state during onCreate
        if (savedInstanceState == null) {
            Log.d(TAG, "onCreate: MSP- Saved Instance is Null");
            FetchJSONPopularMovies();
        } else {
            Log.d(TAG, "onCreate: MSP- Saved Instance is NOT Null");

            if ((savedInstanceState.containsKey("Saved_movies")) && isNetworkConnected(this)) {
                Log.d(TAG, "onCreate: MSP-Save Instance Conditon check");


                ArrayList<Results> movies = savedInstanceState.getParcelableArrayList("Saved_movies");

                try {
                    mGridAdapter = new GridViewAdapter(this, R.layout.layout_for_grid, movies);

                    gridView.setAdapter(mGridAdapter);

                    //Method call for ON click respond
                    senddataIntent();

                } catch (NullPointerException e) {
                    Log.d(TAG, "onCreate: Msp-NullPointerException" + e.getMessage());
                }

            } else {
                //Checking both network state as well as save instance
                if (savedInstanceState.containsKey("error") || !isNetworkConnected(this)) {
                    Log.d(TAG, "onCreate: MSP Error Message ");

                    ErrorMessageDisplay.setVisibility(View.VISIBLE);
                }

            }
        }
    }

    public static boolean isNetworkConnected(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
    }


    //Method to fetch JSON data from moviedb for Popular Movies
    public void FetchJSONPopularMovies() {

        Retrofit retrofit = new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();

        final MovieAPI movieAPI = retrofit.create(MovieAPI.class);

        Call<MovieAttributes> call = movieAPI.getPopularmovieData();

        progressBar.setVisibility(View.VISIBLE);

        call.enqueue(new Callback<MovieAttributes>() {
            @Override
            public void onResponse(Call<MovieAttributes> call, Response<MovieAttributes> response) {

                //Log for checking Server Response
                Log.d(TAG, "onResponse: MSP-On Server Responce:" + response.toString());

                //Log for checking the data obtained upon sucessfull connection
                Log.d(TAG, "onResponse: MSP- On Server Response:" + response.body().toString());


                final ArrayList<Results> list = response.body().getResult();


                progressBar.setVisibility(View.INVISIBLE);

                for (int i = 0; i < list.size(); i++) {
                    Log.d(TAG, "onResponse: \n" + "title:" + list.get(i).getTitle() + "\n"

                            + "Release Date: " + list.get(i).getRelease_date() + "\n"
                            + "Poster Path:" + list.get(i).getPoster_path() + "\n"
                            + "Over View:" + list.get(i).getOverview() + "\n\n\n"
                    );

                    mGridAdapter = new GridViewAdapter(MainActivity.this, R.layout.layout_for_grid, list);
                    gridView.setAdapter(mGridAdapter);
                    gridView.setVisibility(View.VISIBLE);
                }
                //Method call for ON click respond
                senddataIntent();


            }

            @Override
            public void onFailure(Call<MovieAttributes> call, Throwable t) {
                Log.d(TAG, "onFailure: MSP- ON FAILURE: " + t.getMessage());

                showErrorMessage();

                Toast.makeText(MainActivity.this, "Something Went Wrong", Toast.LENGTH_LONG).show();


            }
        });
    }


    //Method to fetch JSON data from moviedb for Top Rated Movies
    public void FetchJSONTopRatedMovies() {

        Retrofit retrofit = new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();

        final MovieAPI movieAPI = retrofit.create(MovieAPI.class);

        Call<MovieAttributes> call = movieAPI.getTopRatedmovieData();

        progressBar.setVisibility(View.VISIBLE);

        call.enqueue(new Callback<MovieAttributes>() {
            @Override
            public void onResponse(Call<MovieAttributes> call, Response<MovieAttributes> response) {

                //Log for checking Server Response
                Log.d(TAG, "onResponse: MSP-On Server Responce:" + response.toString());

                //Log for checking the data obtained upon sucessfull connection
                Log.d(TAG, "onResponse: MSP- On Server Response:" + response.body().toString());


                final ArrayList<Results> list = response.body().getResult();


                progressBar.setVisibility(View.INVISIBLE);

                for (int i = 0; i < list.size(); i++) {
                    Log.d(TAG, "onResponse: \n" + "title:" + list.get(i).getTitle() + "\n"

                            + "Release Date: " + list.get(i).getRelease_date() + "\n"
                            + "Poster Path:" + list.get(i).getPoster_path() + "\n"
                            + "Over View:" + list.get(i).getOverview() + "\n\n\n"
                    );

                    mGridAdapter = new GridViewAdapter(MainActivity.this, R.layout.layout_for_grid, list);
                    gridView.setAdapter(mGridAdapter);
                    gridView.setVisibility(View.VISIBLE);
                }
                //Method call for ON click response-Detail Activity
                senddataIntent();
            }

            @Override
            public void onFailure(Call<MovieAttributes> call, Throwable t) {
                Log.d(TAG, "onFailure: MSP- ON FAILURE: " + t.getMessage());

                showErrorMessage();

                Toast.makeText(MainActivity.this, "Something Went Wrong", Toast.LENGTH_LONG).show();
            }
        });
    }

    //Method to send data from Main Activity to Movie Details Screen
    public void senddataIntent() {
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d(TAG, "onItemClick: MSP- On Item clcik Executed");

                Results item = (Results) parent.getItemAtPosition(position);

                Intent intent = new Intent(MainActivity.this, MovieDetailsScreen.class);

                intent.putExtra("title", item.getTitle()).
                        putExtra("poster_path", item.getPoster_path()).
                        putExtra("overview", item.getOverview()).
                        putExtra("release_date", item.getRelease_date()).
                        putExtra("vote_average", item.getVote_average()).
                        putExtra("movieid", item.getId());

                startActivity(intent);

            }
        });
    }


    //Method to show Error message
    public void showErrorMessage() {
        Log.d(TAG, "showErrorMessage: MSP- Show Error");

        ErrorMessageDisplay.setVisibility(View.VISIBLE);
        gridView.setVisibility(View.INVISIBLE);
        progressBar.setVisibility(View.INVISIBLE);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_layout, menu);
        return super.onCreateOptionsMenu(menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int selecteditem = item.getItemId();

        switch (selecteditem) {
            case R.id.menu_user_score:
                item.setChecked(true);
                ErrorMessageDisplay.setVisibility(View.INVISIBLE);
                FetchJSONPopularMovies();
                break;
            case R.id.menu_popularity:
                item.setChecked(true);
                ErrorMessageDisplay.setVisibility(View.INVISIBLE);
                FetchJSONTopRatedMovies();
                break;
            case R.id.menu_favourite:
                ErrorMessageDisplay.setVisibility(View.INVISIBLE);
                FetchFavouriteMovies();
            default:
                return true;

        }
        return super.onOptionsItemSelected(item);
    }

    public void FetchFavouriteMovies() {

        new AsyncTask<Void, Void, Cursor>() {
            @Override
            protected Cursor doInBackground(Void... voids) {


                Cursor cursor = getContentResolver().query(MoviesContract.MoviesFavourite.CONTENT_URI, null, null, null, null);
                Log.d(TAG, "doInBackground:: " + cursor);

                return cursor;
            }

            @Override
            protected void onPostExecute(Cursor cursor) {
                progressBar.setVisibility(View.INVISIBLE);

                super.onPostExecute(cursor);
                mMovies.clear();


                cursor.moveToFirst();
                if (cursor != null && cursor.moveToFirst()) {
                    do {
                        String mTitle = cursor.getString(MoviesContract.MoviesFavourite.COL_MOVIE_TITLE);
                        Log.d(TAG, "onPostExecute: ABCD: Movie Title: " + mTitle);

                        int mMovieID = cursor.getInt(MoviesContract.MoviesFavourite.COL_MOVIE_ID);
                        Log.d(TAG, "onPostExecute: ABCD: Movie ID: " + mMovieID);

                        String mSynopsis = cursor.getString(MoviesContract.MoviesFavourite.COL_MOVIE_SYNOPSIS);
                        Log.d(TAG, "onPostExecute: ABCD:: Movie Synopsis" + mSynopsis);

                        String mPosterPath = cursor.getString(MoviesContract.MoviesFavourite.COL_MOVIE_POSTER_PATH);
                        Log.d(TAG, "onPostExecute: ABCD: Movie Poster path" + mPosterPath);

                        String mRelease = cursor.getString(MoviesContract.MoviesFavourite.COL_MOVIE_DATE_OF_RELEASE);
                        Log.d(TAG, "onPostExecute: ABCD: Movie Date of release: " + mRelease);

                        Double mVoteAverage = cursor.getDouble(MoviesContract.MoviesFavourite.COL_MOVIE_VOTER_AVERAGE);

                        Log.d(TAG, "onPostExecute: ABCD: Voter Average:" + mVoteAverage);


                        Results results = new Results(mMovieID,mTitle, mRelease,  mPosterPath, mSynopsis, mVoteAverage);

                        Log.d(TAG, "onPostExecute: : Results" + results);

                        mMovies.add(results);
                    }


                    while (cursor.moveToNext());
                    Log.d(TAG, "onPostExecute:  mMovies:" + mMovies);

                }



                mGridAdapter = new GridViewAdapter(MainActivity.this, R.layout.layout_for_grid, mMovies);
                gridView.setAdapter(mGridAdapter);
                gridView.setVisibility(View.VISIBLE);



            }

            @Override
            protected void onPreExecute() {
                progressBar.setVisibility(View.VISIBLE);
                super.onPreExecute();
            }
        }.execute();
    }


    //Method to save instance upon change in Device orientation
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        Log.d(TAG, "onSaveInstanceState: MSP- On save Instance");

        if (mGridAdapter == null) {
            Log.d(TAG, "onSaveInstanceState: MSP- GridAdapter is null");
            if (ErrorMessageDisplay.isShown()) {
                Log.d(TAG, "onSaveInstanceState: MSP- Error message condition is Checked");

                String Error = ErrorMessageDisplay.getText().toString();

                outState.putString("error", Error);
            }
        }

        if (mGridAdapter != null) {
            Log.d(TAG, "onSaveInstanceState: MSP- Gridadapter is not null");
            ArrayList<Results> movies = mGridAdapter.getmGridData();
            if (movies != null && !movies.isEmpty()) {
                outState.putParcelableArrayList("Saved_movies", movies);
            }
        }

        super.onSaveInstanceState(outState);
    }

}
