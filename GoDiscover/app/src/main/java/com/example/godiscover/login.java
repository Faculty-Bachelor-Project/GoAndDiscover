package com.example.godiscover;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;

import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;

import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;
import com.vishnusivadas.advanced_httpurlconnection.PutData;

import java.util.ArrayList;
import java.util.List;


public class login extends AppCompatActivity {

    private static String URL = "http://192.168.0.108/gndApp/login.php";

    EditText username;
    EditText password;
    ProgressBar progressBar;
    Button loginBt, signupButton, forgotBt;
    CheckBox remember;
    String Username;
    String Password;

    String prefs_name = "PrefsFile";
    SharedPreferences MPrefs;

    private long pressedTime;
    public static final int REQUEST_ID_MULTIPLE_PERMISSIONS = 1;

    private void getPreferncesData() {
        SharedPreferences SP = getSharedPreferences(prefs_name, MODE_PRIVATE);
        if (SP.contains("pref_name")) {
            String U = SP.getString("pref_name", "not found");
            username.setText(U.toString());
        }
        if (SP.contains("pref_pass")) {
            String P = SP.getString("pref_pass", "not found");
            password.setText(P.toString());
        }
        if (SP.contains("pref_check")) {
            Boolean b = SP.getBoolean("pref_check", false);
            remember.setChecked(b);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        username = (EditText) findViewById(R.id.user);
        password = (EditText) findViewById(R.id.pass);
        progressBar = (ProgressBar) findViewById(R.id.progressbar);
        loginBt = (Button) findViewById(R.id.loginBtn);
        signupButton = (Button) findViewById(R.id.signupBtn);
        forgotBt = (Button) findViewById(R.id.forgotBtn);
        remember = (CheckBox)findViewById(R.id.RememberMe);

        MPrefs = getSharedPreferences(prefs_name, MODE_PRIVATE);
        getPreferncesData();

        if(checkAndRequestPermissions()) {
        }

        loginBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Username = username.getText().toString();
                Password = password.getText().toString();

                if(!Username.equals("") && !Password.equals("")) {

                    if (remember.isChecked()) {
                        Boolean bool_check = remember.isChecked();
                        SharedPreferences.Editor editor = MPrefs.edit();
                        editor.putString("pref_name", Username);
                        editor.putString("pref_pass", Password);
                        editor.putBoolean("pref_check", bool_check);
                        editor.apply();
                    }
                    else MPrefs.edit().clear().apply();

                    progressBar.setVisibility(View.VISIBLE);
                    Handler handler = new Handler();
                    handler.post(new Runnable() {

                        @Override
                        public void run() {
                            String[] field = new String[2];
                            field[0] = "user";
                            field[1] = "pass";

                            String[] data = new String[2];
                            data[0] = Username;
                            data[1] = Password;

                            PutData putData = new PutData(URL, "POST", field, data);

                            if (putData.startPut()) {
                                if (putData.onComplete()) {
                                    progressBar.setVisibility(View.GONE);
                                    String result = putData.getResult();
                                    if (result.equals("Login Success")) {
                                        Intent intent = new Intent(login.this, img_processing.class);
                                        intent.putExtra("extraString",Username);
                                        startActivity(intent);
                                        username.getText().clear();
                                        password.getText().clear();
                                        finish();
                                    } else
                                        Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();
                                }
                            }
                        }
                    });
                }else
                {
                    Toast.makeText(getApplicationContext(), "All fields required", Toast.LENGTH_SHORT).show();
                }

            }
        });


        forgotBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent homeIntent = new Intent(login.this, forgotPassword.class);
                startActivity(homeIntent);
                finish();
            }
        });

        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent homeIntent = new Intent(login.this, signup.class);
                startActivity(homeIntent);
                finish();
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

    private  boolean checkAndRequestPermissions() {
        int permissionSendMessage = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA);
        int locationPermission = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);
        int storagePermission = ContextCompat.checkSelfPermission(this,Manifest.permission.WRITE_EXTERNAL_STORAGE);
        List<String> listPermissionsNeeded = new ArrayList<>();
        if (locationPermission != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.ACCESS_FINE_LOCATION);
        }
        if (permissionSendMessage != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.CAMERA);
        }
        if (storagePermission != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(this, listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]),REQUEST_ID_MULTIPLE_PERMISSIONS);
            return false;
        }
        return true;
    }
}