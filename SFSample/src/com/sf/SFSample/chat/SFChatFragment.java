package com.sf.SFSample.chat;

import android.app.Fragment;

import com.example.sfchat.SFBaseChatFragment;
import com.example.sfchat.SFBaseChatMoreFragment;
import com.sf.SFSample.emoji.EmojiContainerFragment;

/**
 * Created by NetEase on 2016/8/11 0011.
 */
public class SFChatFragment extends SFBaseChatFragment {
    @Override
    protected Fragment createExpressionFragment() {
        return new EmojiContainerFragment();
    }

    @Override
    protected Fragment createGuiderFragment() {
        return new SFBaseChatMoreFragment();
    }
}
