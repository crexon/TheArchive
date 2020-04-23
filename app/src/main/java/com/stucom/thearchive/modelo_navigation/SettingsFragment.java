package com.stucom.thearchive.modelo_navigation;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.muddzdev.styleabletoast.StyleableToast;
import com.stucom.thearchive.LoginActivity;
import com.stucom.thearchive.R;
import com.stucom.thearchive.utils.AppUtils;

public class SettingsFragment extends Fragment implements View.OnClickListener {

    AppUtils appUtils;
    ProgressDialog progressDialog;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_settings, container, false);
        v.findViewById(R.id.btnDelete).setOnClickListener(this);
        appUtils = new AppUtils(getContext());
        return v;
    }


    private void deleteUser(){
        prepareProgressBar(true);
        RequestQueue queue = Volley.newRequestQueue(getActivity());
        String url = "http://192.168.56.1:8000/archive/user/" + appUtils.getUsername();
        StringRequest request = new StringRequest(
                Request.Method.DELETE,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        StyleableToast.makeText(getActivity(), getActivity().getString(R.string.delete_correct), Toast.LENGTH_LONG, R.style.toast).show();
                        appUtils.deleteUserData();
                        moveToLogin();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        prepareProgressBar(false);
                        StyleableToast.makeText(getActivity(), getActivity().getString(R.string.delete_error), Toast.LENGTH_LONG, R.style.toast).show();
                    }
                }
        );
        queue.add(request);
    }

    private void moveToLogin() {
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


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnDelete: deleteUser(); break;
        }
    }

}
