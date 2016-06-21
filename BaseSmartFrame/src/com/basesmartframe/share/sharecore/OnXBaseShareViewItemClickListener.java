package com.basesmartframe.share.sharecore;

import android.view.View;
import android.widget.AdapterView;

import com.basesmartframe.share.shareitem.XBaseShareItem;

/**
 * Created by NetEase on 2016/6/20 0020.
 */
public interface OnXBaseShareViewItemClickListener {
    void onXBaseShareViewItemClickListener(XBaseShareItem shareItem, AdapterView<?> parent, View view, int position, long id);
}
