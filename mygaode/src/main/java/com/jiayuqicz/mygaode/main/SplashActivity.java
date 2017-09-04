package com.jiayuqicz.mygaode.main;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import com.jiayuqicz.mygaode.R;

public class SplashActivity extends AppCompatActivity {

    private final int DELAY_TIME = 1500;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(intent);
                SplashActivity.this.finish();
            }
        }, DELAY_TIME);

    }
}
