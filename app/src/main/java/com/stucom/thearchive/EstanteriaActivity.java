package com.stucom.thearchive;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class EstanteriaActivity extends AppCompatActivity implements View.OnClickListener {

    private static final int[] BUTTONS_ID = new int[] {
            R.id.btnSeeAll, R.id.btnRead, R.id.btnReading, R.id.btnWantTo
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_estanteria);

        for(int id: BUTTONS_ID) {
            Button button = findViewById(id);
            button.setOnClickListener(this);
        }
    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent(EstanteriaActivity.this, ShelvesActivity.class);;
        switch(view.getId()) {
            case R.id.btnSeeAll:
                intent.putExtra("type", "all");
                break;
            case R.id.btnRead:
                intent.putExtra("type", "read");
                break;
            case R.id.btnReading:
                intent.putExtra("type", "reading");
                break;
            case R.id.btnWantTo:
                intent.putExtra("type", "want");
                break;
        }
        if (intent == null) return;
        startActivity(intent);
    }
}
