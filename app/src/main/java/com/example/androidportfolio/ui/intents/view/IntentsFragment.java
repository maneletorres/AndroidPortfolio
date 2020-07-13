package com.example.androidportfolio.ui.intents.view;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.app.ShareCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.androidportfolio.R;
import com.example.androidportfolio.databinding.FragmentIntentsPracticeBinding;
import com.example.androidportfolio.ui.intents.viewmodel.IntentsViewModel;

public class IntentsFragment extends Fragment implements View.OnClickListener {

    // Variables:
    private IntentsViewModel mViewModel;
    private FragmentIntentsPracticeBinding mBinding;
    private Activity mActivity;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // ViewModel:
        mViewModel = new ViewModelProvider(requireActivity()).get(IntentsViewModel.class);

        // DataBinding:
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_intents_practice, container, false);
        mBinding.setViewModel(mViewModel);

        initializeVariables();
        setupToolbar();
        setupListeners();

        return mBinding.getRoot();
    }

    private void initializeVariables() {
        mActivity = getActivity();
    }

    private void setupToolbar() {
        setHasOptionsMenu(true);
    }

    private void setupListeners() {
        mBinding.openWebsiteButton.setOnClickListener(this);
        mBinding.openLocationInMapButton.setOnClickListener(this);
        mBinding.shareTextContentButton.setOnClickListener(this);
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
        String websiteText = mBinding.websiteEditText.getText().toString();
        Uri webPage = Uri.parse(websiteText);
        Intent intent = new Intent(Intent.ACTION_VIEW, webPage);
        if (mActivity != null) {
            if (intent.resolveActivity(mActivity.getPackageManager()) != null) {
                startActivity(intent);
            }
        }
    }

    private void openLocationInMap() {
        String addressString = mBinding.locationMapEditText.getText().toString();
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
        String title = getString(R.string.text_shared);
        String textToShare = mBinding.shareTextEditText.getText().toString();
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

