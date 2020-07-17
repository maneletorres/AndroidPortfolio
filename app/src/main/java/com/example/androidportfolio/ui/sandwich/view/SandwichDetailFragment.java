package com.example.androidportfolio.ui.sandwich.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.androidportfolio.R;
import com.example.androidportfolio.data.model.Sandwich;
import com.example.androidportfolio.ui.sandwich.viewmodel.SandwichDetailViewModel;
import com.squareup.picasso.Picasso;

import java.util.List;

public class SandwichDetailFragment extends Fragment {

    private static final int DEFAULT_POSITION = -1;
    private SandwichDetailViewModel mViewModel;
    // private FragmentSandwichDetailBinding mBinding;

    private TextView originTv;
    private TextView descriptionTv;
    private TextView mIngredientsLabelTextView;
    private TextView mIngredientsTextView;
    private TextView mAlsoKnownAsLabelTextView;
    private TextView mAlsoKnownAsTextView;
    private ImageView imageIv;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sandwich_detail, container, false);

        // ViewModel:
        mViewModel = new ViewModelProvider(this).get(SandwichDetailViewModel.class);

        // DataBinding:
        /* mBinding = DataBindingUtil.setContentView(requireActivity(), R.layout.fragment_sandwich_detail);
        mBinding.setViewModel(mViewModel); */

        imageIv = view.findViewById(R.id.image_iv);
        originTv = view.findViewById(R.id.origin_tv);
        descriptionTv = view.findViewById(R.id.description_tv);
        mAlsoKnownAsLabelTextView = view.findViewById(R.id.also_known_tv);
        mAlsoKnownAsTextView = view.findViewById(R.id.also_known_lv);
        mIngredientsLabelTextView = view.findViewById(R.id.ingredients_label_tv);
        mIngredientsTextView = view.findViewById(R.id.ingredients_tv);

        setupObservers();

        return view; // return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        Bundle bundle = getArguments();
        if (bundle != null) {
            int position = bundle.getInt("clicked_sandwich_index");
            String name = bundle.getString("clicked_sandwich_name");
            if (position == DEFAULT_POSITION) {
                closeOnError();
            } else setupUI(position);
        }
    }

    private void setupUI(int position) {
        mViewModel.loadSandwichDetail(requireContext(), position);
    }

    private void setupObservers() {
        mViewModel.loadingSandwichDetailObservable().observe(getViewLifecycleOwner(), loadingStatus -> {
            switch (loadingStatus.getStatus()) {
                case SUCCESS:
                    showSandwichDetail(loadingStatus.getData());
                    break;
                case LOADING:
                    // TODO
                    break;
                case ERROR:
                    closeOnError();
                    break;
            }
        });

    }

    private void showSandwichDetail(Sandwich sandwich) {
        populateUI(sandwich);
        Picasso.get()
                .load(sandwich.getImage())
                .into(imageIv);
    }

    private void populateUI(Sandwich sandwich) {
        // TODO: do this in background using Coroutines
        String placeOfOrigin = sandwich.getPlaceOfOrigin();
        if (placeOfOrigin == null || placeOfOrigin.isEmpty())
            originTv.setText("Unknown origin");
        else originTv.setText(placeOfOrigin.concat("."));

        descriptionTv.setText(sandwich.getDescription());
        List<String> ingredients = sandwich.getIngredients();
        if (ingredients != null && !ingredients.isEmpty()) {
            mIngredientsTextView.setText(listFormatter(ingredients));
            mIngredientsLabelTextView.setVisibility(View.VISIBLE);
            mIngredientsTextView.setVisibility(View.VISIBLE);
        } else {
            mIngredientsLabelTextView.setVisibility(View.GONE);
            mIngredientsTextView.setVisibility(View.GONE);
        }

        List<String> alsoKnownAs = sandwich.getAlsoKnownAs();
        if (alsoKnownAs != null && !alsoKnownAs.isEmpty()) {
            mAlsoKnownAsTextView.setText(listFormatter(alsoKnownAs));
            mAlsoKnownAsLabelTextView.setVisibility(View.VISIBLE);
            mAlsoKnownAsTextView.setVisibility(View.VISIBLE);
        } else {
            mAlsoKnownAsLabelTextView.setVisibility(View.GONE);
            mAlsoKnownAsTextView.setVisibility(View.GONE);
        }
    }

    private void closeOnError() {
        // TODO
        //finish();
        Toast.makeText(requireContext(), "R.string.detail_error_message", Toast.LENGTH_SHORT).show();
    }

    private String listFormatter(List<String> list) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            String currentItemList = list.get(i);
            if (list.size() == 1) result.append(currentItemList).append(".");
            else if (i == 0) result.append(currentItemList);
            else if (i < list.size() - 1) result.append(", ").append(currentItemList.toLowerCase());
            else result.append(" and ").append(currentItemList.toLowerCase()).append(".");
        }

        return result.toString();
    }
}
