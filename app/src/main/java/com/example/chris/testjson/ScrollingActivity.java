package com.example.chris.testjson;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.ParsedRequestListener;
import com.google.gson.reflect.TypeToken;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class ScrollingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrolling);
        init();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        Intent intent = getIntent();
        String animalID = intent.getExtras().getString(CDictionary.BK_animalid);

        AndroidNetworking.initialize(getApplicationContext());
        AndroidNetworking.get("http://data.coa.gov.tw/Service/OpenData/AnimalOpenData.aspx?")
                .addQueryParameter("$filter", "animal_id+like+"+animalID)         //前面放參數變數 後面放參數值
                .setTag(this)
                .setPriority(Priority.HIGH)
                .build()
                .getAsParsed(new TypeToken<ArrayList<Response>>() {
                             },
                        new ParsedRequestListener<ArrayList<Response>>() {
                            @Override
                            public void onResponse( ArrayList<Response> response) {
                                //setupRecyclerView(response);
                                String kind, sex, bodytype, color, age, place, sheltername, shelteraddress, sheltertel, remark;
                                for(Response rs : response){
                                    tvID.setText(rs.getAnimal_id());
                                    tvKind.setText(rs.getAnimal_kind());
                                    switch (rs.getAnimal_sex()){
                                        case "M":
                                            sex = "公";
                                            break;
                                        case "F":
                                            sex = "母";
                                            break;
                                        default:
                                            sex = "";
                                    }
                                    tvSex.setText(sex);
                                    switch (rs.getAnimal_bodytype()){
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
                                    tvBodyType.setText(bodytype);
                                    tvColor.setText(rs.getAnimal_colour());
                                    switch (rs.getAnimal_age()){
                                        case "ADULT":
                                            age = "成年";
                                            break;
                                        case "CHILD":
                                            age = "幼年";
                                            break;
                                        default:
                                            age = "";
                                    }
                                    tvAge.setText(age);
                                    tvPlace.setText(rs.getAnimal_place());
                                    tvShelterName.setText(rs.getShelter_name());
                                    tvShelterTel.setText(rs.getShelter_tel());
                                    tvShelterAddress.setText(rs.getShelter_address());
                                    tvRemark.setText(rs.getAnimal_remark());
                                    if(rs.getAlbum_file().length()>0){
                                        Picasso.with(ScrollingActivity.this).load(rs.getAlbum_file())
                                                .into(ivPhoto);
                                    }
                                }
                            }
                            @Override
                            public void onError(ANError anError) {
                            }
                        });
    }

    private void init(){
        ivPhoto = (ImageView)findViewById(R.id.imgDog);
        tvID = (TextView)findViewById(R.id.tvID);
        tvKind = (TextView)findViewById(R.id.tvKind);
        tvSex = (TextView)findViewById(R.id.tvSex);
        tvBodyType = (TextView)findViewById(R.id.tvBodyType);
        tvColor = (TextView)findViewById(R.id.tvColor);
        tvAge = (TextView)findViewById(R.id.tvAge);
        tvPlace = (TextView)findViewById(R.id.tvPlace);
        tvShelterName = (TextView)findViewById(R.id.tvShelterName);
        tvShelterAddress = (TextView)findViewById(R.id.tvShelterAddress);
        tvShelterTel = (TextView)findViewById(R.id.tvShelterTel);
        tvRemark = (TextView)findViewById(R.id.tvRemark);
    }
    ImageView ivPhoto;
    TextView tvID, tvKind, tvSex, tvBodyType, tvColor, tvAge, tvPlace, tvShelterName, tvShelterAddress, tvShelterTel, tvRemark;

}
