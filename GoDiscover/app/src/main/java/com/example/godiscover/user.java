package com.example.godiscover;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MenuItem;

import com.example.godiscover.locations.getDataFromDb;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class user extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_nav);
        bottomNavigationView.setSelectedItemId(R.id.user);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId())
                {
                    case R.id.googleMaps:
                        startActivity(new Intent(getApplicationContext(),map.class));
                        overridePendingTransition(0,0);
                        return true;

                    case R.id.db_data:
                        startActivity(new Intent(getApplicationContext(), getDataFromDb.class));
                        overridePendingTransition(0,0);
                        return true;

                    case R.id.scanner:
                        startActivity(new Intent(getApplicationContext(),img_processing.class));
                        overridePendingTransition(0,0);
                        return true;

                    case R.id.events:
                        startActivity(new Intent(getApplicationContext(),events.class));
                        overridePendingTransition(0,0);
                        return true;

                    case R.id.user:
                        return true;
                }
                return false;
            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK ) {
            Intent intent = new Intent(user.this,
                    img_processing.class);
            startActivity(intent);
        }
        return super.onKeyDown(keyCode, event);
    }
}