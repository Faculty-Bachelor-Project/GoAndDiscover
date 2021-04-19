package com.example.godiscover;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;
import com.vishnusivadas.advanced_httpurlconnection.PutData;


public class login extends AppCompatActivity {

    EditText username;
    EditText password;
    ProgressBar progressBar;
    Button loginBt, signupButton, forgotBt;

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

        loginBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Username = username.getText().toString();
                String Password = password.getText().toString();

                if(!Username.equals("") && !Password.equals("")) {
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

                            PutData putData = new PutData("http://192.168.0.178/gndApp/login.php", "POST", field, data);

                            if (putData.startPut()) {
                                if (putData.onComplete()) {
                                    progressBar.setVisibility(View.GONE);
                                    String result = putData.getResult();
                                    if (result.equals("Login Success")) {
                                        Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(login.this, HomeActivity.class);
                                        startActivity(intent);
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
}