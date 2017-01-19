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

        ImageView ivImage = (ImageView) rowView.findViewById(R.id.image);
        TextView tvType = (TextView) rowView.findViewById(R.id.type);
        TextView tvAge = (TextView) rowView.findViewById(R.id.age);
        TextView tvLocation = (TextView) rowView.findViewById(R.id.location);
        TextView tvDate = (TextView) rowView.findViewById(R.id.date);

        String bodytype="";
        switch (item.getAnimal_bodytype()){
            case "MINI":
                bodytype = "MINI";
                break;
            case "SMALL":
                bodytype = "小型";
                break;
            case "MEDIUM":
                bodytype = "中型";
                break;
            case "BIG":
                bodytype = "大型";
                break;
             default:
                bodytype = "一般";
        }
        String age="";
        switch (item.getAnimal_age()){
            case "ADULT":
                age = "成年";
                break;
            case "CHILD":
                age = "幼年";
                break;
            default:
                age = "";
        }


        String imgURL = item.getAlbum_file();
        if(imgURL.length()>0){
            Picasso.with(context).load(imgURL).into(ivImage);
        }
        tvType.setText(bodytype+item.getAnimal_kind());
        tvAge.setText(age);
        tvLocation.setText(item.getShelter_name().substring(0,3));
        tvDate.setText(item.getAnimal_opendate());
        return rowView;
    }
}
