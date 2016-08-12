package com.sf.SFSample.chat;

import android.os.Bundle;
import android.os.Environment;

import com.basesmartframe.baseui.BaseActivity;
import com.sf.SFSample.R;
import com.sf.loglib.L;
import com.sf.utils.baseutil.SFFileHelp;
import com.sflib.emoji.core.EmojiHelp;

import java.io.File;

/**
 * Created by NetEase on 2016/8/10 0010.
 */
public class ActivitySFChat extends BaseActivity {
    public static final String EMOJI_CONTAINER="sf_emoji";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(SFFileHelp.externalStorageExist()){
            String path = Environment.getExternalStorageDirectory().getPath() + File.separator+ EMOJI_CONTAINER;
            L.info(TAG,"method->EmojiActivity,path: "+path);
            EmojiHelp.loadEmojiFrom(path);
        }
        setContentView(R.layout.activity_sf_chat);
    }
}
