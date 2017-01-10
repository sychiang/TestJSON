package com.example.chris.testjson;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 2017/1/8.
 */

public class CustomAdapter extends BaseAdapter {
    private List<Response> petList;
    private Context context;
    private LayoutInflater inflater;

    public CustomAdapter(Context context, List<Response> petList) {
        this.petList = petList;
        this.context = context;
    }

    @Override
    public int getCount() {
        return petList.size();
    }

    @Override
    public Object getItem(int position) {
        return petList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.each_row_item, parent, false);

        Response item = (Response) getItem(position);

        ImageView image = (ImageView) rowView.findViewById(R.id.image);
        TextView type = (TextView) rowView.findViewById(R.id.type);
        TextView age = (TextView) rowView.findViewById(R.id.age);
        TextView location = (TextView) rowView.findViewById(R.id.location);
        TextView date = (TextView) rowView.findViewById(R.id.date);

        String imgURL = item.getAlbum_file();
        Picasso.with(context).load(imgURL).into(image);
        type.setText(item.getAnimal_kind());
        age.setText(item.getAnimal_age());
        location.setText(item.getAnimal_place());
        date.setText(item.getAnimal_opendate());
        return rowView;
    }
}
