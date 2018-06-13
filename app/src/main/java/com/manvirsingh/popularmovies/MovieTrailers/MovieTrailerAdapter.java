package com.manvirsingh.popularmovies.MovieTrailers;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.manvirsingh.popularmovies.R;
import com.manvirsingh.popularmovies.Trailers;


import java.util.ArrayList;

public class MovieTrailerAdapter extends RecyclerView.Adapter<MovieTrailerAdapter.TrailerViewHolder> {


    private ArrayList<MovieTrailer> mTrailer;
    private Context mContext;

    public MovieTrailerAdapter(ArrayList<MovieTrailer> mTrailer, Context mContext) {
        this.mTrailer = mTrailer;
        this.mContext = mContext;
    }


    @NonNull
    @Override
    public TrailerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_trailer_recycler, parent, false);
        TrailerViewHolder trailerViewHolder = new TrailerViewHolder(view);

        return trailerViewHolder;
    }


    @Override
    public void onBindViewHolder(@NonNull final TrailerViewHolder holder, final int position) {

        final MovieTrailer movieTrailer = mTrailer.get(position);

        final String url = "https://www.youtube.com/watch?v=" + movieTrailer.getKey();

        holder.setName(movieTrailer.getName());
        holder.setType(movieTrailer.getType());
        holder.setKey(movieTrailer.getKey());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                holder.webView.loadUrl(url);
            }
        });

    }

    @Override
    public int getItemCount() {
        return mTrailer.size();
    }

    public class TrailerViewHolder extends RecyclerView.ViewHolder {

        private TextView Name;
        private TextView Type;
        private WebView webView;
        private TextView Key;


        public TrailerViewHolder(View itemView) {
            super(itemView);

            Name = (TextView) itemView.findViewById(R.id.trailer_name);
            Type = (TextView) itemView.findViewById(R.id.trailer_type_of_video);
            webView = (WebView) itemView.findViewById(R.id.trailer_web_view);
            webView.setWebChromeClient(new WebChromeClient());
            webView.getSettings().setJavaScriptEnabled(true);
            Key = (TextView) itemView.findViewById(R.id.trailer_key);

        }

        public void setName(String name) {
            Name.setText(name);
        }

        public void setType(String type) {
            Type.setText(type);
        }

        public void setKey(String key) {
            Key.setText(key);


        }

    }
}
