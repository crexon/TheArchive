package com.stucom.thearchive.modelo_navigation;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.stucom.thearchive.LoginActivity;
import com.stucom.thearchive.R;
import com.stucom.thearchive.utils.AppUtils;

public class LogoutFragment extends Fragment {
    AppUtils appUtils;
    ProgressDialog progressDialog;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_logout, container, false);
        appUtils = new AppUtils(getContext());
        logoutUser();
        return v;
    }

    private void logoutUser() {
        prepareProgressBar(true);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                appUtils.deleteUserData();
                moveFirst();
            }
        }, 250);
    }


    private void moveFirst() {
        Intent intent = new Intent(getActivity(), LoginActivity.class);
        startActivity(intent);
        getActivity().finish();
    }

    private void prepareProgressBar(boolean mode) {
        if (mode) {
            progressDialog = new ProgressDialog(getContext());
            progressDialog.show();
            progressDialog.setContentView(R.layout.progress_dialog);
            progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        } else {
            progressDialog.dismiss();
        }
    }
}


