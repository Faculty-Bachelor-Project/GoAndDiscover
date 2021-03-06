package com.example.godiscover;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;


import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.godiscover.locations.getDataFromDb;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class user extends AppCompatActivity {

    private static String URL = "http://192.168.0.178/gndApp/getUser.php";
    private static final int PICK_IMAGE_REQUEST = 0;
    private Uri images;
    TextView userText;
    TextView emailText;
    Button button;
    CircleImageView avatar;
    String getUser = "";
    private TextView steps_count, calories_count, distance;
    private double MagnitudePrevious = 0;
    private Integer stepCount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        Intent intent = getIntent();
        getUser = intent.getStringExtra("extraString");

        button = (Button)findViewById(R.id.chgImg);
        avatar = (CircleImageView) findViewById(R.id.userImg);

        steps_count = (TextView) findViewById(R.id.counter);
        calories_count = (TextView) findViewById(R.id.calories);
        distance = (TextView) findViewById(R.id.distance);
        SensorManager sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        Sensor sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        String images = preferences.getString("image", null);

        if (images != null) {
            avatar.setImageURI(Uri.parse(images));
        } else {
            avatar.setImageResource(R.drawable.avatar);
        }

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        userText = (TextView) findViewById(R.id.userT);
        emailText = (TextView) findViewById(R.id.emailT);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_nav);
        bottomNavigationView.setSelectedItemId(R.id.user);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId())
                {
                    case R.id.googleMaps:
                        Intent intent1 = new Intent(user.this, map.class);
                        intent1.putExtra("extraString",getUser);
                        startActivity(intent1);
                        overridePendingTransition(0,0);
                        return true;

                    case R.id.db_data:
                        Intent intent2 = new Intent(user.this, getDataFromDb.class);
                        intent2.putExtra("extraString",getUser);
                        startActivity(intent2);
                        overridePendingTransition(0,0);
                        return true;

                    case R.id.scanner:
                        Intent intent3 = new Intent(user.this, img_processing.class);
                        intent3.putExtra("extraString",getUser);
                        startActivity(intent3);
                        overridePendingTransition(0,0);
                        return true;

                    case R.id.events:
                        Intent intent4 = new Intent(user.this, events.class);
                        intent4.putExtra("extraString",getUser);
                        startActivity(intent4);
                        overridePendingTransition(0,0);
                        return true;

                    case R.id.user:
                        return true;
                }
                return false;
            }
        });
        insertData();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //openImg();
                imageSelect();
            }
        });

        SensorEventListener stepDetector = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent event) {
                if(event != null)
                {
                    float x_acceleration = event.values[0];
                    float y_acceleration = event.values[1];
                    float z_acceleration = event.values[2];

                    double Magnitude = Math.sqrt(x_acceleration * x_acceleration + y_acceleration * y_acceleration + z_acceleration * z_acceleration);
                    double MagnitudeDelta = Magnitude - MagnitudePrevious;
                    MagnitudePrevious = Magnitude;

                    if(MagnitudeDelta > 6)
                    {
                        stepCount++;
                    }
                    steps_count.setText(stepCount.toString());
                    calories_count.setText(getCalories(stepCount));
                    distance.setText(getDistance(stepCount));


                }
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {

            }
        };

        sensorManager.registerListener(stepDetector,sensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    public void imageSelect() {
        permissionsCheck();
        Intent intent;
        if (Build.VERSION.SDK_INT < 19) {
            intent = new Intent(Intent.ACTION_GET_CONTENT);
        } else {
            intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
            intent.addCategory(Intent.CATEGORY_OPENABLE);
        }
        intent.setType("image/*");
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    public void permissionsCheck() {
        int storagePermission = ContextCompat.checkSelfPermission(this,Manifest.permission.READ_EXTERNAL_STORAGE);
        if (storagePermission != PackageManager.PERMISSION_GRANTED) {
            ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null) {

            images = data.getData();
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString("image", String.valueOf(images));
            editor.commit();
            avatar.setImageURI(images);
            avatar.invalidate();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.user_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.logout_btn) {
            Intent intent = new Intent(user.this, login.class);
            startActivity(intent);
            return true;
        }else if (id == R.id.chgpass_btn) {
            Intent intent = new Intent(user.this, changePass.class);
            intent.putExtra("extraString",getUser);
            startActivity(intent);
            return true;
        }else if (id == R.id.support_btn) {
            Intent intent = new Intent(user.this, support.class);
            intent.putExtra("extraString",getUser);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void insertData()
    {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                try {
                    JSONArray jsonArr = new JSONArray(response);
                    for (int i = 0; i < jsonArr.length(); i++) {
                        JSONArray userArray = new JSONArray(jsonArr.getJSONObject(i).getString("user"));
                        userText.setText(getUser);
                        emailText.setText(userArray.getJSONObject(i).getString("email"));
                    }
                }catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            protected Map<String,String> getParams() throws AuthFailureError
            {
                Map<String,String> params = new HashMap<String,String>();
                String user = getUser;
                params.put("user",user);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    protected String getCalories(int steps)
    {
        String calories = String.valueOf((int) (steps * 0.033));
        return calories;
    }

    protected String getDistance(int steps)
    {
        String distance = String.valueOf((int) (steps * 0.3048));
        return distance;
    }

    protected void onPause()
    {
        super.onPause();

        SharedPreferences sharedPreferences = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear().putInt("stepCount", stepCount).apply();
    }

    protected void onStop() {
        super.onStop();

        SharedPreferences sharedPreferences = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear().putInt("stepCount", stepCount).apply();
    }

    protected void onResume() {
        super.onResume();

        SharedPreferences sharedPreferences = getPreferences(MODE_PRIVATE);
        stepCount = sharedPreferences.getInt("stepCount", 0);
    }


}