package com.example.godiscover;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.format.Formatter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.UnknownHostException;


public class getDataFromDb extends AppCompatActivity {

    String url = "http://192.168.0.164/AplicatieLicenta/index.php";
    String newStr = null;
    ListView listView;
    TextView title, describeLocation, links;
    ImageView ivShowImage;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_data_from_db);

        WifiManager wifiManager = (WifiManager) getApplicationContext().getSystemService(WIFI_SERVICE);
        String ipAddress = Formatter.formatIpAddress(wifiManager.getConnectionInfo().getIpAddress());

        title = (TextView) findViewById(R.id.titleView);
        describeLocation = (TextView) findViewById(R.id.describeView);
        //ivShowImage = (ImageView) findViewById(R.id.ivShowImage);
        //links = (TextView) findViewById(R.id.linkImages);
        //listView = (ListView) findViewById(R.id.lView);

        downloadJSON(url);

        //Toast.makeText(getApplicationContext(), ip , Toast.LENGTH_SHORT).show();

        //downloadImagesLink(url);

        //String link = "http://192.168.40.239/AplicatieLicenta/imagini/tampa.jpg";

//        imageDownloader imgDownloader = new imageDownloader();
//        try {
//            Bitmap image = imgDownloader.execute(link).get();
//            ivShowImage.setImageBitmap(image);
//        } catch (ExecutionException e) {
//            e.printStackTrace();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
    }


    private void downloadJSON(final String urlWebService)
    {

        class DownloadJSON extends AsyncTask<Void, Void, String>
        {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }


            @Override
            protected void onPostExecute(String s)
            {
                super.onPostExecute(s);

                try {
                    loadIntoListView(s);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            protected String doInBackground(Void... voids)
            {
                try {
                    URL url = new URL(urlWebService);
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
                    StringBuilder sb = new StringBuilder();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));
                    String json;
                    while ((json = bufferedReader.readLine()) != null)
                    {
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

    private void loadIntoListView(String json) throws JSONException
    {
        JSONArray jsonArray = new JSONArray(json);
        for (int i = 0; i < jsonArray.length(); i++)
        {
            JSONObject obj = jsonArray.getJSONObject(i);
            title.setText(obj.getString("titlename"));
            describeLocation.setText(obj.getString("describelocation"));
        }
    }

    private void downloadImagesLink(final String imagesLink)
    {
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

    private void jsonToString(String str) throws JSONException
    {
        JSONArray jsArr = new JSONArray(str);
        for(int i = 0; i < jsArr.length(); i++)
        {
            JSONObject jsObj = jsArr.getJSONObject(i);
            newStr = jsObj.optString("imagelink");
        }
//        for(int i = 0; i < jsArr.length(); i++)
//            imageView.setText(imageView.getText() + newStr.get(i) + ", ");
//        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, newStr);
//
//        if (listView != null) {
//            listView.setAdapter(adapter);
//        }
    }

    class imageDownloader extends AsyncTask<String,Void,Bitmap>
    {
        HttpURLConnection httpURLConnection;

        @Override
        protected Bitmap doInBackground(String[] strings)
        {
            try {
                //for(int i = 0; i < strings.length; i++) {
                    URL url = new URL(strings[0]);
                    httpURLConnection = (HttpURLConnection) url.openConnection();
                    InputStream inputStream = new BufferedInputStream(httpURLConnection.getInputStream());
                    Bitmap temp = BitmapFactory.decodeStream(inputStream);
                    return temp;
                //}
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }finally{
                httpURLConnection.disconnect();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap)
        {
            if(bitmap != null)
            {
                ivShowImage.setImageBitmap(bitmap);
                Toast.makeText(getApplicationContext(), "download succesfull", Toast.LENGTH_SHORT).show();
                //for(int i = 0; i < newStr.length; i++)
                  //  links.setText(newStr[i]);
                    //listView.set
            }
            else
            {
                Toast.makeText(getApplicationContext(), "oopsiee", Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        protected void onProgressUpdate(Void...updates)
        {
            super.onProgressUpdate(updates);
        }
    }


}
