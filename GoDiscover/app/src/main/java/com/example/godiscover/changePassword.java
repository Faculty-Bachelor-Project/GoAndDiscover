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

import com.vishnusivadas.advanced_httpurlconnection.PutData;

public class changePassword extends AppCompatActivity {

    private EditText newPassword, confirmNewPassword;
    private ProgressBar progressBar;
    private Button verifyButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        Intent intent = getIntent();
        String getEmailText = intent.getStringExtra("EXTRA_TEXT");

        newPassword = (EditText) findViewById(R.id.newPassword);
        confirmNewPassword = (EditText) findViewById(R.id.confirmNewPassword);

        progressBar = (ProgressBar) findViewById(R.id.progressbar);
        verifyButton = (Button) findViewById(R.id.verifyButton);


        verifyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String NewPassword = newPassword.getText().toString();
                String ConfirmNewPassword = confirmNewPassword.getText().toString();

                if(NewPassword.trim().isEmpty() || ConfirmNewPassword.trim().isEmpty())
                {
                    Toast.makeText(getApplicationContext(), "One or more fields are empty", Toast.LENGTH_SHORT).show();
                    return;
                }else if(!ConfirmNewPassword.equals(NewPassword)) {
                    Toast.makeText(getApplicationContext(), "The Password confirmation does not match", Toast.LENGTH_SHORT).show();
                }
                else {
                    if (!NewPassword.equals("")) {
                        progressBar.setVisibility(View.VISIBLE);
                        Handler handler = new Handler();
                        handler.post(new Runnable() {

                            @Override
                            public void run() {
                                String[] field = new String[2];
                                field[0] = "newPassword";
                                field[1] = "email";

                                String[] data = new String[2];
                                data[0] = NewPassword;
                                data[1] = getEmailText;

                                PutData putData = new PutData("http://192.168.0.178/gndApp/resetPassword.php", "POST", field, data);

                                if (putData.startPut()) {
                                    if (putData.onComplete()) {
                                        progressBar.setVisibility(View.GONE);
                                        String result = putData.getResult();
                                        if (result.equals("Password updated")) {
                                            Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();
                                            Intent intent = new Intent(changePassword.this, login.class);
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
            }
        });
    }
}