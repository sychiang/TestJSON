package com.example.chris.testjson;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.ParsedRequestListener;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.client.cache.Resource;

import java.util.ArrayList;
import java.util.List;

//import cz.msebera.android.httpclient.entity.mime.Header;

public class MainActivity extends AppCompatActivity {
    ArrayList<String> petidlist = new ArrayList<String>();
    ArrayList<Response> petlist;
    ListView listview;
    CustomAdapter adapter;
    Response[] responseObj;
    Gson gson = new Gson();
    AsyncHttpClient client = new AsyncHttpClient();
    //String url = "http://data.coa.gov.tw/Service/OpenData/AnimalOpenData.aspx";
    String url = "http://data.coa.gov.tw/Service/OpenData/AnimalOpenData.aspx?$top=50";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listview = (ListView) findViewById(R.id.petlist);
        //client.setBasicAuth("iiisyc92","P@ssw0rd92/token"); 如果存取資料需要有帳號密碼, 要寫這行
        /*client.get(MainActivity.this, url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String responsestr = new String(responseBody);
                responseObj = gson.fromJson(responsestr, Response[].class);
                for(int i = 0; i<responseObj.length;i++){
                    responselist.add(responseObj[i]);
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                System.out.println("Conn Fail, statuscode:" + statusCode);
            }
        });*/
        //Testing GitHub
        //Testing GitHub from Mac

        AndroidNetworking.initialize(getApplicationContext());
        AndroidNetworking.get(url)
                .setTag(this)
                .setPriority(Priority.HIGH)
                .build()
                .getAsParsed(new TypeToken<ArrayList<Response>>() {
                             },
                        new ParsedRequestListener<ArrayList<Response>>() {
                            @Override
                            public void onResponse( ArrayList<Response> response) {
                                //setupRecyclerView(response);
                                for(Response rs : response){
                                    petidlist.add(rs.getAnimal_id());
                                    Log.d("Debug",rs.getAnimal_id());
                                }
                                adapter = new CustomAdapter(MainActivity.this, response);
                                listview.setAdapter(adapter);
                            }
                            @Override
                            public void onError(ANError anError) {
                            }
                        });

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Bundle bundle = new Bundle();
                bundle.putString(CDictionary.BK_animalid, petidlist.get(position));
                Log.d("Debug",petidlist.get(position));
                //Intent intent = new Intent(MainActivity.this, ScrollingActivity.class);
                //Intent intent = new Intent(MainActivity.this, TestActivity.class);
                Intent intent = new Intent(MainActivity.this, ScrollingActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }

}
