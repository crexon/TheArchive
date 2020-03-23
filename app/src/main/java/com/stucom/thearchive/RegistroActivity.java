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

public class RegistroActivity extends AppCompatActivity {

    TextInputEditText etUser, etName, etSurname, etPass, etPassVerify;
    ProgressDialog progressDialog;
    Button btnSignUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);
        etUser = findViewById(R.id.etUser);
        etName = findViewById(R.id.etName);
        etSurname = findViewById(R.id.etSurname);
        etPass = findViewById(R.id.etPass);
        etPassVerify = findViewById(R.id.etPassVerify);
        btnSignUp = findViewById(R.id.btnSignUp);


        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (etPass.getText().toString().equals(etPassVerify.getText().toString())) {
                    registerUser();
                } else {
                    Toast.makeText(RegistroActivity.this, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    protected void registerUser() {
        prepareProgressBar(true);
        btnSignUp.setVisibility(View.GONE);
        final String user = this.etUser.getText().toString();
        final String name = this.etName.getText().toString();
        final String surname = this.etSurname.getText().toString();
        final String pass = this.etPass.getText().toString();

        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "http://192.168.56.1:8000/archive/signup/";
        StringRequest request = new StringRequest(
                Request.Method.POST,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("Pol", response);
                        moveToLogin();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        prepareProgressBar(false);
                        btnSignUp.setVisibility(View.VISIBLE);
                        StyleableToast.makeText(RegistroActivity.this, "The data provided is not correct", Toast.LENGTH_LONG, R.style.toast).show();
                        Log.d("Pol", error.toString());
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

}
