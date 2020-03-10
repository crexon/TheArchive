package com.stucom.thearchive;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
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

import java.util.HashMap;
import java.util.Map;

public class RegistroActivity extends AppCompatActivity {

    TextInputEditText etUser, etName, etSurname, etPass, etPassVerify;
    ProgressBar pbLoding;
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
        pbLoding = findViewById(R.id.pbLoading);
        btnSignUp = findViewById(R.id.btnSignUp);


        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (etPass.getText().toString().equals(etPassVerify.getText().toString())){
                    registerUser();
                } else {
                    Toast.makeText(RegistroActivity.this, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    protected void registerUser() {

        pbLoding.setVisibility(View.VISIBLE);
        btnSignUp.setVisibility(View.GONE);
        final String user = this.etUser.getText().toString();
        final String name = this.etName.getText().toString();
        final String surname = this.etSurname.getText().toString();
        final String pass = this.etPass.getText().toString();
        final String passVerify = this.etPassVerify.getText().toString();

        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "LINK URL API";
        StringRequest request = new StringRequest(
                Request.Method.POST,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        pbLoding.setVisibility(View.GONE);
                        btnSignUp.setVisibility(View.VISIBLE);
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("user", user);
                params.put("name", name);
                params.put("surname", surname);
                params.put("pass", pass);
                params.put("passVerify", passVerify);
                return params;
            }
        };
        queue.add(request);
    }
}
