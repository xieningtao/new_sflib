package com.example.sfchat.item.holder;

import android.view.View;
import android.widget.TextView;

import com.example.sfchat.R;

/**
 * Created by NetEase on 2016/11/25 0025.
 */

public class AudioViewHolder extends UserAndIndicatorViewHolder {
    public final View mStopView;
    public final View mPlayingView;
    public final TextView mTimeView;
    public final View mAudioContainer;

    public AudioViewHolder(View rootView) {
        super(rootView);
        mStopView = rootView.findViewById(R.id.audio_stop);
        mPlayingView = rootView.findViewById(R.id.audio_play);
        mTimeView = rootView.findViewById(R.id.voice_time);
        mAudioContainer = rootView.findViewById(R.id.audio_content_container);
    }
}
