package com.manvirsingh.popularmovies.MovieReviews;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.manvirsingh.popularmovies.R;

import java.util.ArrayList;
import java.util.zip.Inflater;

public class MovieReviewsAdapter extends RecyclerView.Adapter<MovieReviewsAdapter.ViewHolder> {
    private static final String TAG = "MovieReviewsAdapter";

    private ArrayList<MovieReview> mReview;
    private Context mContext;

    public MovieReviewsAdapter(ArrayList<MovieReview> mReview, Context mContext) {
        this.mReview = mReview;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d(TAG, "onCreateViewHolder: BKP- On CreateViewHolder");

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_review_recycler, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Log.d(TAG, "onBindViewHolder: BKP-onBindViewHolder");

        MovieReview movieReview = mReview.get(position);

        holder.setAuthor(movieReview.getAuthor());
        holder.setContent(movieReview.getContent());

    }

    @Override
    public int getItemCount() {
        Log.d(TAG, "getItemCount: BKP-getItemCount");

        return mReview.size();


    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView Author;
        private TextView Content;


        public ViewHolder(View itemView) {

            super(itemView);

            Log.d(TAG, "ViewHolder: BKP- ViewHolder");

            Author = itemView.findViewById(R.id.review_author_name);
            Content = itemView.findViewById(R.id.review_content_description);


        }

        public void setAuthor(String author) {
            Author.setText(author);
        }

        public void setContent(String content) {
            Content.setText(content);
        }
    }
}




