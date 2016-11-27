package com.example.sfchat.item.view;

import android.content.Context;
import android.text.TextUtils;

import com.example.sfchat.item.chatbean.SFMsg;
import com.example.sfchat.item.chatbean.SFTxt;
import com.example.sfchat.item.holder.BaseChatHolder;
import com.example.sfchat.item.holder.TxtViewHolder;
import com.sf.utils.baseutil.GsonUtil;

/**
 * Created by mac on 16/11/27.
 */

public class TxtItemView extends BaseChatItemView<SFMsg> {

    public TxtItemView(Context context) {
        super(context);
    }

    public TxtItemView(Context context, BaseChatItemView<SFMsg> baseChatItemView) {
        super(context, baseChatItemView);
    }

    @Override
    protected void updateContentView(SFMsg data, BaseChatHolder baseChatHolder, int position) {
        if(data==null|| TextUtils.isEmpty(data.getContent()))return;
        if(baseChatHolder instanceof TxtViewHolder){
            TxtViewHolder txtViewHolder= (TxtViewHolder) baseChatHolder;
            SFTxt txt= GsonUtil.parse(data.getContent(),SFTxt.class);

        }
    }
}
