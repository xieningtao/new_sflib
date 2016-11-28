package com.example.sfchat.item.view;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;

import com.example.sfchat.item.chatbean.SFAudio;
import com.example.sfchat.item.chatbean.SFMsg;
import com.example.sfchat.item.holder.AudioViewHolder;
import com.example.sfchat.item.holder.BaseChatHolder;
import com.example.sfchat.media.MediaPlayManager;
import com.sf.utils.baseutil.GsonUtil;
import com.sf.utils.baseutil.UnitHelp;

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
        if (data == null || TextUtils.isEmpty(data.getContent())) return;
        if (baseChatHolder instanceof AudioViewHolder) {
            AudioViewHolder audioViewHolder = (AudioViewHolder) baseChatHolder;
            setUserBgBy(audioViewHolder.mAudioContainer, data.isFromMe());
            final SFAudio audio = GsonUtil.parse(data.getContent(), SFAudio.class);
            int voiceTime = (int) (audio.getMilliseconds() / (60 * 1000));
            audioViewHolder.mTimeView.setText(voiceTime + "\"");
            int width = getAudioViewPadding(voiceTime);
            ViewGroup.LayoutParams params = audioViewHolder.mAudioContainer.getLayoutParams();
            params.width = width;
            audioViewHolder.mAudioContainer.setLayoutParams(params);
            audioViewHolder.mAudioContainer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    MediaPlayManager.getInstance().createMediaPlay();
                    MediaPlayManager.getInstance().startPlay(audio.getUrl());
                }
            });

        }
    }

    private int getAudioViewPadding(int voiceTime) {
        int padding = (int) (voiceTime * 10);
        if (padding > UnitHelp.dip2px(getContext(), 200)) {
            return UnitHelp.dip2px(getContext(), 200);
        } else if (padding < UnitHelp.dip2px(getContext(), 40)) {
            return UnitHelp.dip2px(getContext(), 40);
        }
        return UnitHelp.dip2px(getContext(), padding);
    }
}
