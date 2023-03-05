package com.zea.company.route_u;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        try {
            Thread.sleep(5000);
            startActivity(new Intent(this,SplashActivity.class));
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}