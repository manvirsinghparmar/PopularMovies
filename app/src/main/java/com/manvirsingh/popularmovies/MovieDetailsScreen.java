package com.manvirsingh.popularmovies;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.manvirsingh.popularmovies.DataBase.MovieDBhelper;
import com.manvirsingh.popularmovies.DataBase.MoviesContract;
import com.manvirsingh.popularmovies.MovieAttributes.MovieAttributes;
import com.manvirsingh.popularmovies.MovieAttributes.Results;
import com.manvirsingh.popularmovies.MovieReviews.MovieReviewsAdapter;
import com.squareup.picasso.Picasso;

import static com.manvirsingh.popularmovies.DataBase.MoviesContract.MoviesFavourite.COLUMN_DATE_OF_RELEASE;
import static com.manvirsingh.popularmovies.DataBase.MoviesContract.MoviesFavourite.COLUMN_MOVIE_ID;
import static com.manvirsingh.popularmovies.DataBase.MoviesContract.MoviesFavourite.COLUMN_MOVIE_SYNOPSIS;
import static com.manvirsingh.popularmovies.DataBase.MoviesContract.MoviesFavourite.COLUMN_MOVIE_TITLE;
import static com.manvirsingh.popularmovies.DataBase.MoviesContract.MoviesFavourite.COLUMN_POSTER_PATH;
import static com.manvirsingh.popularmovies.DataBase.MoviesContract.MoviesFavourite.COLUMN_VOTER_AVERAGE;
import static com.manvirsingh.popularmovies.DataBase.MoviesContract.MoviesFavourite.CONTENT_URI;
import static com.manvirsingh.popularmovies.DataBase.MoviesContract.MoviesFavourite.TABLE_NAME;

public class MovieDetailsScreen extends AppCompatActivity {

    private static final String TAG = "MovieDetailsScreen";

    private TextView movie_title;
    private TextView movie_description;
    private TextView movie_Date_of_release;
    private TextView movie_popularity_rating;
    private ImageView movie_image;
    private TextView movie_vote_average;


    Button review_button;
    private ImageView Favourite;
    private Button Favourite_Button;
    private Button Remove_Favourite_button;

    private SQLiteDatabase mdb;


    //URL for movie Image
    private final String BASE_URL_IMAGE = "http://image.tmdb.org/t/p/";

    //Query Paramter
    private final static String QUERY_PARAM = "w500";

    private Results mResult;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Setting details screen layout
        setContentView(R.layout.movie_detail_ui);

        //References to Movie Details
        movie_title = (TextView) findViewById(R.id.movie_Title);
        movie_description = (TextView) findViewById(R.id.movie_Description);
        movie_popularity_rating = (TextView) findViewById(R.id.movie_Popular);
        movie_Date_of_release = (TextView) findViewById(R.id.Release_date);
        movie_image = (ImageView) findViewById(R.id.movie_Poster);
        movie_vote_average = (TextView) findViewById(R.id.movie_user_vote_average);

        review_button = (Button) findViewById(R.id.button_review);
        Favourite = (ImageView) findViewById(R.id.heart_favourite);
        Favourite_Button = (Button) findViewById(R.id.button2);
        Remove_Favourite_button = (Button) findViewById(R.id.button);

        //Intent to Receive Data from Main Activity
        Intent incomingIntent = getIntent();

        //Checking Incoming Intent
        if (incomingIntent != null) {

            //Variables to take data from Incoming Intent for Title,Description & Date of Release
            String new_movie_title = incomingIntent.getStringExtra("title");
            String new_movie_description = incomingIntent.getStringExtra("overview");
            String new_movie_Date_of_release = incomingIntent.getStringExtra("release_date");

            //Variables to take data from Incoming Intent for Popularity & Vote Average
            double new_movie_popularity_rating = incomingIntent.getDoubleExtra("popularity", 0);
            double new_movie_vote_average = incomingIntent.getDoubleExtra("vote_average", 0);

            String path = incomingIntent.getStringExtra("poster_path");

            //Uri to append a base path ahead of relative path
            Uri uri = Uri.parse(BASE_URL_IMAGE).buildUpon().appendEncodedPath(QUERY_PARAM).appendEncodedPath(path).build();

            //Setting the Views
            movie_title.setText(new_movie_title);
            movie_description.setText(new_movie_description);
            movie_Date_of_release.setText(new_movie_Date_of_release);
            movie_popularity_rating.setText(String.valueOf(new_movie_popularity_rating));
            movie_vote_average.setText(String.valueOf(new_movie_vote_average));


            //Loading of MOVIE POSTER into ImageView
            Picasso.get().load(uri).into(movie_image);


        }

        Favourite_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddtoFavourite();

            }
        });

        Remove_Favourite_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RemoveAsFavourite();

            }
        });
        updateFavoriteButtons();


    }

    //Method for sending the movie ID to Reviews class
    public void ViewReviews(View view) {

        Intent incomingIntent = getIntent();
        //Getting Movie ID from incoming Intent
        int movieid = incomingIntent.getIntExtra("movieid", 0);
        Log.d(TAG, "ViewReviews: MSP- Movie Id" + movieid);

        String movieID = String.valueOf(movieid);


        Log.d(TAG, "ViewReviews: MSP- movie ID" + movieID);
        //Sending movieID to Reviews
        Intent intent = new Intent(MovieDetailsScreen.this, Reviews.class);
        intent.putExtra("mID", movieID);

        startActivity(intent);
    }

    //Method for sending the movie ID to Trailer class
    public void PlayTrailer(View view) {
        Intent incomingIntent = getIntent();
        //Getting Movie ID from incoming Intent
        int movieid = incomingIntent.getIntExtra("movieid", 0);
        Log.d(TAG, "ViewReviews: XXX- Movie Id" + movieid);

        String movieID = String.valueOf(movieid);

        Log.d(TAG, "ViewReviews: MSP- movie ID" + movieID);
        //Sending movieID to Trailers
        Intent intent = new Intent(MovieDetailsScreen.this, Trailers.class);
        intent.putExtra("mID", movieID);

        startActivity(intent);

    }

    public void AddtoFavourite() {

        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                Log.d(TAG, "doInBackground: MSP- Add to Favourite Started");

                if (!movieisFavorite()) {
                    Log.d(TAG, "doInBackground: MSP-AddtoFavourite- Movie is Not Favourite and Data input has started");

                   Intent incomingIntent = getIntent();
                    //Getting Movie ID from incoming Intent
                   int movieid = incomingIntent.getIntExtra("movieid", 0);
                    Log.d(TAG, "doInBackground: ABCD");

                   String movieID = String.valueOf(movieid);
                   String movieTitle=incomingIntent.getStringExtra("title");
                   String movieRelease=incomingIntent.getStringExtra("release_date");
                   double dmovieVotes=incomingIntent.getDoubleExtra("vote_average",0);
                   String movieVotes=String.valueOf(dmovieVotes);
                    Log.d(TAG, "doInBackground: CBA: "+ movieVotes);
                   String moviesynopsis=incomingIntent.getStringExtra("overview");
                   String moviePoster=incomingIntent.getStringExtra("poster_path");

                    ContentValues cv = new ContentValues();
                    cv.put(COLUMN_MOVIE_ID,movieID);
                    cv.put(COLUMN_MOVIE_TITLE, movieTitle);
                    cv.put(COLUMN_DATE_OF_RELEASE, movieRelease);
                    cv.put(COLUMN_VOTER_AVERAGE, movieVotes);
                    cv.put(COLUMN_MOVIE_SYNOPSIS, moviesynopsis);
                    cv.put(COLUMN_POSTER_PATH, moviePoster);

                   Uri uri=getContentResolver().insert(MoviesContract.MoviesFavourite.CONTENT_URI, cv);

                    Log.d(TAG, "doInBackground: ABCD- "+ uri);

                    }

                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                Log.d(TAG, "onPostExecute: ABCD-AddtoFavourite-Update Favourite Button");

                updateFavoriteButtons();
                super.onPostExecute(aVoid);
            }
        }.execute();
    }

    public void RemoveAsFavourite() {

        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                Log.d(TAG, "doInBackground: MSP-");

                if (movieisFavorite()) {

                   Uri uri = CONTENT_URI;
                    Intent incomingIntent = getIntent();
                    //Getting Movie ID from incoming Intent
                    int movieid = incomingIntent.getIntExtra("movieid", 0);
                    Log.d(TAG, "ViewReviews: ABCD- Movie Id" + movieid);

                    String movieID = String.valueOf(movieid);



                    getContentResolver().delete(uri, COLUMN_MOVIE_ID + " = " + movieID, null);

                    Log.d(TAG, "doInBackground: ABCD: Remove button executed ");


                    }
                return null;
            }
            @Override
            protected void onPostExecute(Void aVoid) {
                updateFavoriteButtons();

                super.onPostExecute(aVoid);
            }
        }.execute();
    }

    private void updateFavoriteButtons() {

        new AsyncTask<Void, Void, Boolean>() {
            @Override
            protected Boolean doInBackground(Void... voids) {
                return movieisFavorite();
                
            }
            @Override
            protected void onPostExecute(Boolean aBoolean) {
                if (movieisFavorite()) {
                    Log.d(TAG, "onPostExecute: ABCD: UPDATE FAVOUITE BUTTON: REMOVE FROM FAVOURITE");

                    Favourite_Button.setVisibility(View.GONE);
                    Remove_Favourite_button.setVisibility(View.VISIBLE);

                } else {
                    Log.d(TAG, "onPostExecute: ABCD: ADD TO FAVOURITE BUTTON VISIBLE");
                    Favourite_Button.setVisibility(View.VISIBLE);
                    Remove_Favourite_button.setVisibility(View.GONE);

                }

                super.onPostExecute(aBoolean);
            }
        }.execute();


    }

    private boolean movieisFavorite() {


        Log.d(TAG, "movieisFavorite: MSP-movieisFavorite- Checking if movie is favourite ");

        Intent incomingIntent = getIntent();
        //Getting Movie ID from incoming Intent
        int movieid = incomingIntent.getIntExtra("movieid", 0);


        String movieID = String.valueOf(movieid);
        Log.d(TAG, "movieisFavorite: ABCD: Incoming Intent Movie ID : "+ movieID);



        String[]mSelectionArgs=new String[]{MoviesContract.MoviesFavourite.COLUMN_MOVIE_ID};
        String mSelection=" = ";
        Cursor movieCursor = getContentResolver().query(
                MoviesContract.MoviesFavourite.CONTENT_URI,
                mSelectionArgs,
                MoviesContract.MoviesFavourite.COLUMN_MOVIE_ID +mSelection+ movieID,
                null,
                null);

        Log.d(TAG, "movieisFavorite: ABCD cursor- "+ movieCursor);


        if (movieCursor != null && movieCursor.moveToFirst())
        {
            movieCursor.close();
            return true;
        } else {
            return false;
        }
    }


}
