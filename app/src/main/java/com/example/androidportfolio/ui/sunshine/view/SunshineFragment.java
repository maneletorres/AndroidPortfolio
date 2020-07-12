package com.example.androidportfolio.ui.sunshine.view;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.androidportfolio.R;
import com.example.androidportfolio.databinding.FragmentSunshineBinding;
import com.example.androidportfolio.ui.DetailActivity;
import com.example.androidportfolio.ui.sunshine.adapter.ForecastAdapter;
import com.example.androidportfolio.ui.sunshine.viewmodel.SunshineViewModel;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

public class SunshineFragment extends Fragment implements ForecastAdapter.ForecastAdapterOnClickHandler {

    // Variables:
    private SunshineViewModel mViewModel;
    private FragmentSunshineBinding mBinding;
    private ForecastAdapter mForecastAdapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // ViewModel:
        mViewModel = new ViewModelProvider(requireActivity()).get(SunshineViewModel.class);

        // DataBinding:
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_sunshine, container, false);
        mBinding.setViewModel(mViewModel);

        setupToolbar();
        setupUI();
        setupObservers();

        mViewModel.loadWeatherData(getContext());

        return mBinding.getRoot();
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.sunshine_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_refresh) {
            mForecastAdapter.setWeatherData(null);
            mViewModel.loadWeatherData(getContext());
            return true;
        } else if (id == R.id.action_map) {
            openLocationMap();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(String weatherForDay) {
        Intent intent = new Intent(getContext(), DetailActivity.class);
        intent.putExtra(Intent.EXTRA_TEXT, weatherForDay);
        startActivity(intent);
    }

    private void setupToolbar() {
        setHasOptionsMenu(true);
    }

    private void setupUI() {
        // RecyclerView configuration:
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        mBinding.weatherRecyclerView.setLayoutManager(linearLayoutManager);
        mBinding.weatherRecyclerView.setHasFixedSize(true);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(mBinding.weatherRecyclerView.getContext(), linearLayoutManager.getOrientation());
        mBinding.weatherRecyclerView.addItemDecoration(dividerItemDecoration);

        // ForecastAdapter configuration:
        mForecastAdapter = new ForecastAdapter(this);
        mBinding.weatherRecyclerView.setAdapter(mForecastAdapter);
    }

    private void setupObservers() {
        mViewModel.loadingWeathersObservable().observe(getViewLifecycleOwner(), loadingStatus -> {
            switch (loadingStatus.getStatus()) {
                case SUCCESS:
                    showWeatherData(loadingStatus.getData());
                    break;
                case LOADING:
                    showProgressBar();
                    break;
                case ERROR:
                    showLoadWeatherErrorMessage();
                    break;
            }
        });
    }

    public void showWeatherData(String[] weatherData) {
        mBinding.pbLoadingIndicator.setVisibility(GONE);
        mBinding.tvErrorMessageDisplay.setVisibility(GONE);
        mBinding.weatherRecyclerView.setVisibility(VISIBLE);

        mForecastAdapter.setWeatherData(weatherData);
    }

    private void showProgressBar() {
        mBinding.weatherRecyclerView.setVisibility(GONE);
        mBinding.tvErrorMessageDisplay.setVisibility(GONE);
        mBinding.pbLoadingIndicator.setVisibility(VISIBLE);
    }

    private void showLoadWeatherErrorMessage() {
        mBinding.pbLoadingIndicator.setVisibility(GONE);
        mBinding.weatherRecyclerView.setVisibility(GONE);
        mBinding.tvErrorMessageDisplay.setVisibility(VISIBLE);
    }

    private void openLocationMap() {
        String addressString = "1600 Ampitheatre Parkway, CA";
        Uri gmmIntentUri = Uri.parse("geo:0,0?q=" + addressString);
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);

        if (mapIntent.resolveActivity(getActivity().getPackageManager()) != null) {
            mapIntent.setPackage("com.google.android.apps.maps");
            startActivity(mapIntent);
        }
    }
}
