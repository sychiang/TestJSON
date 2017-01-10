package com.example.chris.testjson;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

    ListView listviw;
    Response[] responseObj;
    List<Response> responselist;
    CustomAdapter adapter;
    Gson gson = new Gson();
    String url = "http://data.coa.gov.tw/Service/OpenData/AnimalOpenData.aspx";
    AsyncHttpClient client = new AsyncHttpClient();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listviw = (ListView) findViewById(R.id.petlist);
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

        AndroidNetworking.initialize(getApplicationContext());
        AndroidNetworking.get(url)
                .setTag(this)
                .setPriority(Priority.HIGH)
                .build()
                .getAsParsed(new TypeToken<ArrayList<Response>>() {
                             },
                        new ParsedRequestListener<ArrayList<Response>>() {
                            @Override
                            public void onResponse(final ArrayList<Response> response) {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        //setupRecyclerView(response);
                                        adapter = new CustomAdapter(MainActivity.this, response);
                                        listviw.setAdapter(adapter);
                                    }
                                });
                            }

                            @Override
                            public void onError(ANError anError) {

                            }
                        });


    }
}
