package com.example.androidportfolio;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.androidportfolio.utilities.ToyBox;

public class FavoriteToysFragment extends Fragment {
    private TextView mToysListTextView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favorite_toys, container, false);

        mToysListTextView = view.findViewById(R.id.tv_toy_names);

        for (String toyName : ToyBox.getToyNames()) mToysListTextView.append(toyName + "\n");

        return view;
    }
}
