package com.example.androidportfolio.ui.sunshine.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ShareCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.example.androidportfolio.R;
import com.example.androidportfolio.databinding.FragmentSunshineDetailBinding;

public class SunshineDetailFragment extends Fragment {

    private static final String FORECAST_SHARE_HASHTAG = " #SunshineApp";
    private FragmentSunshineDetailBinding mBinding;
    private String mForecast;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // DataBinding:
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_sunshine_detail, container, false);

        Bundle bundle = getArguments();
        if (bundle != null) {
            String weather = bundle.getString(getString(R.string.clicked_forecast_key));
            setupUI(weather);
        } else closeOnError();

        return mBinding.getRoot();
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

    private void setupUI(String weather) {
        mForecast = weather;
        mBinding.tvDisplayWeather.setText(mForecast);
    }

    private void closeOnError() {
        Toast.makeText(requireContext(), getString(R.string.sunshine_detail_error_message), Toast.LENGTH_SHORT).show();
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
