package com.basesmartframe.share.sharecore;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.basesmartframe.share.shareitem.XBaseShareItem;


/**
 * Created by xieningtao on 15-8-14.
 */
public interface BaseShareAdapter {
    View getContentView(int position, LayoutInflater inflater, View convertView, ViewGroup parent);

    void bindView(int position, View convertView, XBaseShareItem shareItem);
}
