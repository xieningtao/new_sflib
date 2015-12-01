package com.sf.SFSample.ui;

import android.content.Intent;
import android.util.Pair;
import android.util.SparseArray;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.basesmartframe.anim.animation.DoActivityAnim;
import com.basesmartframe.baseui.BaseActivity;
import com.sf.SFSample.R;

/**
 * Created by xieningtao on 15-11-16.
 */
abstract public class BaseLevelActivity extends BaseActivity {
    private SparseArray<Pair<String, Class>> mActivities = new SparseArray<>();
    private ListView mListView;

    protected void onCreate(android.os.Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SparseArray<Pair<String, Class>> activities = getActivities();
        if (activities != null) {
            mActivities = activities;
        }
        mListView = (ListView) findViewById(R.id.list_view);
        String names[] = getItemNames();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, names);
        mListView.setAdapter(adapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Class activity_class = getItemClass(position);
                Intent intent = new Intent(BaseLevelActivity.this,
                        activity_class);
                DoActivityAnim.BOTTOM_IN_OUT.doStartActivityAnimation(
                        BaseLevelActivity.this, intent);
            }
        });
    }

    private String[] getItemNames() {
        String names[] = new String[mActivities.size()];
        for (int i = 0; i < mActivities.size(); i++) {
            names[i] = mActivities.get(i).first;
        }
        return names;
    }

    private Class getItemClass(int position) {
        return mActivities.get(position).second;
    }

    abstract protected SparseArray<Pair<String, Class>> getActivities();

}
