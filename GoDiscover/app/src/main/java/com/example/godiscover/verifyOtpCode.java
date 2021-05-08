package com.example.godiscover;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.vishnusivadas.advanced_httpurlconnection.PutData;

public class verifyOtpCode extends AppCompatActivity {

    //public static final String EXTRA_TEXT = "com.example.godiscover.EXTRA_TEXT";
    private EditText input1, input2, input3, input4, input5;
    private ProgressBar progressBar;
    private Button verifyButton;
    private static String URL = "http://192.168.0.178/gndApp/resetPasswordWithOTP.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_otp_code);

        Intent intent = getIntent();
        String getEmailText = intent.getStringExtra("EXTRA_TEXT");

        input1 = (EditText) findViewById(R.id.input1);
        input2 = (EditText) findViewById(R.id.input2);
        input3 = (EditText) findViewById(R.id.input3);
        input4 = (EditText) findViewById(R.id.input4);
        input5 = (EditText) findViewById(R.id.input5);
        progressBar = (ProgressBar) findViewById(R.id.progressbar);
        verifyButton = (Button) findViewById(R.id.verifyButton);

        setupOTPInpus();

        verifyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(input1.getText().toString().trim().isEmpty()
                        || input2.getText().toString().trim().isEmpty()
                        || input3.getText().toString().trim().isEmpty()
                        || input4.getText().toString().trim().isEmpty()
                        || input5.getText().toString().trim().isEmpty())
                {
                    Toast.makeText(getApplicationContext(), "Please enter valid code", Toast.LENGTH_SHORT).show();
                    return;
                }
                String OTP_Code = input1.getText().toString() + input2.getText().toString() + input3.getText().toString() + input4.getText().toString() + input5.getText().toString();

                if(!OTP_Code.equals("")) {
                    progressBar.setVisibility(View.VISIBLE);
                    Handler handler = new Handler();
                    handler.post(new Runnable() {

                        @Override
                        public void run() {
                            String[] field = new String[1];
                            field[0] = "otp_code";

                            String[] data = new String[1];
                            data[0] = OTP_Code;

                            PutData putData = new PutData(URL, "POST", field, data);

                            String sessionEmail = getIntent().getStringExtra("EXTRA_SESSION_ID");

                            if (putData.startPut()) {
                                if (putData.onComplete()) {
                                    progressBar.setVisibility(View.GONE);
                                    String resultForgotPage = putData.getResult();
                                    if (resultForgotPage.equals("Verification success")) {
                                        Toast.makeText(getApplicationContext(), "Verification success", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(verifyOtpCode.this, resetPassword.class);
                                        intent.putExtra("EXTRA_TEXT", getEmailText);
                                        startActivity(intent);
                                        finish();
                                    } else
                                        Toast.makeText(getApplicationContext(), resultForgotPage, Toast.LENGTH_SHORT).show();
                                }
                            }
                        }
                    });
                }
            }
        });
    }

    private void setupOTPInpus()
    {
        input1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!s.toString().trim().isEmpty())
                {
                    input2.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        input2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!s.toString().trim().isEmpty())
                {
                    input3.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        input3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!s.toString().trim().isEmpty())
                {
                    input4.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        input4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!s.toString().trim().isEmpty())
                {
                    input5.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }
}