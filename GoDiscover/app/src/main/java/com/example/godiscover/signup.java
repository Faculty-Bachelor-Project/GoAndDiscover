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

public class signup extends AppCompatActivity {

    EditText username, email, password, verifyPassword;
    ProgressBar progressBar;
    Button loginButton, registerButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        username = (EditText) findViewById(R.id.usernameText);
        email = (EditText) findViewById(R.id.emailText);
        password = (EditText) findViewById(R.id.passwordText);
        verifyPassword = (EditText) findViewById(R.id.passwordVerify);
        loginButton = (Button) findViewById(R.id.loginBtn);
        progressBar = (ProgressBar) findViewById(R.id.progress);
        registerButton = (Button) findViewById(R.id.signupButton);
        loginButton = (Button) findViewById(R.id.loginBtn);

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Username = username.getText().toString();
                String Email = email.getText().toString();
                String Password = password.getText().toString();
                String VerifyPassword = verifyPassword.getText().toString();

                if(!Username.equals("") && !Password.equals("")) {
                    progressBar.setVisibility(View.VISIBLE);
                    Handler handler = new Handler();
                    handler.post(new Runnable() {

                        @Override
                        public void run() {

                            if(!VerifyPassword.equals(Password))
                            {
                                Toast.makeText(getApplicationContext(),"The Password confirmation does not match" , Toast.LENGTH_SHORT).show();
                            }else {


                                String[] field = new String[3];
                                field[0] = "user";
                                field[1] = "email";
                                field[2] = "pass";

                                String[] data = new String[3];
                                data[0] = Username;
                                data[1] = Email;
                                data[2] = Password;

                                PutData putData = new PutData("http://192.168.0.178/gndapp/signup.php", "POST", field, data);

                                if (putData.startPut()) {
                                    if (putData.onComplete()) {
                                        progressBar.setVisibility(View.GONE);
                                        String result = putData.getResult();
                                        if (result.equals("Sign up Success")) {
                                            Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();
                                            Intent intent = new Intent(signup.this, login.class);
                                            startActivity(intent);
                                            finish();
                                        } else
                                            Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();
                                    }
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

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent homeIntent = new Intent(signup.this, login.class);
                startActivity(homeIntent);
                finish();
            }
        });



    }
}