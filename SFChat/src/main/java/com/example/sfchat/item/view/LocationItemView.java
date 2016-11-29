package com.example.sfchat.item.view;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;

import com.example.sfchat.item.chatbean.SFLocationBean;
import com.example.sfchat.item.chatbean.SFMsg;
import com.example.sfchat.item.holder.BaseChatHolder;
import com.example.sfchat.item.holder.LocationViewHolder;
import com.sf.utils.baseutil.GsonUtil;

/**
 * Created by NetEase on 2016/11/18 0018.
 */

public class LocationItemView extends BaseChatItemView<SFMsg> {

    public LocationItemView(Context context, BaseChatItemView<SFMsg> baseChatItemView) {
        super(context, baseChatItemView);
    }

    @Override
    protected void updateContentView(SFMsg data, BaseChatHolder baseChatHolder, int position) {
        if (data == null || TextUtils.isEmpty(data.getContent())) return;
        if (baseChatHolder instanceof LocationViewHolder) {
            LocationViewHolder locationViewHolder = (LocationViewHolder) baseChatHolder;
            setUserBgBy(locationViewHolder.mMapContainer, data.isFromMe());
            SFLocationBean location = GsonUtil.parse(data.getContent(), SFLocationBean.class);
            locationViewHolder.mAddressTv.setText(location.getAddress());
            locationViewHolder.mProgress.setVisibility(View.GONE);
        }
    }


}
