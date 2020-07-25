package com.example.androidportfolio.ui.movies.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidportfolio.R;
import com.example.androidportfolio.data.model.Movie;
import com.example.androidportfolio.utilities.NetworkUtils;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {

    private MovieAdapterOnClickHandler mClickHandler;
    private List<Movie> mMovieData;

    public MovieAdapter(MovieAdapterOnClickHandler clickHandler) {
        mClickHandler = clickHandler;
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.movie_list_item, parent, false);
        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, int position) {
        Movie movie = mMovieData.get(position);
        Picasso.get()
                .load(NetworkUtils.IMAGE_TMDB_URL + movie.getPosterPath())
                .into(holder.mMovieImageView);
    }

    @Override
    public int getItemCount() {
        if (mMovieData == null) return 0;
        else return mMovieData.size();
    }

    public void setMoviesData(List<Movie> movies) {
        mMovieData = movies;
        notifyDataSetChanged();
    }

    public interface MovieAdapterOnClickHandler {
        void onMovieClick(Movie movie);
    }

    class MovieViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ImageView mMovieImageView;

        public MovieViewHolder(@NonNull View itemView) {
            super(itemView);
            mMovieImageView = itemView.findViewById(R.id.movie_cover_iv);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            Movie movie = mMovieData.get(getAdapterPosition());
            mClickHandler.onMovieClick(movie);
        }
    }
}
