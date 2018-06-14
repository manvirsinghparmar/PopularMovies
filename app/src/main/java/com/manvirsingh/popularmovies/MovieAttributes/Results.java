package com.manvirsingh.popularmovies.MovieAttributes;

import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

    public class Results implements Parcelable {
    private static final String TAG = "Results";

    private String title;
    private String poster_path;
    private String overview;
    private String release_date;
    private double vote_average;
    private int id;

     public Results() {
    }

    public Results( int id,String title,String release_date,String poster_path,String overview, double vote_average ) {
        this.title = title;
        this.poster_path = poster_path;
        this.overview = overview;
        this.release_date = release_date;
        this.vote_average = vote_average;
        this.id = id;
    }

        public int getId() {
        return id;
    }



    public double getVote_average() {
        return vote_average;
    }



    public String getTitle() {
        return title;

    }




    public String getPoster_path() {
        return poster_path;
    }



    public String getOverview() {
        return overview;
    }


    public String getRelease_date() {
        return release_date;
    }




    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        Log.d(TAG, "writeToParcel: MSP- write to parcel ");
        dest.writeString(title);

        dest.writeString(poster_path);
        dest.writeString(overview);
        dest.writeString(release_date);
        dest.writeValue(vote_average);

    }

    public Results(Parcel in) {
        title = in.readString();

        poster_path = in.readString();
        overview = in.readString();
        release_date = in.readString();
        vote_average = in.readDouble();
    }

    public static final Creator<Results> CREATOR = new Creator<Results>() {
        @Override
        public Results createFromParcel(Parcel in) {
            return new Results(in);
        }

        @Override
        public Results[] newArray(int size) {
            return new Results[size];
        }
    };


}
