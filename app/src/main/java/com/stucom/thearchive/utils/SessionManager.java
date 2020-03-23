package com.stucom.thearchive.utils;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

import com.stucom.thearchive.LoginActivity;
import com.stucom.thearchive.MainActivity;
import com.stucom.thearchive.R;

public class SessionManager extends AppCompatActivity {

    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager);
        prepareProgressBar(true);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                SharedPreferences prefs = getSharedPreferences(getPackageName(), MODE_PRIVATE);
                String token = prefs.getString("token", "");
                if (token.isEmpty()) {
                    Intent singin = new Intent(SessionManager.this, LoginActivity.class);
                    startActivity(singin);
                } else {
                    Intent home = new Intent(SessionManager.this, MainActivity.class);
                    startActivity(home);
                }
                finish();
            }
        }, 1000);
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
}
