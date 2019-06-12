package com.sf.SFSample.ui;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sf.SFSample.R;
import com.sflib.CustomView.viewgroup.HotWordsView;

import java.util.ArrayList;
import java.util.List;

public class HotwordActivity extends Activity {
    /**
     * Called when the activity is first created.
     */
    private List<String> beans = new ArrayList<>();

    private HotWordsView mHotWordsView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hotword);

        mHotWordsView = (HotWordsView) findViewById(R.id.hotwords);

       beans.add("fsjfksljfklsj");
       beans.add("fsjsfsdffksljfklsj");
       beans.add("fsjffdfsdfsfsfafdsfsfdfksljfklsj");
       beans.add("fsjfksljfdfsfklsj");
       beans.add("fsjfksljfojjlkjlkjlkjljlkjljslfjksjfksjflsajflsjaflkaklsj");
       beans.add("fsjfksljfsjfskjfkfklsj");
       beans.add("fsjfksljfsjfskjfkfkfffffffwwlsj");
       beans.add("fsjfksjfkfklsj");
       beans.add("fsjfksljfsjfskjfkfklsj");

        mHotWordsView.setAdapter(new HotWordsView.HotWordsAdapter() {
            @Override
            public View getView(ViewGroup parent, int position) {
                TextView textView = new TextView(HotwordActivity.this);
                textView.setPadding(10, 10, 10, 10);
                String item = (String) getItem(position);
                textView.setText(item);
                return textView;
            }

            @Override
            public int getCount() {
                return beans.size();
            }

            @Override
            public Object getItem(int position) {
                return beans.get(position);
            }
        });
    }
}
