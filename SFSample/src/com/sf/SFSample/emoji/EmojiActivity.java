package com.sf.SFSample.emoji;

import android.os.Bundle;
import android.os.Environment;

import com.basesmartframe.baseui.BaseActivity;
import com.sf.SFSample.R;
import com.sf.loglib.L;
import com.sf.utils.baseutil.SFFileHelp;
import com.sflib.emoji.core.EmojiHelp;

import java.io.File;

/**
 * Created by NetEase on 2016/7/11 0011.
 */
public class EmojiActivity extends BaseActivity {
    public static final String EMOJI_CONTAINER="sf_emoji";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emoji);
        if(SFFileHelp.externalStorageExist()){
            String path = Environment.getExternalStorageDirectory().getPath() + File.separator+ EMOJI_CONTAINER;
            L.info(TAG,"method->EmojiActivity,path: "+path);
            EmojiHelp.loadEmojiFrom(path);
        }
        getFragmentManager().beginTransaction().replace(R.id.emoji_container,new EmojiContainerFragment()).commit();
        getFragmentManager().executePendingTransactions();
    }


}
