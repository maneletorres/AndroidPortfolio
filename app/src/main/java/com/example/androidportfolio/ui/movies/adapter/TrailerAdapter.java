package com.example.androidportfolio.ui.movies.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidportfolio.R;
import com.example.androidportfolio.data.model.Trailer;

import java.util.List;

public class TrailerAdapter extends RecyclerView.Adapter<TrailerAdapter.TrailerViewHolder> {

    private TrailerAdapterOnClickHandler mClickHandler;
    private List<Trailer> mTrailerData;

    public TrailerAdapter(TrailerAdapterOnClickHandler clickHandler) {
        mClickHandler = clickHandler;
    }

    @NonNull
    @Override
    public TrailerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.trailer_list_item, parent, false);
        return new TrailerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TrailerViewHolder holder, int position) {
        Trailer trailer = mTrailerData.get(position);
        holder.mTrailerTextView.setText(trailer.getName());
    }

    @Override
    public int getItemCount() {
        if (mTrailerData == null) return 0;
        return mTrailerData.size();
    }

    public void setTrailersData(List<Trailer> trailers) {
        mTrailerData = trailers;
        notifyDataSetChanged();
    }

    public interface TrailerAdapterOnClickHandler {
        void onTrailerClick(Trailer trailer);
    }

    public class TrailerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView mTrailerTextView;

        public TrailerViewHolder(@NonNull View itemView) {
            super(itemView);
            mTrailerTextView = itemView.findViewById(R.id.trailer_tv);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            Trailer trailer = mTrailerData.get(getAdapterPosition());
            mClickHandler.onTrailerClick(trailer);
        }
    }
}
