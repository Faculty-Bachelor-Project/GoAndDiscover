package com.example.godiscover.locations;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.godiscover.R;
import com.example.godiscover.events;
import com.example.godiscover.img_processing;
import com.example.godiscover.locations.DatabaseParseAdapter;
import com.example.godiscover.locations.DatabaseParseItem;
import com.example.godiscover.login;
import com.example.godiscover.map;
import com.example.godiscover.user;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.squareup.picasso.Picasso;
import com.vishnusivadas.advanced_httpurlconnection.PutData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class getDataFromDb extends AppCompatActivity {

    private static String URL = "http://192.168.0.178/gndApp/index.php";
    TextView title, describeLocation;
    ImageView headerImage;
    RecyclerView recyclerView;
    List<DatabaseParseItem> databaseParseItemsList;
    DatabaseParseAdapter getDataAdapter;
    int id = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_data_from_db);

        recyclerView = (RecyclerView) findViewById(R.id.dbDataList);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);

        headerImage = (ImageView) findViewById(R.id.topImage);
        title = (TextView) findViewById(R.id.dbTitle);
        describeLocation = (TextView) findViewById(R.id.dbDescribeLocation);
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_nav);
        bottomNavigationView.setSelectedItemId(R.id.db_data);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId())
                {
                    case R.id.googleMaps:
                        startActivity(new Intent(getApplicationContext(), map.class));
                        overridePendingTransition(0,0);
                        return true;

                    case R.id.db_data:
                        return true;

                    case R.id.scanner:
                        startActivity(new Intent(getApplicationContext(), img_processing.class));
                        overridePendingTransition(0,0);
                        return true;

                    case R.id.events:
                        startActivity(new Intent(getApplicationContext(), events.class));
                        overridePendingTransition(0,0);
                        return true;

                    case R.id.user:
                        startActivity(new Intent(getApplicationContext(), user.class));
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });

        databaseParseItemsList = new ArrayList<>();
        insertData();
    }

    private void insertData()
    {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                try {
                    JSONArray jsonArr = new JSONArray(response);

                        JSONObject dbObject = jsonArr.getJSONObject(0);
                        JSONArray imagesArray = new JSONArray(jsonArr.getJSONObject(0).getString("images"));
                        String[] imagelink = new String[imagesArray.length()];

                    for (int j = 0; j < imagesArray.length(); j++)
                        imagelink[j] = imagesArray.getJSONObject(j).getString("imagelink");

                        Picasso.get().load(imagelink[0]).into(headerImage);
                        title.setText(dbObject.getString("titlename"));
                        describeLocation.setText(dbObject.getString("describelocation"));

                        DatabaseParseItem databaseParseImages = new DatabaseParseItem();

                    for (int j = 1; j < imagesArray.length(); j++)
                        databaseParseItemsList.add(new DatabaseParseItem(imagelink[j]));

                }catch (JSONException e) {
                    e.printStackTrace();
                }
                recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                getDataAdapter = new DatabaseParseAdapter(getDataFromDb.this, databaseParseItemsList);
                recyclerView.setAdapter(getDataAdapter);
            }
        }, new Response.ErrorListener() {@Override public void onErrorResponse(VolleyError error) {}    }) {
            @Override
            protected Map<String,String> getParams() throws AuthFailureError
            {
                Map<String,String> params = new HashMap<String,String>();
                params.put("id",String.valueOf(id));
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}