package com.example.androidportfolio.ui.sunshine.view;

import android.app.Activity;
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
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.androidportfolio.R;
import com.example.androidportfolio.databinding.FragmentSunshineBinding;
import com.example.androidportfolio.ui.sunshine.adapter.ForecastAdapter;
import com.example.androidportfolio.ui.sunshine.viewmodel.SunshineViewModel;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

public class SunshineFragment extends Fragment implements ForecastAdapter.ForecastAdapterOnClickHandler {

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
        // NavigationUI:
        /*SunshineFragmentDirections.OpenSunshineDetail action = SunshineFragmentDirections.openSunshineDetail(weatherForDay);
        action.setSunshineListElement(weatherForDay);
        Navigation.findNavController(view).navigate(action);*/

        // Navigation legacy:
        Bundle bundle = new Bundle();
        bundle.putString("weatherForDay", weatherForDay);

        Fragment fragment = new SunshineDetailFragment();
        fragment.setArguments(bundle);
        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.main_linear_layout, fragment).addToBackStack(getString(R.string.sunshine_fragment_title)).commit();
    }

    private void setupToolbar() {
        setHasOptionsMenu(true);
    }

    private void setupUI() {
        // RecyclerView configuration:
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        mBinding.weatherRecyclerview.setLayoutManager(linearLayoutManager);
        mBinding.weatherRecyclerview.setHasFixedSize(true);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(mBinding.weatherRecyclerview.getContext(), linearLayoutManager.getOrientation());
        mBinding.weatherRecyclerview.addItemDecoration(dividerItemDecoration);

        // ForecastAdapter configuration:
        mForecastAdapter = new ForecastAdapter(this);
        mBinding.weatherRecyclerview.setAdapter(mForecastAdapter);
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
        mBinding.sunshineProgressBar.setVisibility(GONE);
        mBinding.tvSunshineErrorMessage.setVisibility(GONE);
        mBinding.weatherRecyclerview.setVisibility(VISIBLE);

        mForecastAdapter.setWeatherData(weatherData);
    }

    private void showProgressBar() {
        mBinding.weatherRecyclerview.setVisibility(GONE);
        mBinding.tvSunshineErrorMessage.setVisibility(GONE);
        mBinding.sunshineProgressBar.setVisibility(VISIBLE);
    }

    private void showLoadWeatherErrorMessage() {
        mBinding.sunshineProgressBar.setVisibility(GONE);
        mBinding.weatherRecyclerview.setVisibility(GONE);
        mBinding.tvSunshineErrorMessage.setVisibility(VISIBLE);
    }

    private void openLocationMap() {
        String addressString = "1600 Ampitheatre Parkway, CA";
        Uri gmmIntentUri = Uri.parse("geo:0,0?q=" + addressString);
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);

        Activity activity = getActivity();
        if (activity != null && mapIntent.resolveActivity(activity.getPackageManager()) != null) {
            mapIntent.setPackage("com.google.android.apps.maps");
            startActivity(mapIntent);
        }
    }
}
