package com.stucom.thearchive.modelo_navigation;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.stucom.thearchive.R;
import com.stucom.thearchive.modelo_user.Token;
import com.stucom.thearchive.modelo_user.User;
import com.stucom.thearchive.utils.AppUtils;

import java.util.HashMap;
import java.util.Map;

import static android.content.Context.MODE_PRIVATE;

public class ProfileFragment extends Fragment implements View.OnClickListener {

    AppUtils appUtils;
    TextView tvUser;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_perfil_usuario, container, false);
        v.findViewById(R.id.btnEditProfile).setOnClickListener(this);
        v.findViewById(R.id.btnSeeBooks).setOnClickListener(this);
        v.findViewById(R.id.btnSettings).setOnClickListener(this);
        appUtils = new AppUtils(getContext());
        tvUser = v.findViewById(R.id.tvUser);

        downloadUser();
        loadData();
        return v;
    }


    private void loadData(){
        tvUser.setText(appUtils.getUsername());
    }

    private void moveToEditProfile() {
        EditFragment fragment= new EditFragment();
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(((ViewGroup)getView().getParent()).getId(), fragment, "findThisFragment")
                .commit();
    }


    private void moveToSettings() {
        SettingsFragment fragment= new SettingsFragment();
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(((ViewGroup)getView().getParent()).getId(), fragment, "findThisFragment")
                .commit();
    }

    private void downloadUser(){
        RequestQueue queue = Volley.newRequestQueue(getActivity());
        String url = "http://169.254.25.54:8000/archive/user/" + appUtils.getUsername();
        StringRequest request = new StringRequest(
                Request.Method.GET,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Gson gson = new Gson();
                        User user = gson.fromJson(response, User.class);

                        SharedPreferences prefs = getActivity().getSharedPreferences(getActivity().getPackageName(), MODE_PRIVATE);
                        SharedPreferences.Editor ed = prefs.edit();
                        String json = gson.toJson(user);
                        ed.putString("user", json);
                        ed.commit();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                    }
                })
        {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Authorization", "Token " + appUtils.getToken());
                return params;
            }
        };
        queue.add(request);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnEditProfile: moveToEditProfile(); break;
            case R.id.btnSeeBooks: break;
            case R.id.btnSettings: moveToSettings(); break;
        }
    }
}
