package com.example.godiscover;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class CustomListView extends ArrayAdapter<String> {

    private String[] titleName;
    private String[] describeLocation;
    private Activity context;

    public CustomListView(Activity context, String[] titleName, String[] describeLocation) {
        super(context, R.layout.dblayout, titleName);
        this.context = context;
        this.titleName = titleName;
        this.describeLocation = describeLocation;
    }

    @NonNull
    //@Override

    public View GetView(int position, @Nullable View convertView, @NonNull ViewGroup parent)
    {
        View r = convertView;
        ViewHolder viewHolder = null;
        if(r == null)
        {
            LayoutInflater layoutInflater = context.getLayoutInflater();
            r = layoutInflater.inflate(R.layout.dblayout, null, true);
            viewHolder = new ViewHolder(r);
            r.setTag(viewHolder);
        }
        else{
            viewHolder = (ViewHolder)r.getTag();
        }

        viewHolder.tv1.setText(titleName[position]);
        viewHolder.tv2.setText(describeLocation[position]);

        return r;
    }

    class ViewHolder{
        TextView tv1;
        TextView tv2;

        ViewHolder(View v)
        {
            tv1 = (TextView)v.findViewById(R.id.TN);
            tv2 = (TextView)v.findViewById(R.id.DL);
        }
    }

}
