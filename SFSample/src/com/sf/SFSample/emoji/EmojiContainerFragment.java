package com.sf.SFSample.emoji;

import android.app.Fragment;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.basesmartframe.request.SFHttpFileHandler;
import com.sf.SFSample.R;
import com.sf.httpclient.newcore.MethodType;
import com.sf.httpclient.newcore.SFHttpFileCallback;
import com.sf.httpclient.newcore.SFRequest;
import com.sf.loglib.L;
import com.sf.loglib.file.SFFileHelp;
import com.sflib.emoji.core.BaseSFEmojiContainerFragment;
import com.sflib.emoji.core.EmojiLoadManager;
import com.sflib.reflection.core.ThreadHelp;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by NetEase on 2016/7/11 0011.
 */
public class EmojiContainerFragment extends BaseSFEmojiContainerFragment {
    public static final String EMOJI_CONTAINER = "sf_emoji_download";
    private List<String> mEmojiKeys = new ArrayList<>();

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        loadEmojiFile();
    }

    private void loadIndexFile() {
        if (SFFileHelp.externalStorageExist()) {
            String path = Environment.getExternalStorageDirectory().getPath() + File.separator + EMOJI_CONTAINER;
            L.info(TAG, "method->EmojiActivity,path: " + path);
            EmojiLoadManager.getInstance().loadEmojiOutlineFrom(path);
            EmojiLoadManager.getInstance().loadAllEmoji();
            List<String> keys = EmojiLoadManager.getInstance().getEmojiKeys();
            mEmojiKeys.addAll(keys);
        }
        ThreadHelp.runInMain(new Runnable() {
            @Override
            public void run() {
                notifyDataSetChange();
            }
        });
    }

    private void loadEmojiFile() {
        String url = "https://raw.githubusercontent.com/xieningtao/documents/master/emoji/emojiFileBeen.txt";
//        String url = "http://t4.love.163.com/api/weiboLoginApi";
//        String url = "http://bmob-cdn-6094.b0.upaiyun.com/2016/09/06/496ebc014004e7788066ef652f2f0199.txt";
        SFRequest request = new SFRequest(url, MethodType.GET) {
            @Override
            public Class getClassType() {
                return File.class;
            }
        };
//
//        SFHttpGsonHandler httpGsonCacheHandler=new SFHttpGsonHandler(request, new SFHttpStringCallback<EmojiFileEntry>() {
//
//            @Override
//            public void onSuccess(SFRequest request, EmojiFileEntry g) {
//                    L.info(TAG,"onSuccess g: "+g);
//            }
//
//            @Override
//            public void onFailed(SFRequest request, Exception e) {
//                L.info(TAG,"onFailed");
//            }
//        });
//        httpGsonCacheHandler.start();
        File file = SFFileHelp.createFileOnSD("sf_emoji_download", "emojiFileBeen.txt");
        if (file != null) {
            SFHttpFileHandler fileHandler = new SFHttpFileHandler(request, file.getAbsolutePath(), new SFHttpFileCallback<File>() {
                @Override
                public void onSuccess(SFRequest request, File g) {
                    ThreadHelp.runInSingleBackThread(new Runnable() {
                        @Override
                        public void run() {
                            loadIndexFile();
                        }
                    }, 0);
                }

                @Override
                public void onFailed(SFRequest request, Exception e) {

                }
            });
            fileHandler.start();
        }
    }

    @Override
    protected Fragment getFragment(int i) {
        return new EmojiPagerFragment();
    }

    @Override
    protected Bundle getBundle(int i) {
        Bundle bundle = new Bundle();
        bundle.putString("emojiGroupKey", mEmojiKeys.get(i));
        return bundle;
    }

    @Override
    protected int getFragmentCount() {
        int size = mEmojiKeys.size();
        return size;
    }

    @Override
    protected View getTabHeadView(LayoutInflater inflater, int position, View parent) {
        View headView = inflater.inflate(R.layout.emoji_head_view, null);
        TextView headTv = (TextView) headView.findViewById(R.id.head_tv);
        headTv.setText(position + "");
        return headView;
    }
}
