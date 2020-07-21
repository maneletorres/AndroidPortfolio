package com.example.androidportfolio.ui.sandwich.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.androidportfolio.R;
import com.example.androidportfolio.data.model.Sandwich;
import com.example.androidportfolio.databinding.FragmentSandwichDetailBinding;
import com.example.androidportfolio.ui.MainActivity;
import com.example.androidportfolio.ui.sandwich.viewmodel.SandwichDetailViewModel;
import com.squareup.picasso.Picasso;

import java.util.List;

public class SandwichDetailFragment extends Fragment {

    private SandwichDetailViewModel mViewModel;
    private FragmentSandwichDetailBinding mBinding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // ViewModel:
        mViewModel = new ViewModelProvider(this).get(SandwichDetailViewModel.class);

        // DataBinding:
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_sandwich_detail, container, false);
        mBinding.setViewModel(mViewModel);

        Bundle bundle = getArguments();
        if (bundle != null) {
            int sandwichIndex = bundle.getInt("clicked_sandwich_index");
            String sandwichName = bundle.getString("clicked_sandwich_name");

            setupToolbar(sandwichName);
            setupUI(sandwichIndex);
        } else closeOnError();

        setupObservers();

        return mBinding.getRoot();
    }

    private void setupToolbar(String sandwichName) {
        MainActivity mainActivity = (MainActivity) getActivity();
        if (mainActivity != null) mainActivity.setToolbarTitle(sandwichName);
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
        setupUI(sandwich);
        Picasso.get()
                .load(sandwich.getImage())
                .into(mBinding.imageIv);
    }

    private void setupUI(Sandwich sandwich) {
        String placeOfOrigin = sandwich.getPlaceOfOrigin();
        if (placeOfOrigin == null || placeOfOrigin.isEmpty())
            mBinding.originTv.setText(getString(R.string.sandwich_detail_unknown_origin));
        else mBinding.originTv.setText(placeOfOrigin.concat("."));

        List<String> alsoKnownAs = sandwich.getAlsoKnownAs();
        if (alsoKnownAs != null && !alsoKnownAs.isEmpty()) {
            mBinding.alsoKnownTv.setText(listFormatter(alsoKnownAs));
            mBinding.alsoKnownLv.setVisibility(View.VISIBLE);
            mBinding.alsoKnownTv.setVisibility(View.VISIBLE);
        } else {
            mBinding.alsoKnownLv.setVisibility(View.GONE);
            mBinding.alsoKnownTv.setVisibility(View.GONE);
        }

        List<String> ingredients = sandwich.getIngredients();
        if (ingredients != null && !ingredients.isEmpty()) {
            mBinding.ingredientsTv.setText(listFormatter(ingredients));
            mBinding.ingredientsLabelTv.setVisibility(View.VISIBLE);
            mBinding.ingredientsTv.setVisibility(View.VISIBLE);
        } else {
            mBinding.ingredientsLabelTv.setVisibility(View.GONE);
            mBinding.ingredientsTv.setVisibility(View.GONE);
        }

        mBinding.descriptionTv.setText(sandwich.getDescription());
    }

    private void closeOnError() {
        Toast.makeText(requireContext(), getString(R.string.sandwich_detail_error_message), Toast.LENGTH_SHORT).show();
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
