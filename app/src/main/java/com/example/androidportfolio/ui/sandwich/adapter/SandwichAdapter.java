package com.example.androidportfolio.ui.sandwich.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidportfolio.R;

public class SandwichAdapter extends RecyclerView.Adapter<SandwichAdapter.SandwichViewHolder> {

    private final SandwichAdapterOnClickHandler mClickHandler;
    private String[] mSandwichData;

    public SandwichAdapter(SandwichAdapterOnClickHandler clickHandler) {
        mClickHandler = clickHandler;
    }

    @NonNull
    @Override
    public SandwichAdapter.SandwichViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.sandwich_list_item, parent, false);

        return new SandwichViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SandwichAdapter.SandwichViewHolder holder, int position) {
        String sandwich = mSandwichData[position];
        holder.mSandwichTextView.setText(sandwich);
    }

    @Override
    public int getItemCount() {
        if (mSandwichData == null) return 0;
        else return mSandwichData.length;
    }

    public void setSandwichData(String[] sandwichData) {
        mSandwichData = sandwichData;
        notifyDataSetChanged();
    }

    public interface SandwichAdapterOnClickHandler {
        void onClick(int clickedSandwichIndex, String clickedSandwichName);
    }

    class SandwichViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public final TextView mSandwichTextView;

        public SandwichViewHolder(@NonNull final View itemView) {
            super(itemView);
            mSandwichTextView = itemView.findViewById(R.id.tv_item_sandwich);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            mClickHandler.onClick(getAdapterPosition(), mSandwichData[getAdapterPosition()]);
        }
    }
}
