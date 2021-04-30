package com.example.godiscover;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.parse_item, parent, false);
        view.measure(
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.EXACTLY),
                View.MeasureSpec.makeMeasureSpec(100, View.MeasureSpec.UNSPECIFIED));
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        EventsParseItem eventsParseItem = eventsParseItems.get(position);
        holder.textView.setText(eventsParseItem.getTitle());
        Picasso.get().load(eventsParseItem.getImg_url()).into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return eventsParseItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView imageView;
        TextView textView;

        public ViewHolder(@NonNull View view)
        {
            super(view);
            imageView = view.findViewById(R.id.imageView);
            textView = view.findViewById(R.id.textView);
        }
    }
}
