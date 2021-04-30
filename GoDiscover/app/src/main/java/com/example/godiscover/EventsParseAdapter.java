package com.example.godiscover;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class EventsParseAdapter extends RecyclerView.Adapter<EventsParseAdapter.ViewHolder> {

    private ArrayList<EventsParseItem> eventsParseItems;
    public Context context;

    public EventsParseAdapter(ArrayList<EventsParseItem> eventsParseItems, Context context)
    {
        this.eventsParseItems = eventsParseItems;
        this.context = context;
    }

    @NonNull
    @Override
    public EventsParseAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.events_parse_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        EventsParseItem eventsParseItem = eventsParseItems.get(position);
        holder.titlu.setText(eventsParseItem.getTitle());
        holder.locatie.setText(eventsParseItem.getLocation());
        holder.text_date.setText(eventsParseItem.getText_date());
        //holder.imageView.setMaxHeight(Integer.parseInt(eventsParseItem.getImg_height()));
        holder.url_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent web_url = new Intent(Intent.ACTION_VIEW, Uri.parse(eventsParseItem.getWeb_url()));
                context.startActivity(web_url);
            }
        });

        Picasso.get().load(eventsParseItem.getImg_url()).into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return eventsParseItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView imageView;
        TextView titlu;
        TextView locatie;
        TextView text_date;
        Button url_btn;

        public ViewHolder(@NonNull View view)
        {
            super(view);
            imageView = view.findViewById(R.id.imageView);
            titlu = view.findViewById(R.id.title);
            locatie = view.findViewById(R.id.location);
            text_date = view.findViewById(R.id.text_date);
            url_btn = view.findViewById(R.id.url_btn);
        }
    }
}
