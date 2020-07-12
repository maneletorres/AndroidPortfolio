package com.example.androidportfolio.ui.intents.view;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.core.app.ShareCompat;
import androidx.fragment.app.Fragment;

import com.example.androidportfolio.R;

public class IntentsPracticeFragment extends Fragment implements View.OnClickListener {
    private Activity mActivity;
    private EditText mWebsiteEditText;
    private Button mOpenWebsiteButton;
    private EditText mLocationMapEditText;
    private Button mOpenLocationInMapButton;
    private EditText mShareTextEditText;
    private Button mShareTextContentButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_intents_practice, container, false);
        setHasOptionsMenu(true);

        mActivity = getActivity();
        mWebsiteEditText = view.findViewById(R.id.website_edit_text);
        mOpenWebsiteButton = view.findViewById(R.id.open_website_button);
        mLocationMapEditText = view.findViewById(R.id.location_map_edit_text);
        mOpenLocationInMapButton = view.findViewById(R.id.open_location_in_map_button);
        mShareTextEditText = view.findViewById(R.id.share_text_edit_text);
        mShareTextContentButton = view.findViewById(R.id.share_text_content_button);

        setListeners();

        return view;
    }

    private void setListeners() {
        mOpenWebsiteButton.setOnClickListener(this);
        mOpenLocationInMapButton.setOnClickListener(this);
        mShareTextContentButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.open_website_button:
                openWebPage();
                break;
            case R.id.open_location_in_map_button:
                openLocationInMap();
                break;
            case R.id.share_text_content_button:
                shareTextContent();
                break;
            default:
                break;
        }
    }

    private void openWebPage() {
        String websiteText = mWebsiteEditText.getText().toString();
        Uri webPage = Uri.parse(websiteText);
        Intent intent = new Intent(Intent.ACTION_VIEW, webPage);
        if (mActivity != null) {
            if (intent.resolveActivity(mActivity.getPackageManager()) != null) {
                startActivity(intent);
            }
        }
    }

    private void openLocationInMap() {
        String addressString = mLocationMapEditText.getText().toString();
        Uri.Builder builder = new Uri.Builder();
        builder.scheme("geo")
                .path("0,0")
                .appendQueryParameter("q", addressString);
        Uri addressUri = builder.build();
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(addressUri);
        if (mActivity != null) {
            if (intent.resolveActivity(mActivity.getPackageManager()) != null) {
                startActivity(intent);
            }
        }
    }

    private void shareTextContent() {
        String mimeType = "text/plain";
        String title = "Text shared";
        String textToShare = mShareTextEditText.getText().toString();
        Intent intent = ShareCompat.IntentBuilder.from(mActivity)
                .setChooserTitle(title)
                .setType(mimeType)
                .setText(textToShare)
                .createChooserIntent();
        if (mActivity != null) {
            if (intent.resolveActivity(mActivity.getPackageManager()) != null) {
                startActivity(intent);
            }
        }
    }
}

