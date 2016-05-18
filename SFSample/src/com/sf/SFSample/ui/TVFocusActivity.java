package com.sf.SFSample.ui;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.basesmartframe.baseui.BaseActivity;
import com.basesmartframe.log.L;
import com.sf.SFSample.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xieningtao on 16-3-17.
 */
public class TVFocusActivity extends BaseActivity {

    private ListView mListView;

    private View mRightBt, mLeftBt;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_focus_tv);
        mListView = (ListView) findViewById(R.id.focus_list);
        mRightBt = findViewById(R.id.right_bt);
        mLeftBt = findViewById(R.id.left_bt);
        mListView.setAdapter(new FocusAdapter());
        mListView.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                L.info(TAG, "method->setOnKeyListener,mListView,onkey: " + keyCode);
                View focusChild = mListView.findFocus();
                if (focusChild != null) {
                    String className = focusChild.getClass().getName();
                    L.info(TAG, "method->setOnKeyListener,mListView focusChild class name: " + className);
                }
                return false;
            }
        });
        mListView.setNextFocusLeftId(R.id.left_bt);
        mListView.setNextFocusRightId(R.id.right_bt);

        mListView.setNextFocusUpId(R.id.left_bt);

        mListView.setItemsCanFocus(true);


    }

    class FocusAdapter extends BaseAdapter {
        private List<String> items = new ArrayList<>();

        public FocusAdapter() {
            for (int i = 0; i < 10; i++) {
                items.add("item" + i);
            }
        }

        @Override
        public int getCount() {
            return items.size();
        }

        @Override
        public Object getItem(int position) {
            return items.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = LayoutInflater.from(TVFocusActivity.this).inflate(R.layout.item_focus_tv, null);
            }
            TextView item_tv = (TextView) convertView.findViewById(R.id.item_tv);
            String item_str = items.get(position);
            item_tv.setText(item_str);
            if (position % 2 == 0) {
                convertView.setNextFocusLeftId(R.id.right_bt);
            } else {
                convertView.setNextFocusRightId(R.id.left_bt);
            }
            convertView.setOnKeyListener(new View.OnKeyListener() {
                @Override
                public boolean onKey(View v, int keyCode, KeyEvent event) {
                    L.info(TAG, "method->setOnKeyListener,convertView,onkey: " + keyCode);
                    return false;
                }
            });
            return convertView;
        }
    }
}