package com.sflib.CustomView.listview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.sflib.CustomView.R;

/**
 * Created by xieningtao on 16-3-25.
 */
public class XPinAdapter extends BaseAdapter {
    private final LayoutInflater mLayoutInflater;

    public XPinAdapter(Context context) {
        mLayoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return 140;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        int type = getItemViewType(position);
        if (type == 0) {
            if (convertView == null) {
                convertView = mLayoutInflater.inflate(R.layout.head_item, null);
            }
            TextView head_tv = (TextView) convertView.findViewById(R.id.head_tv);
            String content = "head position: " + position;
            head_tv.setText(content);
        } else {
            if (convertView == null) {
                convertView = mLayoutInflater.inflate(R.layout.content_item, null);
            }
            TextView content_tv = (TextView) convertView.findViewById(R.id.content_tv);
            String content = "content position: " + position;
            content_tv.setText(content);
        }
        return convertView;
    }

    @Override
    public int getItemViewType(int position) {
        if (position % 15 == 0) return 0;
        else return 1;
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    public String getHeadTitle(int position) {
        int index = position / 15;
        return "head position: " + (index * 15);
    }
}
