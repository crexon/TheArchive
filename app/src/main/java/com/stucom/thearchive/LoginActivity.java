package com.stucom.thearchive;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.Gson;
import com.muddzdev.styleabletoast.StyleableToast;
import com.stucom.thearchive.modelo_user.Token;
import com.stucom.thearchive.utils.AppUtils;
import com.tapadoo.alerter.Alerter;
import com.tapadoo.alerter.OnHideAlertListener;
import com.tapadoo.alerter.OnShowAlertListener;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    TextInputEditText etUser, etPass;
    ProgressDialog progressDialog;
    AppUtils appUtils;
    Button btnSignIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        etUser = findViewById(R.id.etUser);
        etPass = findViewById(R.id.etPass);
        appUtils = new AppUtils(getApplicationContext());
        btnSignIn = findViewById(R.id.btnSignIn);
        btnSignIn.setOnClickListener(this);
        findViewById(R.id.tvRegister).setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tvRegister: Intent intent = new Intent(LoginActivity.this, RegistroActivity.class); startActivity(intent); break;
            case R.id.btnSignIn: loginUser(); break;
        }
    }

    protected void loginUser() {
        prepareProgressBar(true);
        btnSignIn.setVisibility(View.GONE);
        final String user = this.etUser.getText().toString();
        final String pass = this.etPass.getText().toString();

        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "http://169.254.25.54:8000/archive/login/";
        StringRequest request = new StringRequest(
                Request.Method.POST,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Gson gson = new Gson();
                        Token token = gson.fromJson(response, Token.class);
                        String token_aux = token.getToken();

                        SharedPreferences prefs = getSharedPreferences(getPackageName(), MODE_PRIVATE);
                        SharedPreferences.Editor ed = prefs.edit();
                        ed.putString("token", token_aux);
                        ed.putString("username", user);
                        ed.apply();

                        moveToMain();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        prepareProgressBar(false);
                        btnSignIn.setVisibility(View.VISIBLE);
                        StyleableToast.makeText(LoginActivity.this, "Your login credentials are not corrects", Toast.LENGTH_LONG, R.style.toast).show();
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("username", user);
                params.put("password", pass);
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

    private void moveToMain() {
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
