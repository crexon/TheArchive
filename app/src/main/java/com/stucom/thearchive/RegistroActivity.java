package com.stucom.thearchive;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputEditText;
import com.muddzdev.styleabletoast.StyleableToast;

import java.util.HashMap;
import java.util.Map;

public class RegistroActivity extends AppCompatActivity implements View.OnClickListener {

    TextInputEditText etUser, etName, etSurname, etPass, etPassVerify;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);
        etUser = findViewById(R.id.etUser);
        etName = findViewById(R.id.etName);
        etSurname = findViewById(R.id.etSurname);
        etPass = findViewById(R.id.etPass);
        etPassVerify = findViewById(R.id.etPassVerify);
        findViewById(R.id.btnSignUp).setOnClickListener(this);
    }

    protected void registerUser() {
        prepareProgressBar(true);
        findViewById(R.id.btnSignUp).setVisibility(View.GONE);
        final String user = this.etUser.getText().toString();
        final String name = this.etName.getText().toString();
        final String surname = this.etSurname.getText().toString();
        final String pass = this.etPass.getText().toString();

        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "http://169.254.25.54:8000/archive/signup/";
        StringRequest request = new StringRequest(
                Request.Method.POST,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        moveToLogin();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        prepareProgressBar(false);
                        findViewById(R.id.btnSignUp).setVisibility(View.VISIBLE);
                        StyleableToast.makeText(RegistroActivity.this, "The data provided is not correct", Toast.LENGTH_LONG, R.style.toast).show();
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("username", user);
                params.put("password", pass);
                params.put("name", name);
                params.put("surname", surname);
                return params;
            }
        };
        queue.add(request);
    }

    private void prepareProgressBar(boolean mode) {
        if (mode) {
            progressDialog = new ProgressDialog(this);
            progressDialog.show();
            progressDialog.setContentView(R.layout.progress_dialog);
            progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        } else {
            progressDialog.dismiss();
        }
    }

    private void moveToLogin() {
        Intent intent = new Intent(RegistroActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnSignUp:
                if (!etUser.getText().toString().isEmpty() && !etName.getText().toString().isEmpty() && !etSurname.getText().toString().isEmpty() && !etPass.getText().toString().isEmpty()){
                    if (etPass.getText().toString().equals(etPassVerify.getText().toString())) {
                        registerUser();
                    } else{
                        StyleableToast.makeText(RegistroActivity.this, RegistroActivity.this.getString(R.string.pass_equals), Toast.LENGTH_LONG, R.style.toast).show();
                    }
                } else{
                    StyleableToast.makeText(RegistroActivity.this, RegistroActivity.this.getString(R.string.data_missing), Toast.LENGTH_LONG, R.style.toast).show();
                }
                break;
        }
    }
}
