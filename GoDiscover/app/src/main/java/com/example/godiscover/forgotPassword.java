package com.example.godiscover;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.vishnusivadas.advanced_httpurlconnection.PutData;

import java.util.HashMap;
import java.util.Map;

public class forgotPassword extends AppCompatActivity {

    //public static final String EXTRA_TEXT = "com.example.godiscover.EXTRA_TEXT";
    EditText emailAddress;
    ProgressBar progressBar;
    Button resetPasswordBtn, registerButton;
    private static String URL = "http://192.168.0.178/gndApp/forgotPassword.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        emailAddress = (EditText) findViewById(R.id.emailEditText);
        progressBar = (ProgressBar) findViewById(R.id.progressbar);
        resetPasswordBtn = (Button) findViewById(R.id.resetPass);
        registerButton = (Button) findViewById(R.id.registerBtn);

        resetPasswordBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Email = emailAddress.getText().toString();

                if(!Email.equals("")) {
                    progressBar.setVisibility(View.VISIBLE);
                    Handler handler = new Handler();
                    handler.post(new Runnable() {

                        @Override
                        public void run() {
                            String[] field = new String[1];
                            field[0] = "email";

                            String[] data = new String[1];
                            data[0] = Email;

                            PutData putData = new PutData(URL, "POST", field, data);

                            if (putData.startPut()) {
                                if (putData.onComplete()) {

                                    progressBar.setVisibility(View.GONE);
                                    String result = putData.getResult();

                                    if (result.equals("Email sent successfully")) {
                                        Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(forgotPassword.this, verifyOtpCode.class);
                                        intent.putExtra("EXTRA_TEXT", Email);
                                        startActivity(intent);
                                        finish();
                                    } else
                                        Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();
                                }
                            }
                        }
                    });
                }
            }
        });

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent homeIntent = new Intent(forgotPassword.this, signup.class);
                startActivity(homeIntent);
                finish();
            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK ) {
            Intent intent = new Intent(forgotPassword.this,
                    login.class);
            startActivity(intent);
        }
        return super.onKeyDown(keyCode, event);
    }
}