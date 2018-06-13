package com.manvirsingh.popularmovies.DataBase;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MovieDBhelper extends SQLiteOpenHelper {

    private final static String DATABASE_NAME = "movies.db";
    private final static int DATABASE_VERSION = 3;

    public MovieDBhelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        final String SQL_MOVIES_INFO_TABLE = " CREATE TABLE " +
                MoviesContract.MoviesFavourite.TABLE_NAME + " (" +
                MoviesContract.MoviesFavourite._ID + " INTEGER PRIMARY KEY," +
                MoviesContract.MoviesFavourite.COLUMN_MOVIE_ID + " INTEGER NOT NULL, " +
                MoviesContract.MoviesFavourite.COLUMN_MOVIE_TITLE + " TEXT NOT NULL, " +
                MoviesContract.MoviesFavourite.COLUMN_DATE_OF_RELEASE + " TEXT NOT NULL, " +
                MoviesContract.MoviesFavourite.COLUMN_POSTER_PATH + " TEXT NOT NULL, " +
                MoviesContract.MoviesFavourite.COLUMN_MOVIE_SYNOPSIS + " TEXT NOT NULL, " +
                MoviesContract.MoviesFavourite.COLUMN_VOTER_AVERAGE + " TEXT NOT NULL " +

                " );";

        db.execSQL(SQL_MOVIES_INFO_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL(" DROP TABLE IF EXISTS " + MoviesContract.MoviesFavourite.TABLE_NAME);
        onCreate(db);

    }
}
