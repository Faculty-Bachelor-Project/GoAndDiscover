package com.example.godiscover;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;


public class getDataFromDb extends AppCompatActivity {

    ArrayList<String> newStr = new ArrayList<>();
    String[] imageLinks = new String[newStr.size()];
    String url = "http://192.168.0.178/gndApp/index.php";
    String linkStr = "http://192.168.0.178/gndApp/imagini/tampa.jpg";
    //ListView listView;
    TextView title, describeLocation;
    ImageView ivShowImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_data_from_db);

        title = (TextView) findViewById(R.id.titleView);
        describeLocation = (TextView) findViewById(R.id.describeView);
        ivShowImage = (ImageView) findViewById(R.id.ivShowImage);
        //listView = (ListView) findViewById(R.id.lView);

        downloadJSON(url);
        downloadImagesLink(url);


        imageDownloader imgDownloader = new imageDownloader();
        try {
            Bitmap image = imgDownloader.execute(linkStr).get();
            ivShowImage.setImageBitmap(image);
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_nav);
        bottomNavigationView.setSelectedItemId(R.id.db_data);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId())
                {
                    case R.id.db_data:
                        return true;

                    case R.id.scanner:
                        startActivity(new Intent(getApplicationContext(),img_processing.class));
                        overridePendingTransition(0,0);
                        return true;

                    case R.id.user:
                        startActivity(new Intent(getApplicationContext(),user.class));
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });


    }


    private void downloadJSON(final String urlWebService) {

        class DownloadJSON extends AsyncTask<Void, Void, String> {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }


            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);

                try {
                    loadIntoListView(s);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            protected String doInBackground(Void... voids) {
                try {
                    URL url = new URL(urlWebService);
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
                    StringBuilder sb = new StringBuilder();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));
                    String json;
                    while ((json = bufferedReader.readLine()) != null) {
                        sb.append(json + "\n");
                    }
                    return sb.toString().trim();
                } catch (Exception e) {
                    return null;
                }
            }
        }
        DownloadJSON getJSON = new DownloadJSON();
        getJSON.execute();
    }

    private void loadIntoListView(String json) throws JSONException {
        JSONArray jsonArray = new JSONArray(json);
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject obj = jsonArray.getJSONObject(i);
            title.setText(obj.getString("titlename"));
            describeLocation.setText(obj.getString("describelocation"));
        }
    }

    private void downloadImagesLink(final String imagesLink) {
        class DownloadImagesLink extends AsyncTask<Void, Void, String> {

            /*@Override
            protected void onPreExecute() {
                super.onPreExecute();
            }*/

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);

                try {
                    jsonToString(s);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            protected String doInBackground(Void... voids) {
                try {
                    URL url = new URL(imagesLink);
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
                    StringBuilder sb = new StringBuilder();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));
                    String json;
                    while ((json = bufferedReader.readLine()) != null) {
                        sb.append(json + "\n");
                    }
                    return sb.toString().trim();
                } catch (Exception e) {
                    return null;
                }
            }
        }
        DownloadImagesLink getJSON = new DownloadImagesLink();
        getJSON.execute();
    }

    private void jsonToString(String str) throws JSONException {
        JSONArray jsArr = new JSONArray(str);

        for (int i = 1; i < jsArr.length(); i++) {
            JSONObject jsObj = jsArr.getJSONObject(i);
            newStr.add(jsObj.optString("imagelink"));
        }

/*        for (int j = 0; j < newStr.size(); j++) {
            imageLinks[j] = newStr.get(j);
        }*/
//        for(int i = 0; i < jsArr.length(); i++)
//            imageView.setText(imageView.getText() + newStr.get(i) + ", ");
//        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, newStr);
//
//        if (listView != null) {
//            listView.setAdapter(adapter);
//        }
    }

    class imageDownloader extends AsyncTask<String,Void,Bitmap> {
        HttpURLConnection httpURLConnection;

        @Override
        protected Bitmap doInBackground(String[] strings) {
            try {
                    URL url = new URL(strings[0]);
                    httpURLConnection = (HttpURLConnection) url.openConnection();
                    InputStream inputStream = new BufferedInputStream(httpURLConnection.getInputStream());
                    Bitmap temp = BitmapFactory.decodeStream(inputStream);
                    return temp;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                httpURLConnection.disconnect();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            if (bitmap != null) {
                ivShowImage.setImageBitmap(bitmap);
                Toast.makeText(getApplicationContext(), "download succesfull", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getApplicationContext(), "oopsiee", Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        protected void onProgressUpdate(Void... updates) {
            super.onProgressUpdate(updates);
        }
    }
}
