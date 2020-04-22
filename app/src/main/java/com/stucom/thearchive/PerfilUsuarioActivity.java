package com.stucom.thearchive;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class PerfilUsuarioActivity extends AppCompatActivity {

    Button btnEditProfile, btnSeeBooks, btnSettings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil_usuario);
        btnEditProfile = findViewById(R.id.btnEditProfile);
        btnSeeBooks = findViewById(R.id.btnSeeBooks);
        btnSettings = findViewById(R.id.btnSettings);


        btnEditProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //moveToEditarPerfil();
            }
        });

        btnSeeBooks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                moveToEstanteriaActivity();
            }
        });

        btnSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

    }


    private void moveToEstanteriaActivity() {
        Intent intent = new Intent(PerfilUsuarioActivity.this, EstanteriaActivity.class);
        startActivity(intent);
        finish();
    }

}
