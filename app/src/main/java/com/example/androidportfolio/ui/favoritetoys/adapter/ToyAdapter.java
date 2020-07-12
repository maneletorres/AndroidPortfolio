package com.example.androidportfolio.ui.favoritetoys.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidportfolio.R;

public class ToyAdapter extends RecyclerView.Adapter<ToyAdapter.ToyAdapterViewHolder> {

    private final ToyAdapterOnClickHandler mClickHandler;
    private String[] mToyData;

    public ToyAdapter(ToyAdapterOnClickHandler clickHandler) {
        mClickHandler = clickHandler;
    }

    @NonNull
    @Override
    public ToyAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.favorite_toy_list_item, parent, false);
        return new ToyAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ToyAdapterViewHolder holder, int position) {
        String toy = mToyData[position];
        holder.mToyTextView.setText(toy);
    }

    @Override
    public int getItemCount() {
        if (mToyData == null) return 0;
        else return mToyData.length;
    }

    public void setToyData(String[] toyData) {
        mToyData = toyData;
        notifyDataSetChanged();
    }

    public interface ToyAdapterOnClickHandler {
        void onClick(String toy);
    }

    public class ToyAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public final TextView mToyTextView;

        public ToyAdapterViewHolder(View itemView) {
            super(itemView);
            mToyTextView = itemView.findViewById(R.id.tv_toy_data);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            String toy = mToyData[getAdapterPosition()];
            mClickHandler.onClick(toy);
        }
    }
}
