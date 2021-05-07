package com.example.godiscover.locations;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.godiscover.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class DatabaseParseAdapter extends RecyclerView.Adapter<DatabaseParseAdapter.ViewHolder> {
    LayoutInflater inflater;
    List<DatabaseParseItem> databseParseItems;

    public DatabaseParseAdapter(Context context, List<DatabaseParseItem> databseParseItemList) { this.inflater = LayoutInflater.from(context); this.databseParseItems = databseParseItemList; }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.dblayout,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Picasso.get().load(databseParseItems.get(position).getImages()).into(holder.images);
    }

    @Override
    public int getItemCount() {
        return databseParseItems.size();
    }

    public  class ViewHolder extends  RecyclerView.ViewHolder {

        ImageView images;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            images = itemView.findViewById(R.id.dbImages);
        }
    }
}
