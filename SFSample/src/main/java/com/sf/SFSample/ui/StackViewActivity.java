package com.sf.SFSample.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.StackView;

import com.basesmartframe.baseui.BaseActivity;
import com.sf.SFSample.R;

/**
 * Created by g8876 on 2017/9/8.
 */

public class StackViewActivity extends BaseActivity {
    private StackView mStackView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.stackview_activity);
        mStackView= findViewById(R.id.stackview);
        mStackView.setAdapter(new StackViewAdapter());
    }

    class StackViewAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return 10;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if(convertView==null){
                convertView=LayoutInflater.from(StackViewActivity.this).inflate(R.layout.stack_item_view,null);
            }
            return convertView;
        }
    }
}
