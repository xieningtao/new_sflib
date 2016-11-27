package com.example.sfchat.item.view;

import android.content.Context;
import android.text.TextUtils;

import com.example.sfchat.item.chatbean.SFAudio;
import com.example.sfchat.item.chatbean.SFMsg;
import com.example.sfchat.item.holder.AudioViewHolder;
import com.example.sfchat.item.holder.BaseChatHolder;
import com.example.sfchat.media.MediaPlayManager;
import com.example.sfchat.media.NewAudioPlayerManager;
import com.sf.utils.baseutil.GsonUtil;

/**
 * Created by mac on 16/11/27.
 */

public class AudioItemView extends BaseChatItemView<SFMsg> {

    public AudioItemView(Context context) {
        super(context);
    }

    public AudioItemView(Context context, BaseChatItemView<SFMsg> baseChatItemView) {
        super(context, baseChatItemView);
    }

    @Override
    protected void updateContentView(SFMsg data, BaseChatHolder baseChatHolder, int position) {
        if(data==null|| TextUtils.isEmpty(data.getContent()))return;
        if(baseChatHolder instanceof AudioViewHolder){
            AudioViewHolder audioViewHolder= (AudioViewHolder) baseChatHolder;
            SFAudio audio= GsonUtil.parse(data.getContent(),SFAudio.class);
            MediaPlayManager.getInstance().startPlay(audio.getUrl());
        }
    }
}
