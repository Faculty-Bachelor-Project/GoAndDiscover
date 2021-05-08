package com.example.godiscover;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MenuItem;

import com.example.godiscover.locations.getDataFromDb;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class map extends FragmentActivity implements OnMapReadyCallback {

    GoogleMap map;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        Intent intent = getIntent();
        String getUser = intent.getStringExtra("extraString");

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_nav);
        bottomNavigationView.setSelectedItemId(R.id.googleMaps);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId())
                {
                    case R.id.googleMaps:
                        return true;

                    case R.id.db_data:
                        Intent intent1 = new Intent(map.this, getDataFromDb.class);
                        intent1.putExtra("extraString",getUser);
                        startActivity(intent1);
                        overridePendingTransition(0,0);
                        return true;

                    case R.id.scanner:
                        Intent intent2 = new Intent(map.this, img_processing.class);
                        intent2.putExtra("extraString",getUser);
                        startActivity(intent2);
                        overridePendingTransition(0,0);
                        return true;

                    case R.id.events:
                        Intent intent3 = new Intent(map.this, events.class);
                        intent3.putExtra("extraString",getUser);
                        startActivity(intent3);
                        overridePendingTransition(0,0);
                        return true;

                    case R.id.user:
                        Intent intent4 = new Intent(map.this, user.class);
                        intent4.putExtra("extraString",getUser);
                        startActivity(intent4);
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK ) {
            Intent intent = new Intent(map.this,
                    img_processing.class);
            startActivity(intent);
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        float zoomLevel = 12.0f;
        LatLng Brasov = new LatLng(45.657,25.601);

        try{
            boolean changeStyle = googleMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(this, R.raw.map_style));

        }catch (Resources.NotFoundException e)
        {
            e.getStackTrace();
        }

        map.moveCamera(CameraUpdateFactory.newLatLngZoom(Brasov,zoomLevel));
    }
}