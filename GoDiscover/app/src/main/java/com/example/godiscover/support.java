package com.example.godiscover;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class support extends AppCompatActivity {

    ImageButton insta,facebook,github,insta1,facebook1,github1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_support);

        insta = (ImageButton)findViewById(R.id.insta_btn);
        insta1 = (ImageButton)findViewById(R.id.insta_btn1);
        facebook = (ImageButton)findViewById(R.id.facebook_btn);
        facebook1 = (ImageButton)findViewById(R.id.facebook_btn1);
        github = (ImageButton)findViewById(R.id.github_btn);
        github1 = (ImageButton)findViewById(R.id.github_btn1);

        insta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uriUrl = Uri.parse("https://www.instagram.com/andreizhaa");
                Intent web_url = new Intent(Intent.ACTION_VIEW, uriUrl);
                startActivity(web_url);
            }
        });

        insta1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uriUrl = Uri.parse("https://www.instagram.com/sabinsj07");
                Intent web_url = new Intent(Intent.ACTION_VIEW, uriUrl);
                startActivity(web_url);
            }
        });

        facebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uriUrl = Uri.parse("https://www.facebook.com/andrei.zaharia.509/");
                Intent web_url = new Intent(Intent.ACTION_VIEW, uriUrl);
                startActivity(web_url);
            }
        });

        facebook1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uriUrl = Uri.parse("https://www.facebook.com/SabinTheGeek/");
                Intent web_url = new Intent(Intent.ACTION_VIEW, uriUrl);
                startActivity(web_url);
            }
        });

        github.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uriUrl = Uri.parse("https://github.com/andreiz98");
                Intent web_url = new Intent(Intent.ACTION_VIEW, uriUrl);
                startActivity(web_url);
            }
        });

        github1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uriUrl = Uri.parse("https://github.com/SabinSJ");
                Intent web_url = new Intent(Intent.ACTION_VIEW, uriUrl);
                startActivity(web_url);
            }
        });
    }
}