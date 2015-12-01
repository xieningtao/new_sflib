package com.sf.yysdkdemo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.gson.Gson;
import com.sf.yysdkdemo.bean.Detail;
import com.sfhttpclient.FinalHttp;
import com.sfhttpclient.core.AjaxCallBack;
import com.sfhttpclient.core.AjaxParams;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by xieningtao on 15-11-6.
 */
public class YYSdkHome extends Activity implements AdapterView.OnItemClickListener {
    private final String HOST_URI = "http://api.m.huya.com/channel/game";
    private ListView mListView;
    private ArrayAdapter<String> mAdapter;
    private List<String> items = new ArrayList<>();
    private List<Detail.TVDetailListBean> beans = new ArrayList<>();
    private String TAG = YYSdkHome.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_activity);
        init();
    }

    private void init() {
        mListView = (ListView) findViewById(R.id.list);
        mListView.setOnItemClickListener(this);
        request();
    }

    private void request() {
        FinalHttp finalHttp = new FinalHttp();
        AjaxParams params = new AjaxParams();
        params.put("game_id", "1");
        params.put("pageSize", "15");
        params.put("page", "1");
        finalHttp.get(HOST_URI, params, new AjaxCallBack<String>() {
            @Override
            public void onSuccess(String s) {
                super.onSuccess(s);
                Gson gson = new Gson();

                Detail detail = gson.fromJson(s, Detail.class);
                beans.clear();
                beans.addAll(detail.data);
                items.clear();
                for (int i = 0; i < beans.size(); i++) {
                    Detail.TVDetailListBean bean = beans.get(i);
                    items.add(bean.getsNick());
                }
                mAdapter = new ArrayAdapter<String>(YYSdkHome.this, android.R.layout.simple_list_item_1, items.toArray(new String[0]));
                mListView.setAdapter(mAdapter);
            }

            @Override
            public void onFailure(Throwable t, int errorNo, String strMsg) {
                super.onFailure(t, errorNo, strMsg);
                Log.e(TAG, "method->onFailure, strMsg: " + strMsg);
            }
        });
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Detail.TVDetailListBean bean = beans.get(position);
        Intent intent = new Intent(this, YYSdkLivingActivity.class);
        intent.putExtra("channel", (Serializable) bean);
        startActivity(intent);
    }

    private void toEmptyActivity() {
        Intent intent = new Intent(this, EmptyActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        cont = 0;
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                onItemClick(null, null, 0, 0);
////                toEmptyActivity();
//            }
//        }, 3 * 1000);
    }

    private int cont = 0;

    @Override
    public void onBackPressed() {
        cont++;
        if (cont > 10) {
            super.onBackPressed();
        }
    }
}
