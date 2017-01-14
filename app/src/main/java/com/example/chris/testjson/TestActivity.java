package com.example.chris.testjson;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class TestActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        Intent intent = getIntent();
        String animalID = intent.getExtras().getString(CDictionary.BK_animalid);
//        TextView txtAnimalId = (TextView)findViewById(R.id.txtAnimalId);
//        txtAnimalId.setText(animalID);
    }
}
