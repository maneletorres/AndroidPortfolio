package com.example.androidportfolio.ui.sandwich.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.androidportfolio.R;
import com.example.androidportfolio.databinding.FragmentSandwichBinding;
import com.example.androidportfolio.ui.MainActivity;
import com.example.androidportfolio.ui.sandwich.adapter.SandwichAdapter;
import com.example.androidportfolio.ui.sandwich.viewmodel.SandwichViewModel;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

public class SandwichFragment extends Fragment implements SandwichAdapter.SandwichAdapterOnClickHandler {

    private SandwichViewModel mViewModel;
    private FragmentSandwichBinding mBinding;
    private SandwichAdapter mSandwichAdapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // ViewModel:
        mViewModel = new ViewModelProvider(requireActivity()).get(SandwichViewModel.class);

        // DataBinding:
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_sandwich, container, false);
        mBinding.setViewModel(mViewModel);

        setupToolbar();
        setupUI();
        setupObservers();

        mViewModel.start(getContext());

        return mBinding.getRoot();
    }

    @Override
    public void onClick(int clickedSandwichIndex, String clickedSandwichName) {
        // NavigationUI:
        /*SandwichFragmentDirections.OpenSandwichDetail action = SandwichFragmentDirections.openSandwichDetail(clickedSandwichName);
        action.setSandwichListPosition(clickedSandwichIndex);
        Navigation.findNavController(view).navigate(action);*/

        // Navigation legacy:
        Bundle bundle = new Bundle();
        bundle.putInt("clicked_sandwich_index", clickedSandwichIndex);
        bundle.putString("clicked_sandwich_name", clickedSandwichName);

        Fragment fragment = new SandwichDetailFragment();
        fragment.setArguments(bundle);
        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.main_linear_layout, fragment).addToBackStack(getString(R.string.sandwiches_fragment_title)).commit();
    }

    private void setupToolbar() {
        MainActivity mainActivity = (MainActivity) getActivity();
        if (mainActivity != null)
            mainActivity.setToolbarTitle(getString(R.string.sandwiches_fragment_title));
    }

    private void setupUI() {
        // RecyclerView configuration:
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        mBinding.sandwichRecyclerview.setLayoutManager(linearLayoutManager);
        mBinding.sandwichRecyclerview.setHasFixedSize(true);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(mBinding.sandwichRecyclerview.getContext(), linearLayoutManager.getOrientation());
        mBinding.sandwichRecyclerview.addItemDecoration(dividerItemDecoration);

        // ToyAdapter configuration:
        mSandwichAdapter = new SandwichAdapter(this);
        mBinding.sandwichRecyclerview.setAdapter(mSandwichAdapter);
    }

    private void setupObservers() {
        mViewModel.loadingSandwichesObservable().observe(getViewLifecycleOwner(), loadingStatus -> {
            switch (loadingStatus.getStatus()) {
                case SUCCESS:
                    showSandwichData(loadingStatus.getData());
                    break;
                case LOADING:
                    showProgressBar();
                    break;
                case ERROR:
                    showLoadSandwichErrorMessage();
                    break;
            }
        });
    }

    private void showSandwichData(String[] sandwichData) {
        mBinding.sandwichProgressBar.setVisibility(GONE);
        mBinding.tvSandwichErrorMessage.setVisibility(GONE);
        mBinding.sandwichRecyclerview.setVisibility(VISIBLE);
        mSandwichAdapter.setSandwichData(sandwichData);
    }

    private void showProgressBar() {
        mBinding.sandwichRecyclerview.setVisibility(GONE);
        mBinding.tvSandwichErrorMessage.setVisibility(GONE);
        mBinding.sandwichProgressBar.setVisibility(VISIBLE);
    }

    private void showLoadSandwichErrorMessage() {
        mBinding.sandwichProgressBar.setVisibility(GONE);
        mBinding.sandwichRecyclerview.setVisibility(GONE);
        mBinding.tvSandwichErrorMessage.setVisibility(VISIBLE);
    }
}
