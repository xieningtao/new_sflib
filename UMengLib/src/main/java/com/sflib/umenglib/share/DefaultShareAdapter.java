package com.sflib.umenglib.share;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.sflib.umenglib.R;
import com.sflib.umenglib.share.sharecore.BaseShareAdapter;
import com.sflib.umenglib.share.shareitem.XBaseShareItem;


/**
 * Created by xieningtao on 15-8-14.
 */
public final class DefaultShareAdapter implements BaseShareAdapter {
    @Override
    public View getContentView(int position, LayoutInflater inflater, View convertView, ViewGroup parent) {
        if (null == convertView) {
            convertView = inflater.inflate(R.layout.grideviewshare_item, null);
        }
        return convertView;
    }

    @Override
    public void bindView(int position, View convertView, XBaseShareItem shareItem) {
        ImageView iv = convertView.findViewById(R.id.icon);
        TextView tv = convertView.findViewById(R.id.tv);
        iv.setImageResource(shareItem.getIconRes());
        tv.setText(shareItem.getTitle());
    }
}
