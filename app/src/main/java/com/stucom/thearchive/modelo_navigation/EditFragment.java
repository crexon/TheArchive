package com.stucom.thearchive.modelo_navigation;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputEditText;
import com.muddzdev.styleabletoast.StyleableToast;
import com.stucom.thearchive.BDetailActivity;
import com.stucom.thearchive.R;
import com.stucom.thearchive.utils.AppUtils;

import java.util.HashMap;
import java.util.Map;

public class EditFragment extends Fragment implements View.OnClickListener {

    AppUtils appUtils;
    ProgressDialog progressDialog;
    TextInputEditText etName, etSurname, etPass, etPassVerify;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_editar_perfil, container, false);
        etName = v.findViewById(R.id.etName);
        etSurname = v.findViewById(R.id.etSurname);
        etPass = v.findViewById(R.id.etPass);
        etPassVerify = v.findViewById(R.id.etPassVerify);
        v.findViewById(R.id.btnSave).setOnClickListener(this);

        appUtils = new AppUtils(getActivity());
        loadData();
        return v;
    }

    protected void updateUserInfo() {
        prepareProgressBar(true);
        final String name = this.etName.getText().toString();
        final String surname = this.etSurname.getText().toString();
        final String pass = this.etPassVerify.getText().toString();

        RequestQueue queue = Volley.newRequestQueue(getActivity());
        String url = "http://192.168.56.1:8000/archive/user/" + appUtils.getUserConnected().getUsername();
        StringRequest request = new StringRequest(
                Request.Method.PUT,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        prepareProgressBar(false);
                        StyleableToast.makeText(getActivity(), getActivity().getString(R.string.update_user_correct), Toast.LENGTH_SHORT, R.style.toast).show();
                        moveToProfile();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        prepareProgressBar(false);
                        StyleableToast.makeText(getActivity(), getActivity().getString(R.string.update_user_error), Toast.LENGTH_LONG, R.style.toast).show();
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("username", appUtils.getUserConnected().getUsername());
                params.put("first_name", name);
                params.put("last_name", surname);
                params.put("password", pass);
                return params;
            }
        };
        queue.add(request);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnSave:
                if (!etName.getText().toString().isEmpty() && !etSurname.getText().toString().isEmpty() && !etPass.getText().toString().isEmpty()){
                    if (etPass.getText().toString().equals(etPassVerify.getText().toString())) {
                        updateUserInfo();
                    } else{
                        StyleableToast.makeText(getActivity(), getActivity().getString(R.string.pass_equals), Toast.LENGTH_LONG, R.style.toast).show();
                    }
                } else{
                    StyleableToast.makeText(getActivity(), getActivity().getString(R.string.data_missing), Toast.LENGTH_LONG, R.style.toast).show();
                }
                break;
        }
    }

    private void loadData(){
        etName.setText(appUtils.getUserConnected().getFirst_name());
        etSurname.setText(appUtils.getUserConnected().getLast_name());
    }

    private void moveToProfile() {
        ProfileFragment fragment= new ProfileFragment();
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(((ViewGroup)getView().getParent()).getId(), fragment, "findThisFragment")
                .addToBackStack(null)
                .commit();
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
