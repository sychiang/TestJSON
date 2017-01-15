package com.example.chris.testjson;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.ParsedRequestListener;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.Toast;
import com.google.gson.reflect.TypeToken;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements OnScrollListener{
    ArrayList<String> petidlist = new ArrayList<String>();
    ArrayList<Response> petlist = new ArrayList<Response>();
    ArrayList<Response> showlist = new ArrayList<Response>();
    ListView listview;
    CustomAdapter adapter;
    String url = "http://data.coa.gov.tw/Service/OpenData/AnimalOpenData.aspx?$top=50";

    //listView 分頁加載
    private Button btn_load;
    private ProgressBar pg;
    // ListView底部View
    private View moreView;
    private Handler handler;
    // 設置一個最大的數據條數，超過即不再加載
    private int MaxDataNum;
    // 最後可見條目的索引
    private int lastVisibleIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listview = (ListView) findViewById(R.id.petlist);
        // 實例化底部布局
        moreView = getLayoutInflater().inflate(R.layout.loadmoredata, null);
        btn_load = (Button) moreView.findViewById(R.id.btn_load);
        pg = (ProgressBar) moreView.findViewById(R.id.pg);
        handler = new Handler();

        //取回JSON資料存入集合
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
                                String size = String.format("%d",response.size());
                                Log.d("Debug",size);
                                for(Response rs : response){
                                    petlist.add(rs);
                                    petidlist.add(rs.getAnimal_id());
                                    Log.d("Debug",rs.getAnimal_id());
                                }
                                MaxDataNum = petlist.size(); // 設置最大數據條數
                                size = String.format("%d",petlist.size());
                                Log.d("Debug",size);
//                                adapter = new CustomAdapter(MainActivity.this, response);
//                                listview.setAdapter(adapter);
                                for(int i = 0; i < 10; i++){
                                    showlist.add(petlist.get(i));
                                }
                                size = String.format("%d",showlist.size());
                                Log.d("Debug",size);

                                // 加上底部View，注意要放在setAdapter方法前
                                listview.addFooterView(moreView);
                                adapter = new CustomAdapter(MainActivity.this, showlist);
                                listview.setAdapter(adapter);
                                // 绑定監聽器
                                listview.setOnScrollListener(MainActivity.this);

                                btn_load.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        pg.setVisibility(View.VISIBLE);// 將進度條可見
                                        btn_load.setVisibility(View.GONE);// 按鈕不可見
                                        handler.postDelayed(new Runnable() {
                                            @Override
                                            public void run() {
                                                loadMoreDate();// 加載更多數據
                                                btn_load.setVisibility(View.VISIBLE);
                                                pg.setVisibility(View.GONE);
                                                adapter.notifyDataSetChanged();// 通知listView刷新數據
                                            }
                                        }, 2000);
                                    }
                                });
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
    private void loadMoreDate() {
        int count = adapter.getCount();
        if (count + 5 < MaxDataNum) {
            // 每次加載5條
            for (int i = count; i < count + 5; i++) {
                showlist.add(petlist.get(i));
            }
        } else {
            // 數據已經不足5條
            for (int i = count; i < MaxDataNum; i++) {
                showlist.add(petlist.get(i));
            }
        }
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        // 滑到底部後自動加載，判斷listview已經停止滾動並且最後可視的條目等於adapter的條目
        if (scrollState == OnScrollListener.SCROLL_STATE_IDLE
                && lastVisibleIndex == adapter.getCount()) {
            // 當滑到底部時自動加載
            // pg.setVisibility(View.VISIBLE);
            // bt.setVisibility(View.GONE);
            // handler.postDelayed(new Runnable() {
            //
            // @Override
            // public void run() {
            // loadMoreDate();
            // bt.setVisibility(View.VISIBLE);
            // pg.setVisibility(View.GONE);
            // mSimpleAdapter.notifyDataSetChanged();
            // }
            //
            // }, 2000);
        }
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        // 計算最後可見條目的索引
        lastVisibleIndex = firstVisibleItem + visibleItemCount - 1;

        // 所有的條目已經和最大條數相等，則移除底部的View
        if (totalItemCount == MaxDataNum + 1) {
            listview.removeFooterView(moreView);
            Toast.makeText(this, "數據全部加載完成，沒有更多數據！", Toast.LENGTH_LONG).show();
        }
    }
}


