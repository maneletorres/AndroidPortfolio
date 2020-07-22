package com.example.androidportfolio.ui.movies.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidportfolio.R;
import com.example.androidportfolio.data.model.Movie;

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
        holder.mMovieTextView.setText(movie.getTitle());
    }

    @Override
    public int getItemCount() {
        if (mMovieData == null) return 0;
        else return mMovieData.size();
    }

    public void setMoviesData(List<Movie> movies) {
        mMovieData = movies;
    }

    public interface MovieAdapterOnClickHandler {
        void onClick(Movie movie);
    }

    class MovieViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView mMovieTextView;

        public MovieViewHolder(@NonNull View itemView) {
            super(itemView);
            mMovieTextView = itemView.findViewById(R.id.tv_movie_data);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            Movie movie = mMovieData.get(getAdapterPosition());
            mClickHandler.onClick(movie);
        }
    }
}
