package com.example.godiscover;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.ProgressBar;

import com.example.godiscover.locations.getDataFromDb;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

public class events extends AppCompatActivity {

    private RecyclerView recyclerView;
    private EventsParseAdapter adapter;
    private ArrayList<EventsParseItem> eventsParseItems = new ArrayList<>();
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events);

        progressBar = findViewById(R.id.progressBar);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new EventsParseAdapter(eventsParseItems, this);
        recyclerView.setAdapter(adapter);

        Content content = new Content();
        content.execute();

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_nav);
        bottomNavigationView.setSelectedItemId(R.id.events);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId())
                {
                    case R.id.googleMaps:
                        startActivity(new Intent(getApplicationContext(),map.class));
                        overridePendingTransition(0,0);
                        return true;

                    case R.id.db_data:
                        startActivity(new Intent(getApplicationContext(), getDataFromDb.class));
                        overridePendingTransition(0,0);
                        return true;

                    case R.id.scanner:
                        startActivity(new Intent(getApplicationContext(),img_processing.class));
                        overridePendingTransition(0,0);
                        return true;

                    case R.id.events:
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

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK ) {
            Intent intent = new Intent(events.this,
                    img_processing.class);
            startActivity(intent);
        }
        return super.onKeyDown(keyCode, event);
    }

    private class Content extends AsyncTask<Void,Void,Void>
    {
        @Override
        public void onPreExecute()
        {
            super.onPreExecute();
            progressBar.setVisibility(View.GONE);
            progressBar.startAnimation(AnimationUtils.loadAnimation(events.this,android.R.anim.fade_in));
        }

        @Override
        public void onPostExecute(Void _void)
        {
            super.onPreExecute();
            progressBar.setVisibility(View.GONE);
            progressBar.startAnimation(AnimationUtils.loadAnimation(events.this,android.R.anim.fade_out));
            adapter.notifyDataSetChanged();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                String url = "https://spectacolebrasov.ro/";
                Document doc = Jsoup.connect(url).get();
                Elements data = doc.select("div.item");
                int size = data.size();
                for(int i = 0; i < size; i++)
                {
                    String imgUrl = data.select("p.poster")
                            .select("a")
                            .eq(i)
                            .attr("href");
                    String img_width = data.select("p.poster")
                            .select("img")
                            .eq(i)
                            .attr("width");
                    String img_height = data.select("p.poster")
                            .select("img")
                            .eq(i)
                            .attr("height");
                    String title = data.select("h1")
                            .select("a")
                            .eq(i)
                            .text();
                    String location = data.select("div.detail_box")
                            .select("p.locatie")
                            .eq(i)
                            .text();
                    String text_date = data.select("div.info_left")
                            .select("span.info_first_row")
                            .eq(i)
                            .text();
                    String url_btn = data.select("h1")
                            .select("a")
                            .eq(i)
                            .attr("href");
                    eventsParseItems.add(new EventsParseItem(imgUrl,img_width,img_height,title,location,text_date,url_btn));
                    //Log.d("items", "img: " + imgUrl + " . title: " + title + " . width: " + img_width + " . height: " + img_height + " . location: " + location + ". url_btn: " + url_btn);
                }
            }catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }
}