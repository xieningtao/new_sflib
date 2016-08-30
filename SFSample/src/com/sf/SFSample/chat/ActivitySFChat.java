package com.sf.SFSample.chat;

import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.basesmartframe.baseui.BaseActivity;
import com.example.sfchat.SFChatMessageId;
import com.example.sfchat.media.MediaRecordManager;
import com.example.sfchat.media.NewAudioRecorderManager;
import com.sf.SFSample.R;
import com.sf.SFSample.ui.ActivityMessageId;
import com.sf.loglib.L;
import com.sf.utils.baseutil.SFFileHelp;
import com.sflib.emoji.core.EmojiHelp;
import com.sflib.reflection.core.SFIntegerMessage;
import com.sflib.reflection.core.ThreadId;

import java.io.File;

/**
 * Created by NetEase on 2016/8/10 0010.
 */
public class ActivitySFChat extends BaseActivity {
    public static final String EMOJI_CONTAINER = "sf_emoji";
    private FrameLayout mContainer;
    private View mVoiceView;
    private ImageView mVoiceShowIv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (SFFileHelp.externalStorageExist()) {
            String path = Environment.getExternalStorageDirectory().getPath() + File.separator + EMOJI_CONTAINER;
            L.info(TAG, "method->EmojiActivity,path: " + path);
            EmojiHelp.loadEmojiFrom(path);
        }
        setContentView(R.layout.activity_sf_chat);
        mContainer = (FrameLayout) findViewById(R.id.chat_container);
        mContainer.setVisibility(View.GONE);
        mVoiceView = LayoutInflater.from(this).inflate(R.layout.voice_view, null);
        mVoiceShowIv = (ImageView) mVoiceView.findViewById(R.id.voice_show_iv);
        NewAudioRecorderManager.getInstance().setOnRecordListener(new NewAudioRecorderManager.OnRecordListener() {
            @Override
            public void onStartRecord() {

            }

            @Override
            public void onEndRecord(boolean isSuccess, int duration) {

            }

            @Override
            public void onError(String error) {

            }

            @Override
            public void updateForeground(double maxAmplitude) {
                L.info(TAG, "updateForeground maxAmplitude: " + maxAmplitude);
                mVoiceShowIv.setImageLevel((int) (maxAmplitude*10));
            }

            @Override
            public void updateTime(int currentTime, int maxTime) {

            }
        });
    }

    @SFIntegerMessage(messageId = SFChatMessageId.VOICE_BUTTON_PRESS, theadId = ThreadId.MainThread)
    public void onVoiceButtonPress() {
        if (mVoiceView.getParent() == null) {
            mContainer.addView(mVoiceView);
        }
        mContainer.setVisibility(View.VISIBLE);
    }

    @SFIntegerMessage(messageId = SFChatMessageId.VOICE_BUTTON_UP, theadId = ThreadId.MainThread)
    public void onVoiceButtonUp() {
        mContainer.setVisibility(View.GONE);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        NewAudioRecorderManager.getInstance().destroyRecord();
        MediaRecordManager.getInstance().destroy();
    }
}
