package com.example.godiscover;


import androidx.fragment.app.FragmentActivity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.KeyEvent;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.MarkerOptions;
import com.example.godiscover.databinding.ActivityMapsBinding;
import java.util.ArrayList;

public class Maps extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ActivityMapsBinding binding;
    String getUser = "";
    ArrayList<LatLng> markerL = new ArrayList<>();
    ArrayList <String> markerT = new ArrayList<>();
    LatLng tampa = new LatLng(45.634, 25.592);
    LatLng turnu_alb = new LatLng(45.642, 25.586);
    LatLng prima_scoala = new LatLng(45.635, 25.581);
    LatLng park_aventura = new LatLng(45.613, 25.637);
    LatLng paradisul_acvatic = new LatLng(45.673, 25.586);
    LatLng biserica_neagra = new LatLng(45.641, 25.588);
    LatLng bastionu_tesaturilor = new LatLng(45.637, 25.589);
    LatLng pietrele_solomon = new LatLng(45.617, 25.558);
    LatLng gradina_zoo = new LatLng(45.613, 25.633);
    LatLng poiana_brasov = new LatLng(45.595, 25.553);
    LatLng cetate = new LatLng(45.649, 25.591);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        markerL.add(tampa);
        markerL.add(turnu_alb);
        markerL.add(prima_scoala);
        markerL.add(park_aventura);
        markerL.add(paradisul_acvatic);
        markerL.add(biserica_neagra);
        markerL.add(bastionu_tesaturilor);
        markerL.add(pietrele_solomon);
        markerL.add(gradina_zoo);
        markerL.add(poiana_brasov);
        markerL.add(cetate);

        markerT.add("Tampa");
        markerT.add("Turnul Alb");
        markerT.add("Muzeul Prima Scoala");
        markerT.add("Park Aventura");
        markerT.add("Paradisul Acvatic");
        markerT.add("Biserica Neagra");
        markerT.add("Bastionu Tesaturilor");
        markerT.add("Pietrele lui Solomon");
        markerT.add("Gradina Zoo");
        markerT.add("Poiana Brasov");
        markerT.add("Cetatuia de pe Straja Brasov");

        Intent intent = getIntent();
        getUser = intent.getStringExtra("extraString");

        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK ) {
            Intent intent = new Intent(Maps.this, img_processing.class);
            intent.putExtra("extraString",getUser);
            startActivity(intent);
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        float zoomLevel = 12.0f;
        LatLng Brasov = new LatLng(45.657,25.601);

        try{
            boolean changeStyle = googleMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(this, R.raw.map_style));

        }catch (Resources.NotFoundException e)
        {
            e.getStackTrace();
        }

        for(int i = 0; i < markerL.size();i++){
            mMap.addMarker(new MarkerOptions().position(markerL.get(i)).title(markerT.get(i)));
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(Brasov,zoomLevel));
        }
    }
}