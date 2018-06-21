package com.manvirsingh.popularmovies;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.manvirsingh.popularmovies.DataBase.MoviesContract;
import com.manvirsingh.popularmovies.MovieAttributes.Results;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class GridViewAdapter extends ArrayAdapter<Results> {
    private static final String TAG = "GridViewAdapter";

    private Context mContext;
    private int layoutResourceId;
    private ArrayList<Results> mGridData;


    private final String BASE_URL_IMAGE = "http://image.tmdb.org/t/p/";

    private final static String QUERY_PARAM = "w185";


    public GridViewAdapter(Context mContext, int layoutResourceId, ArrayList<Results> mGridData) {
        super(mContext, R.layout.layout_for_grid, mGridData);
        Log.d(TAG, "GridViewAdapter: ABC: Grid view Adapter");

        this.mContext = mContext;
        this.layoutResourceId = layoutResourceId;
        this.mGridData = mGridData;
    }


    @Override
    public int getCount() {
        Log.d(TAG, "getCount: ABC: GET COUNT");

        return mGridData.size();
    }


    @Override
    public long getItemId(int i) {
        return 0;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        ViewHolder holder;

        if (view == null) {
            Log.d(TAG, "getView: ABC- Inflating Layout");
            LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
            view = inflater.inflate(layoutResourceId, parent, false);
            holder = new ViewHolder();


            //holder.titleTextView = (TextView) row.findViewById(R.id.Title_view_grid);

            holder.imageView = (ImageView) view.findViewById(R.id.image_view_grid);
            holder.vote_average = (TextView) view.findViewById(R.id.vote_average_grid_view);
            view.setTag(holder);


        } else {
            Log.d(TAG, "getView: ABC- Else statement of Layout Inflation");
            holder = (ViewHolder) view.getTag();
        }

        Results item = mGridData.get(position);
        Log.d(TAG, "getView: ABC:" + item);
        //holder.titleTextView.setText(item.getTitle());
        holder.vote_average.setText(String.valueOf(item.getVote_average()));

        //Building uri for Picasso
        Uri uri = Uri.parse(BASE_URL_IMAGE).buildUpon().appendEncodedPath(QUERY_PARAM).appendEncodedPath(item.getPoster_path()).build();

        Picasso.get().load(uri).placeholder(R.drawable.placeholder).into(holder.imageView);
        Log.d(TAG, "getView: ABC: Picasso");
        return view;


    }


    public ArrayList<Results> getmGridData() {
        Log.d(TAG, "getmGridData: ABCDE: Array Method Executed");
        return mGridData;
    }

    static class ViewHolder {
        //TextView titleTextView;
        TextView vote_average;
        ImageView imageView;

    }

}
