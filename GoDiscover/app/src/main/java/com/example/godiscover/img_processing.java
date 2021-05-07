package com.example.godiscover;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.SurfaceView;
import android.widget.Toast;

import com.example.godiscover.locations.getDataFromDb;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.opencv.android.BaseLoaderCallback;
import org.opencv.android.CameraBridgeViewBase;
import org.opencv.android.JavaCameraView;
import org.opencv.android.OpenCVLoader;
import org.opencv.core.Mat;

public class img_processing extends AppCompatActivity implements CameraBridgeViewBase.CvCameraViewListener2 {

    CameraBridgeViewBase cameraBridgeViewBase;
    BaseLoaderCallback callback;
    int counter = 0;
    private long pressedTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_img_processing);
        Intent intent = getIntent();
        String getUser = intent.getStringExtra("extra");
        cameraBridgeViewBase = (JavaCameraView)findViewById(R.id.camera);
        cameraBridgeViewBase.setVisibility(SurfaceView.VISIBLE);
        cameraBridgeViewBase.setCvCameraViewListener(this);

        callback = new BaseLoaderCallback(this) {
            @Override
            public void onManagerConnected(int status) {
                super.onManagerConnected(status);
                switch (status) {
                    case BaseLoaderCallback.SUCCESS:
                        cameraBridgeViewBase.enableView();
                        break;
                    default:super.onManagerConnected(status);
                }
            }
        };

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_nav);
        bottomNavigationView.setSelectedItemId(R.id.scanner);
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
                        return true;

                    case R.id.events:
                        startActivity(new Intent(getApplicationContext(),events.class));
                        overridePendingTransition(0,0);
                        return true;

                    case R.id.user:
                        Intent intent1 = new Intent(img_processing.this, user_menu.class);
                        intent1.putExtra("extraString",getUser);
                        startActivity(intent1);
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });
    }

    @Override
    public void onBackPressed() {

        if (pressedTime + 2000 > System.currentTimeMillis()) {
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }else {
            Toast.makeText(getBaseContext(), "Press the back button once again to close the application.", Toast.LENGTH_SHORT).show();
        }
        pressedTime = System.currentTimeMillis();
    }

    @Override
    public Mat onCameraFrame(CameraBridgeViewBase.CvCameraViewFrame input_frame) {
        Mat frame = input_frame.rgba();
       return frame;
    }

    @Override
    public void onCameraViewStarted(int width, int height) {

    }

    @Override
    public void onCameraViewStopped() {

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!OpenCVLoader.initDebug())
            Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
        else
            callback.onManagerConnected(callback.SUCCESS);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (cameraBridgeViewBase != null)
            cameraBridgeViewBase.disableView();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (cameraBridgeViewBase != null)
            cameraBridgeViewBase.disableView();
    }
}