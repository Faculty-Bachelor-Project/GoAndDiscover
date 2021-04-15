package com.example.godiscover;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
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

    EditText emailAddress;
    ProgressBar progressBar;
    Button resetPasswordBtn, registerButton;
    StringRequest stringRequest;
    String email;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        emailAddress = (EditText) findViewById(R.id.emailText);
        progressBar = (ProgressBar) findViewById(R.id.progressbar);
        resetPasswordBtn = (Button) findViewById(R.id.resetPass);
        registerButton = (Button) findViewById(R.id.registerBtn);

        email = emailAddress.toString();

        resetPasswordBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stringRequest = new StringRequest(Request.Method.POST, "http://192.168.0.178/AplicatieLicenta/forgotPassword.php", new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response{
                        if(response.equals("SUCCESS"))
                            Toast.makeText(getApplicationContext(), "Email successfully sent. Please check your email inbox", Toast.LENGTH_SHORT).show();
                        else
                            Toast.makeText(getApplicationContext(), "Failed", Toast.LENGTH_SHORT).show();
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), "Please check connection", Toast.LENGTH_SHORT).show();
                    }
                }){
                        @Override
                      protected Map<String, String> getParams() throws AuthFailureError{
                            Map<String,String> params = new HashMap<>();
                            params.put("email", email);
                          return super.getParams();
                      }
                };
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




}