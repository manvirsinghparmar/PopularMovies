package com.manvirsingh.popularmovies;

import android.annotation.SuppressLint;
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

import com.manvirsingh.popularmovies.DataBase.MoviesContract;
import com.manvirsingh.popularmovies.MovieAttributes.MovieAttributes;
import com.manvirsingh.popularmovies.MovieAttributes.Results;

import java.util.ArrayList;

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
    private TextView Emptyfavoritemovies;
    private Results results = new Results();
    private ArrayList<Results> mMovies = new ArrayList<>();

    MovieDetailsScreen movieDetailsScreen = new MovieDetailsScreen();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate: MSP- On create Started");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        gridView = (GridView) findViewById(R.id.main_grid_View);
        progressBar = (ProgressBar) findViewById(R.id.main_progressbar);
        ErrorMessageDisplay = (TextView) findViewById(R.id.error_message);
        Emptyfavoritemovies = (TextView) findViewById(R.id.Favorite_movie_empty);


        //Checking Saved Instance state during onCreate
        if (savedInstanceState == null) {

            FetchJSONPopularMovies();
        } else {


            if ((savedInstanceState.containsKey("Saved_movies")) && isNetworkConnected(this)) {


                ArrayList<Results> movies = savedInstanceState.getParcelableArrayList("Saved_movies");


                mGridAdapter = new GridViewAdapter(this, R.layout.layout_for_grid, movies);

                gridView.setAdapter(mGridAdapter);

                //Method call for ON click respond
                senddataIntent();


            } else {

                if (savedInstanceState.containsKey("error") || !isNetworkConnected(this)) {

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
        Emptyfavoritemovies.setVisibility(View.GONE);

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

                mGridAdapter = new GridViewAdapter(MainActivity.this, R.layout.layout_for_grid, list);
                gridView.setAdapter(mGridAdapter);
                gridView.setVisibility(View.VISIBLE);

                //Method call for ON click respond
                senddataIntent();

            }

            @Override
            public void onFailure(Call<MovieAttributes> call, Throwable t) {

                showErrorMessage();

                Toast.makeText(MainActivity.this, "Something Went Wrong", Toast.LENGTH_LONG).show();

            }
        });
    }


    //Method to fetch JSON data from moviedb for Top Rated Movies
    public void FetchJSONTopRatedMovies() {
        Emptyfavoritemovies.setVisibility(View.GONE);

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

                mGridAdapter = new GridViewAdapter(MainActivity.this, R.layout.layout_for_grid, list);
                gridView.setAdapter(mGridAdapter);
                gridView.setVisibility(View.VISIBLE);

                //Method call for ON click response-Detail Activity
                senddataIntent();
            }

            @Override
            public void onFailure(Call<MovieAttributes> call, Throwable t) {
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

        final int selecteditem = item.getItemId();

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
                break;

            default:
                return true;

        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressLint("StaticFieldLeak")
    public void FetchFavouriteMovies() {
        Log.d(TAG, "FetchFavouriteMovies: MSP: FetchFavouriteMovies ");

        new AsyncTask<Void, Void, Cursor>() {
            @Override
            protected Cursor doInBackground(Void... voids) {


                Cursor cursor = getContentResolver().query(MoviesContract.MoviesFavourite.CONTENT_URI, null, null, null, null);
                Log.d(TAG, "doInBackground:: " + cursor);

                mMovies.clear();
                if (cursor != null && cursor.moveToFirst()) {
                    do {
                        String mTitle = cursor.getString(MoviesContract.MoviesFavourite.COL_MOVIE_TITLE);
                        int mMovieID = cursor.getInt(MoviesContract.MoviesFavourite.COL_MOVIE_ID);
                        String mSynopsis = cursor.getString(MoviesContract.MoviesFavourite.COL_MOVIE_SYNOPSIS);
                        String mPosterPath = cursor.getString(MoviesContract.MoviesFavourite.COL_MOVIE_POSTER_PATH);
                        String mRelease = cursor.getString(MoviesContract.MoviesFavourite.COL_MOVIE_DATE_OF_RELEASE);
                        Double mVoteAverage = cursor.getDouble(MoviesContract.MoviesFavourite.COL_MOVIE_VOTER_AVERAGE);

                        Results results = new Results(mMovieID, mTitle, mRelease, mPosterPath, mSynopsis, mVoteAverage);

                        mMovies.add(results);
                    }

                    while (cursor.moveToNext());
                }
                return cursor;
            }


            @Override
            protected void onPostExecute(Cursor cursor) {
                progressBar.setVisibility(View.INVISIBLE);
                super.onPostExecute(cursor);

                Emptyfavoritemovies.setVisibility(View.GONE);
                mGridAdapter = new GridViewAdapter(MainActivity.this, R.layout.layout_for_grid, mMovies);
                gridView.setAdapter(mGridAdapter);
                senddataIntent();
                gridView.setVisibility(View.VISIBLE);


                mGridAdapter.notifyDataSetChanged();

                if (mGridAdapter.getCount() == 0) {
                    Log.d(TAG, "onPostExecute: MSP: GridAdapter is zero");
                    Emptyfavoritemovies.setVisibility(View.VISIBLE);
                    return;

                }
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
            if (ErrorMessageDisplay.isShown()) {
                Log.d(TAG, "onSaveInstanceState: MSP- Error message condition is Checked");
                String Error = ErrorMessageDisplay.getText().toString();
                outState.putString("error", Error);
            }
        }
        if (mGridAdapter != null) {
            //Log.d(TAG, "onSaveInstanceState: HUDSON- Gridadapter is not null" + mGridAdapter);
            ArrayList<Results> movies = mGridAdapter.getmGridData();

            Log.d(TAG, "onSaveInstanceState: MSP: " + movies);
            if (movies != null && !movies.isEmpty()) {
                outState.putParcelableArrayList("Saved_movies", movies);
            }
        }
        super.onSaveInstanceState(outState);
    }


}
