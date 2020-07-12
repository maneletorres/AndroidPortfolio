package com.example.androidportfolio.ui.favoritetoys.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.androidportfolio.R;
import com.example.androidportfolio.databinding.FragmentFavoriteToysBinding;
import com.example.androidportfolio.ui.favoritetoys.adapter.ToyAdapter;
import com.example.androidportfolio.ui.favoritetoys.viewmodel.FavoriteToysViewModel;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

public class FavoriteToysFragment extends Fragment implements ToyAdapter.ToyAdapterOnClickHandler {

    // Variables:
    private FavoriteToysViewModel mViewModel;
    private FragmentFavoriteToysBinding mBinding;
    private ToyAdapter mToyAdapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // ViewModel:
        mViewModel = new ViewModelProvider(requireActivity()).get(FavoriteToysViewModel.class);

        // DataBinding:
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_favorite_toys, container, false);
        mBinding.setViewModel(mViewModel);

        setupToolbar();
        setupUI();
        setupObservers();

        mViewModel.start();

        return mBinding.getRoot();
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.favorite_toys_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.action_refresh) {
            mToyAdapter.setToyData(null);
            mViewModel.start();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(String toy) {
        Toast.makeText(getContext(), "Item clicked: # " + toy, Toast.LENGTH_LONG).show();
    }

    private void setupToolbar() {
        setHasOptionsMenu(true);
    }

    private void setupUI() {
        // RecyclerView configuration:
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        mBinding.toyRecyclerView.setLayoutManager(linearLayoutManager);
        mBinding.toyRecyclerView.setHasFixedSize(true);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(mBinding.toyRecyclerView.getContext(), linearLayoutManager.getOrientation());
        mBinding.toyRecyclerView.addItemDecoration(dividerItemDecoration);

        // ToyAdapter configuration:
        mToyAdapter = new ToyAdapter(this);
        mBinding.toyRecyclerView.setAdapter(mToyAdapter);
    }

    private void setupObservers() {
        mViewModel.loadingToysObservable().observe(getViewLifecycleOwner(), loadingStatus -> {
            switch (loadingStatus.getStatus()) {
                case SUCCESS:
                    showToyData(loadingStatus.getData());
                    break;
                case LOADING:
                    showProgressBar();
                    break;
                case ERROR:
                    showLoadToyErrorMessage();
                    break;
                default:
                    // TODO
            }
        });
    }

    private void showToyData(String[] toyData) {
        mBinding.toyProgressBar.setVisibility(GONE);
        mBinding.toyErrorTextView.setVisibility(GONE);
        mBinding.toyRecyclerView.setVisibility(VISIBLE);

        mToyAdapter.setToyData(toyData);
    }

    private void showProgressBar() {
        mBinding.toyRecyclerView.setVisibility(GONE);
        mBinding.toyErrorTextView.setVisibility(GONE);
        mBinding.toyProgressBar.setVisibility(VISIBLE);
    }

    private void showLoadToyErrorMessage() {
        mBinding.toyProgressBar.setVisibility(GONE);
        mBinding.toyRecyclerView.setVisibility(GONE);
        mBinding.toyErrorTextView.setVisibility(VISIBLE);
    }
}
