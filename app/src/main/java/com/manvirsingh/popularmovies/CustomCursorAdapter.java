package com.manvirsingh.popularmovies;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.manvirsingh.popularmovies.DataBase.MoviesContract;
import com.squareup.picasso.Picasso;

public class CustomCursorAdapter extends RecyclerView.Adapter<CustomCursorAdapter.FavouriteViewHolder>{

    private final String BASE_URL_IMAGE = "http://image.tmdb.org/t/p/";

    private final static String QUERY_PARAM = "w185";

    private Cursor mCursor;
    private Context mContext;

    public CustomCursorAdapter(Cursor mCursor, Context mContext) {
        this.mCursor = mCursor;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public FavouriteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_for_grid,parent,false);

        FavouriteViewHolder favouriteViewHolder= new FavouriteViewHolder(view);

        return favouriteViewHolder;

    }

    @Override
    public void onBindViewHolder(@NonNull FavouriteViewHolder holder, int position) {

        int vote_average=mCursor.getColumnIndex(MoviesContract.MoviesFavourite.COLUMN_VOTER_AVERAGE);
        int movie_poster_path=mCursor.getColumnIndex(MoviesContract.MoviesFavourite.COLUMN_POSTER_PATH);


        String final_movie_name=String.valueOf(vote_average);
        String final_movie_poster_path=String.valueOf(movie_poster_path);

        mCursor.moveToPosition(position);

        holder.vote_average.setText(final_movie_name);

        Uri uri=Uri.parse(BASE_URL_IMAGE).buildUpon().appendEncodedPath(QUERY_PARAM).appendEncodedPath(final_movie_poster_path).build();

        Picasso.get().load(uri).placeholder(R.drawable.placeholder).into(holder.imageView);

        mCursor.close();

            }

    @Override
    public int getItemCount() {
        return mCursor.getCount();
    }

    public class FavouriteViewHolder extends RecyclerView.ViewHolder {


    TextView vote_average;
    ImageView imageView;


    public FavouriteViewHolder(View itemView) {



        super(itemView);
        vote_average=(TextView) itemView.findViewById(R.id.vote_average_grid_view);
        imageView=(ImageView)itemView.findViewById(R.id.image_view_grid);

    }
}

}
