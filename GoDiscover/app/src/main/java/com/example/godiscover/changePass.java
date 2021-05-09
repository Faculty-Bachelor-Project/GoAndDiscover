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

public class changePass extends AppCompatActivity {

    String getUser = "";
    private static String URL = "http://192.168.0.108/gndApp/changePassword.php";
    EditText oldpassword, newpassword, confirmpassword;
    ProgressBar progressBar;
    Button changepass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_pass);

        Intent intent = getIntent();
        getUser = intent.getStringExtra("extraString");

        oldpassword = (EditText) findViewById(R.id.oldpass);
        newpassword = (EditText) findViewById(R.id.newpass);
        confirmpassword = (EditText) findViewById(R.id.confpass);
        progressBar = (ProgressBar) findViewById(R.id.progress);
        changepass = (Button) findViewById(R.id.changeButton);

        changepass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String oldPassword = oldpassword.getText().toString();
                String newPassword = newpassword.getText().toString();
                String confirmPassword = confirmpassword.getText().toString();

                if(!oldPassword.equals("") && !newPassword.equals("") && !confirmPassword.equals("")) {
                    progressBar.setVisibility(View.GONE);
                    Handler handler = new Handler();
                    handler.post(new Runnable() {

                        @Override
                        public void run() {

                            if(!confirmPassword.equals(newPassword))
                            {
                                Toast.makeText(getApplicationContext(),"The Password confirmation does not match" , Toast.LENGTH_SHORT).show();
                            }else {


                                String[] field = new String[2];
                                field[0] = "user";
                                field[1] = "newpassw";

                                String[] data = new String[2];
                                data[0] = getUser;
                                data[1] = newPassword;

                                PutData putData = new PutData(URL, "POST", field, data);

                                if (putData.startPut()) {
                                    if (putData.onComplete()) {
                                        progressBar.setVisibility(View.GONE);
                                        String result = putData.getResult();
                                        if (result.equals("Change Password Success")) {
                                            Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();
                                            Intent intent = new Intent(changePass.this, user.class);
                                            intent.putExtra("extraString", getUser);
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
    }
}