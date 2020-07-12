package com.example.androidportfolio.ui.sandwitch.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.androidportfolio.R;

public class SandwichFragment extends Fragment  {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_intents_practice, container, false);
        setHasOptionsMenu(true);

        return view;
    }
}
