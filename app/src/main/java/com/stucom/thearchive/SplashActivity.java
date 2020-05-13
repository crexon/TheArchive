package com.stucom.thearchive;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.stucom.thearchive.utils.SessionManager;

public class SplashActivity extends AppCompatActivity {

    private static int SPLASH_TIME_OUT = 5000;

    Animation topAnimation, middleAnimation;
    View first, second, third, fourth, fifth, sixth;
    TextView name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);

        topAnimation = AnimationUtils.loadAnimation(this, R.anim.top_animation);
        middleAnimation = AnimationUtils.loadAnimation(this, R.anim.middle_animation);


        first = findViewById(R.id.first_line);
        second = findViewById(R.id.second_line);
        third = findViewById(R.id.third_line);
        fourth = findViewById(R.id.four_line);
        fifth = findViewById(R.id.five_line);
        sixth = findViewById(R.id.six_line);

        name = findViewById(R.id.name);


        first.setAnimation(topAnimation);
        second.setAnimation(topAnimation);
        third.setAnimation(topAnimation);
        fourth.setAnimation(topAnimation);
        fifth.setAnimation(topAnimation);
        sixth.setAnimation(topAnimation);
        name.setAnimation(middleAnimation);


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashActivity.this, SessionManager.class);
                startActivity(intent);
                finish();
            }
        }, SPLASH_TIME_OUT);


    }
}
