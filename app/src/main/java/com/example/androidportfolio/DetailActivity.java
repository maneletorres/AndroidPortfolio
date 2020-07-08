package com.example.androidportfolio;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ShareCompat;

public class DetailActivity extends AppCompatActivity {
    private static final String FORECAST_SHARE_HASHTAG = " #SunshineApp";

    private String mForecast;
    private TextView mWeatherDisplay;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        mWeatherDisplay = findViewById(R.id.tv_display_weather);

        Intent intent = getIntent();
        if (intent != null && intent.hasExtra(Intent.EXTRA_TEXT)) {
            mForecast = intent.getStringExtra(Intent.EXTRA_TEXT);
            mWeatherDisplay.setText(mForecast);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.details, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_share) {
            createShareForecastIntent();
        }
        return super.onOptionsItemSelected(item);
    }

    private void createShareForecastIntent() {
        Intent intent = ShareCompat.IntentBuilder.from(this)
        .setType("text/plain")
        .setText(mForecast + FORECAST_SHARE_HASHTAG)
        .setChooserTitle("Share Weather Details")
        .createChooserIntent();

        if (intent.resolveActivity(getPackageManager()) != null) startActivity(intent);
    }
}
