package com.manvirsingh.popularmovies.DataBase;

import android.net.Uri;
import android.provider.BaseColumns;

public class MoviesContract {


    public static final String AUTHORITY = "com.manvirsingh.popularmovies";

    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + AUTHORITY);

    public static final String PATHS_MOVIES = "favourite_movies";

    public static final class MoviesFavourite implements BaseColumns {


        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATHS_MOVIES).build();

        public static final String TABLE_NAME = "favourite_movies";
        public static final String COLUMN_MOVIE_ID = "movie_id";
        public static final String COLUMN_MOVIE_TITLE = "movie_name";
        public static final String COLUMN_DATE_OF_RELEASE = "movie_release_date";
        public static final String COLUMN_POSTER_PATH = "movie_poster";
        public static final String COLUMN_MOVIE_SYNOPSIS = "movie_sysnopsis";
        public static final String COLUMN_VOTER_AVERAGE = "voter_average";


        public static final int COL_MOVIE_ID = 1;
        public static final int COL_MOVIE_TITLE = 2;
        public static final int COL_MOVIE_DATE_OF_RELEASE = 3;
        public static final int COL_MOVIE_POSTER_PATH = 4;
        public static final int COL_MOVIE_SYNOPSIS = 5;
        public static final int COL_MOVIE_VOTER_AVERAGE = 6;

    }


}
