package com.sf.SFSample.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.basesmartframe.baseadapter.newmultiadapter.BaseMultiChoiceAdapter;
import com.basesmartframe.baseui.BaseActivity;
import com.sf.SFSample.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by NetEase on 2016/7/25 0025.
 */
public class ActivityMultiChoice extends BaseActivity {

    private ListView mChoice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choices);
        mChoice = (ListView) findViewById(R.id.choice_lv);
        mChoice.setAdapter(new MyMultiChoiceAdapter(new MyBaseAdpater(),R.id.choice_iv));
    }

    class MyMultiChoiceAdapter extends BaseMultiChoiceAdapter {

        public MyMultiChoiceAdapter(BaseAdapter baseAdapter, int checkId) {
            super(baseAdapter, checkId);
        }

        @Override
        protected void updateCheckViews(int position, View rootView, View checkedView, boolean state) {
            ImageView checkedIv = (ImageView) checkedView;
            if (state) {
                checkedIv.setImageResource(R.drawable.pickphotos_checkbox_check);
            }else {
                checkedIv.setImageResource(R.drawable.pickphotos_checkbox_uncheck_press);
            }
        }
    }

    class MyBaseAdpater extends BaseAdapter {

        private List<String> mData = new ArrayList<>();

        public MyBaseAdpater() {
            for (int i = 0; i < 20; i++) {
                mData.add("student" + i * 10);
            }
        }

        @Override
        public int getCount() {
            return 20;
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
            if (convertView == null) {
                convertView = LayoutInflater.from(ActivityMultiChoice.this).inflate(R.layout.choice_item, null);
            }
            TextView choiceTv = (TextView) convertView.findViewById(R.id.choice_tv);
            choiceTv.setText(mData.get(position));
            return convertView;
        }
    }
}
