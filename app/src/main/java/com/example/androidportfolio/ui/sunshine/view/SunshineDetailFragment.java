package com.example.androidportfolio.ui.sunshine.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ShareCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.androidportfolio.R;
import com.example.androidportfolio.ui.sunshine.viewmodel.SunshineDetailViewModel;

public class SunshineDetailFragment extends Fragment {

    private static final String FORECAST_SHARE_HASHTAG = " #SunshineApp";
    //private FragmentSunshineDetailBinding mBinding;
    private TextView mTextView;
    private String mForecast;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sunshine_detail, container, false);

        // ViewModel:
        SunshineDetailViewModel viewModel = new ViewModelProvider(this).get(SunshineDetailViewModel.class);

        // DataBinding:
        /*mBinding = DataBindingUtil.setContentView(requireActivity(), R.layout.fragment_sunshine_detail);
        mBinding.setViewModel(viewModel);*/

        mTextView = view.findViewById(R.id.tv_display_weather);

        setupToolbar();
        setupObservers();

        return view; //return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        Bundle bundle = getArguments();
        if (bundle != null) {
            String weather = bundle.getString("weatherForDay");
            if (weather == null) {
                //closeOnError();
            } else setupUI(weather);
        }
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.sunshine_details_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_share) {
            createShareForecastIntent();
        }
        return super.onOptionsItemSelected(item);
    }

    private void setupToolbar() {
        setHasOptionsMenu(true);
    }

    private void setupUI(String weather) {
        mForecast = weather;
        mTextView.setText(mForecast);
    }

    private void setupObservers() {
        // TODO
    }

    private void createShareForecastIntent() {
        Intent intent = ShareCompat.IntentBuilder.from(requireActivity())
                .setType("text/plain")
                .setText(mForecast + FORECAST_SHARE_HASHTAG)
                .setChooserTitle("Share Weather Details")
                .createChooserIntent();

        if (intent.resolveActivity(requireActivity().getPackageManager()) != null)
            startActivity(intent);
    }
}
