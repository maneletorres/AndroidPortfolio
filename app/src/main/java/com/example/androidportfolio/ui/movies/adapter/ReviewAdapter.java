package com.example.androidportfolio.ui.movies.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidportfolio.R;
import com.example.androidportfolio.data.model.Review;

import java.util.List;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ReviewViewHolder> {

    private ReviewAdapterOnClickHandler mClickHandler;
    private List<Review> mReviewData;

    public ReviewAdapter(ReviewAdapterOnClickHandler clickHandler) {
        mClickHandler = clickHandler;
    }

    @NonNull
    @Override
    public ReviewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.review_list_item, parent, false);
        return new ReviewViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReviewViewHolder holder, int position) {
        Review review = mReviewData.get(position);
        holder.mReviewTextView.setText(review.getContent());
    }

    @Override
    public int getItemCount() {
        if (mReviewData == null) return 0;
        else return mReviewData.size();
    }

    public void setReviewsData(List<Review> reviews) {
        mReviewData = reviews;
        notifyDataSetChanged();
    }

    public interface ReviewAdapterOnClickHandler {
        void onReviewClick(Review review);
    }

    public class ReviewViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView mReviewTextView;

        public ReviewViewHolder(@NonNull View itemView) {
            super(itemView);
            mReviewTextView = itemView.findViewById(R.id.review_tv);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            Review review = mReviewData.get(getAdapterPosition());
            mClickHandler.onReviewClick(review);
        }
    }
}
