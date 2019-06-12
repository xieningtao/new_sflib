package com.example.sfchat;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.basesmartframe.baseui.BaseFragment;
import com.example.sfchat.media.MediaRecordManager;
import com.example.sfchat.media.NewAudioRecorderManager;
import com.sf.loglib.L;
import com.sf.utils.ThreadHelp;
import com.sf.utils.baseutil.SystemUIHelp;
import com.sflib.reflection.core.SFBus;

import java.io.IOException;

/**
 * Created by NetEase on 2016/8/10 0010.
 */
abstract public class SFBaseChatFragment extends BaseFragment {

    enum ChatState {
        EDITOR, VOICE
    }

    private ImageView mSmileIv, mGuider, mVoice;
    private View mExpressionContainer;
    private Button mVoiceInput;

    private Fragment mExpressionFragment;
    private Fragment mGuiderFragment;

    private EditText mInputEt;

    private ChatState mChatState = ChatState.EDITOR;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.sf_chat_main, null);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        NewAudioRecorderManager.getInstance().onCreate();
        init(view);
    }

    abstract protected Fragment createExpressionFragment();

    abstract protected Fragment createGuiderFragment();

    private void init(View view) {
        mSmileIv = (ImageView) view.findViewById(R.id.edit_smile);
        mGuider = (ImageView) view.findViewById(R.id.guider);
        mExpressionContainer = view.findViewById(R.id.expression_container);
        mInputEt = (EditText) view.findViewById(R.id.input_et);
        mVoice = (ImageView) view.findViewById(R.id.edit_voice);
        mVoiceInput = (Button) view.findViewById(R.id.input_voice_bt);
        mExpressionContainer.setVisibility(View.GONE);
        final FragmentManager fragmentManager = getFragmentManager();

        mVoice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mChatState != ChatState.VOICE) {
                    mVoiceInput.setVisibility(View.VISIBLE);
                    mInputEt.setVisibility(View.GONE);
                    mChatState = ChatState.VOICE;
                } else if (mChatState != ChatState.EDITOR) {
                    mVoiceInput.setVisibility(View.GONE);
                    mInputEt.setVisibility(View.VISIBLE);
                    mChatState = ChatState.EDITOR;
                }
            }
        });
        mVoiceInput.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (MotionEvent.ACTION_DOWN == event.getAction()) {//down
                    try {
//                        MediaRecordManager.getInstance().start();
                        NewAudioRecorderManager.getInstance().startRecord();
                        mVoiceInput.setText("松开结束说话");
                        SFBus.send(SFChatMessageId.VOICE_BUTTON_PRESS);
                    } catch (Exception e) {
                        L.error(TAG, TAG + ".init.onTouch exception: " + e);
                    }
                } else if (MotionEvent.ACTION_UP == event.getAction() || MotionEvent.ACTION_CANCEL == event.getAction()) {//up
                    mVoiceInput.setText("按住请说话");
//                    MediaRecordManager.getInstance().stop();
                    NewAudioRecorderManager.getInstance().stopRecord();
                    SFBus.send(SFChatMessageId.VOICE_BUTTON_UP);
                } else {//move

                }
                return false;
            }
        });
        mInputEt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mExpressionContainer.setVisibility(View.GONE);
                SystemUIHelp.showSoftKeyboard(getActivity(), mInputEt);
                mInputEt.requestFocus();
            }
        });
        mSmileIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SystemUIHelp.hideSoftKeyboard(getActivity(), mInputEt);
                showExpressionContainer();
                if (mGuiderFragment != null && mGuiderFragment.isVisible()) {
                    fragmentManager.beginTransaction().hide(mGuiderFragment).commit();
                }
                if (mExpressionFragment == null) {
                    mExpressionFragment = createExpressionFragment();
                    if (mExpressionFragment != null) {
                        fragmentManager.beginTransaction().add(R.id.expression_container, mExpressionFragment).commit();
                    }
                } else if (mExpressionFragment.isHidden()) {
                    fragmentManager.beginTransaction().show(mExpressionFragment).commit();
                }

            }
        });

        mGuider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SystemUIHelp.hideSoftKeyboard(getActivity(), mInputEt);
                showExpressionContainer();
                if (mExpressionFragment != null && mExpressionFragment.isVisible()) {
                    fragmentManager.beginTransaction().hide(mExpressionFragment).commit();
                }
                if (mGuiderFragment == null) {
                    mGuiderFragment = createGuiderFragment();
                    if (mGuiderFragment != null) {
                        fragmentManager.beginTransaction().add(R.id.expression_container, mGuiderFragment).commit();
                    }
                } else if (mGuiderFragment.isHidden()) {
                    fragmentManager.beginTransaction().show(mGuiderFragment).commit();
                }
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        NewAudioRecorderManager.getInstance().onDestroy();
    }

    private void showExpressionContainer() {
        ThreadHelp.runInMain(new Runnable() {
            @Override
            public void run() {
                mExpressionContainer.setVisibility(View.VISIBLE);
            }
        }, 100);
    }
}
