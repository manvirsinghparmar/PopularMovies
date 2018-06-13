package com.manvirsingh.popularmovies.DataBase;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;


import static com.manvirsingh.popularmovies.DataBase.MoviesContract.MoviesFavourite.CONTENT_URI;
import static com.manvirsingh.popularmovies.DataBase.MoviesContract.MoviesFavourite.TABLE_NAME;

public class MovieContentProvider extends ContentProvider {
    private MovieDBhelper movieDBhelper;

    public static final int TASKS = 100;
    public static final int TASK_MOVIE_ID=101;

    private static final UriMatcher sUriMatcher = buildUriMatcher();

    public static UriMatcher buildUriMatcher() {

        UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(MoviesContract.AUTHORITY, MoviesContract.PATHS_MOVIES, TASKS);

        return uriMatcher;

    }


    @Override
    public boolean onCreate() {
        Context context = getContext();
        movieDBhelper = new MovieDBhelper(context);
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {

        final SQLiteDatabase db = movieDBhelper.getReadableDatabase();
        int match = sUriMatcher.match(uri);
        Cursor cursor;

        switch (match) {

            case TASKS:
                cursor = db.query(TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder);
                break;

            case TASK_MOVIE_ID:
                String[]mSelectionArgs=new String[]{MoviesContract.MoviesFavourite.COLUMN_MOVIE_ID};
                String mSelection="=?";
                cursor=db.query(TABLE_NAME,projection,mSelection,mSelectionArgs,null,null,sortOrder);
                break;
            default:
                throw new UnsupportedOperationException("Not Yet Implemented" + uri);
        }

        if (getContext() != null) {
            cursor.setNotificationUri(getContext().getContentResolver(), uri);
        }
        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {

        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {

        final SQLiteDatabase db = movieDBhelper.getWritableDatabase();

        int match = sUriMatcher.match(uri);

        Uri returnuri;

        switch (match) {

            case TASKS:
                long id = db.insert(TABLE_NAME, null, values);
                if (id > 0) {

                    //Sucess
                    returnuri = ContentUris.withAppendedId(MoviesContract.MoviesFavourite.CONTENT_URI, id);
                } else {

                    throw new android.database.SQLException("Failed to insert Row into" + uri);
                }
                break;

            default:
                throw new UnsupportedOperationException("Not Yet Implemented");


        }
        if (getContext() != null) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return returnuri;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {

        final SQLiteDatabase db = movieDBhelper.getWritableDatabase();
        int match = sUriMatcher.match(uri);
        int moviedeleted;

        switch (match) {
            case TASKS:
                moviedeleted = db.delete(TABLE_NAME, selection, selectionArgs);
                break;
            default:
                throw new UnsupportedOperationException("Not Yet Implemented" + uri);
        }

        if (getContext() != null) {

            getContext().getContentResolver().notifyChange(uri, null);
        }


        return moviedeleted;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {

        final SQLiteDatabase db=movieDBhelper.getWritableDatabase();
        int match=sUriMatcher.match(uri);
        int rUpdated;

        switch (match){

            case TASKS:
                rUpdated=db.update(TABLE_NAME,values,selection,selectionArgs);
                break;

                default:
                    throw new UnsupportedOperationException("Not Yet Implemented" + uri);
        }
        if(getContext()!=null){

            getContext().getContentResolver().notifyChange(uri,null);
        }

        return rUpdated;
    }
}
