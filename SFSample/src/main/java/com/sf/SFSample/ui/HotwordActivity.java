package com.sf.SFSample.ui;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sf.SFSample.R;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.ArrayList;
import java.util.List;

public class HotwordActivity extends Activity {
    /**
     * Called when the activity is first created.
     */
    private List<String> beans = new ArrayList<>();

    private TagFlowLayout mHotWordsView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hotword);

        mHotWordsView = findViewById(R.id.hotwords);

       beans.add("fsjfksljfklsj");
       beans.add("fsjsfsdffksljfklsj");
       beans.add("fsjffdfsdfsfsfafdsfsfdfksljfklsj");
       beans.add("fsjfksljfdfsfklsj");
       beans.add("fsjfksljfojjlkjlkjlkjljlkjljslfjksjfksjflsajflsjaflkaklsj");
       beans.add("fsjfksljfsjfskjfkfklsj");
       beans.add("fsjfksljfsjfskjfkfkfffffffwwlsj");
       beans.add("fsjfksjfkfklsj");
       beans.add("fsjfksljfsjfskjfkfklsj");

       mHotWordsView.setAdapter(new TagAdapter(beans) {
           @Override
           public View getView(FlowLayout parent, int position, Object o) {
               TextView textView = new TextView(HotwordActivity.this);
               textView.setPadding(10, 10, 10, 10);
               String item = (String) getItem(position);
               textView.setText(item);
               return textView;
           }
       });
    }
}
