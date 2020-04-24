package com.stucom.thearchive;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;


public class ShelvesActivity extends AppCompatActivity {

    String type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shelves);
        Bundle bundle = getIntent().getExtras();
        type = bundle.getString("type");

    }
}
