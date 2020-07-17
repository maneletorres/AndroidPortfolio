package com.example.androidportfolio.base;

import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.viewbinding.ViewBinding;

import com.example.androidportfolio.utils.Failure;

abstract class BaseActivity extends AppCompatActivity {

    protected ViewModel viewModel;

    protected ViewBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = getViewBinding();
        setContentView(binding.getRoot());
    }

    abstract ViewBinding getViewBinding();

    private void onFailure(Failure failure) {
        if (failure instanceof Failure.InvalidCredentials) {
            // Timber.e("Invalid credentials " + failure.message);
        } else if (failure instanceof Failure.ConnectionFailure) {
            // Timber.e("Connection failure " + failure.message);
        } else {
            // Timber.e("Unhandled failure " + failure.message);
        }
    }

    private void onLoading(Boolean loading) {
        // Do nothing
    }

    private void showDialog(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }
}
