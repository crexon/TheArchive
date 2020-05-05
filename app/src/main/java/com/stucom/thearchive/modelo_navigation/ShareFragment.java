package com.stucom.thearchive.modelo_navigation;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.stucom.thearchive.R;

public class ShareFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_share, container, false);
        shareAction();

        return v;

    }

    private void shareAction() {
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_SUBJECT, getActivity().getString(R.string.app_name));
        sendIntent.putExtra(Intent.EXTRA_TEXT, getActivity().getString(R.string.share_text));
        sendIntent.setType("text/plain");
        startActivity(sendIntent);
    }

}
