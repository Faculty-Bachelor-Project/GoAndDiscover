package com.example.godiscover;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import org.opencv.android.OpenCVLoader;


public class MainActivity extends AppCompatActivity {

    private static int SPLASH_TIME_OUT = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new Handler().postDelayed(new Runnable(){
            @Override
            public void run(){
                Intent homeIntent = new Intent(MainActivity.this, img_processing.class);
                startActivity(homeIntent);
                finish();
            }

        },SPLASH_TIME_OUT);
    }
}