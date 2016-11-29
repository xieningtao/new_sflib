package com.sf.SFSample.emoji;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.basesmartframe.request.SFHttpZipFileHandler;
import com.example.sfchat.SFChatMessageId;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.sf.SFSample.R;
import com.sf.httpclient.newcore.MethodType;
import com.sf.httpclient.newcore.SFHttpFileCallback;
import com.sf.httpclient.newcore.SFRequest;
import com.sf.loglib.L;
import com.sf.loglib.file.SFFileHelp;
import com.sf.utils.baseutil.SFBus;
import com.sflib.emoji.core.BaseSFEmojiPagerFragment;
import com.sflib.emoji.core.ConfiguredEmojiGroup;
import com.sflib.emoji.core.EmojiBean;
import com.sflib.emoji.core.EmojiFileBean;
import com.sflib.emoji.core.EmojiGroup;
import com.sflib.emoji.core.EmojiLoadManager;

import java.io.File;
import java.util.List;

/**
 * Created by NetEase on 2016/7/11 0011.
 */
public class EmojiPagerFragment extends BaseSFEmojiPagerFragment {

    private String mEmojiKey;
    private ConfiguredEmojiGroup mConfiguredEmoji;

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        loadEmoji(true);

    }

    private void loadEmoji(boolean fromLocal) {
        Bundle bundle = getArguments();
        if (bundle != null) {
            mEmojiKey = bundle.getString("emojiGroupKey");
            EmojiGroup emojiGroup;
            if (fromLocal) {
                emojiGroup = EmojiLoadManager.getInstance().getEmojiMaps().get(mEmojiKey);
            } else {
                EmojiFileBean fileBean = EmojiLoadManager.getInstance().getEmojiFileBean(mEmojiKey);
                emojiGroup = EmojiLoadManager.getInstance().loadEmoji(fileBean);
            }
            if (emojiGroup != null) {
                mConfiguredEmoji = new ConfiguredEmojiGroup(2, 4, emojiGroup);
                mConfiguredEmoji.doConfigure();
                notifyDatasetChange();
                showLoaderView(false);
            } else {//emoji不存在
                showLoaderView(true);
            }
        }
    }

    @Override
    protected void onDownLoadEmojiClick(final ViewGroup rootView) {
        final ProgressBar downloadEmojiPb = (ProgressBar) rootView.findViewById(com.sflib.emoji.R.id.download_emoji_pb);
        final Button downloadEmojiBt = (Button) rootView.findViewById(com.sflib.emoji.R.id.download_emoji_bt);
        EmojiFileBean fileBean = EmojiLoadManager.getInstance().getEmojiFileBean(mEmojiKey);
        if (fileBean == null) {
            return;
        }
        String url = "https://raw.githubusercontent.com/xieningtao/documents/master/emoji/" + fileBean.getFileName() + ".zip";
        SFRequest request = new SFRequest(url, MethodType.GET) {
            @Override
            public Class getClassType() {
                return File.class;
            }
        };
        File file = SFFileHelp.createFileOnSD("sf_emoji_download", "sf-emoji-sample-hh_hh.zip");
        if (file != null) {
            SFHttpZipFileHandler fileHandler = new SFHttpZipFileHandler(request, file.getAbsolutePath(), new SFHttpFileCallback() {
                @Override
                public void onSuccess(SFRequest request, Object g) {
                    rootView.setVisibility(View.GONE);
                    loadEmoji(false);
                }

                @Override
                public void onFailed(SFRequest request, Exception e) {
                    downloadEmojiBt.setVisibility(View.VISIBLE);
                    downloadEmojiPb.setVisibility(View.GONE);
                }

                @Override
                public void onStart(SFRequest request) {
                    super.onStart(request);
                    rootView.setVisibility(View.VISIBLE);
                    downloadEmojiPb.setProgress(0);
                }

                @Override
                public void callBack(SFRequest request, long count, long current) {
                    super.callBack(request, count, current);
                    float percentage = current * 1.0f / count;
                    downloadEmojiPb.setProgress((int) (percentage * 100));
                    L.info(TAG, "callBack: " + percentage + " current: " + current + " count: " + count);
                }
            });
            fileHandler.start();
        }
    }

    @Override
    protected int getFragmentCount() {
        return mConfiguredEmoji == null ? 0 : mConfiguredEmoji.getmEmojiBeans().size();
    }

    @Override
    protected int getEmojiCount(int groupPosition) {
        return mConfiguredEmoji == null ? 0 : mConfiguredEmoji.getmEmojiBeans().get(groupPosition).size();
    }

    @Override
    protected View getEmojiItemView(int groupPosition, int subPosition, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getActivity()).inflate(R.layout.emoji_item_view, null);
        }
        TextView headTv = (TextView) convertView.findViewById(R.id.item_view_tv);
        ImageView emojiIv = (ImageView) convertView.findViewById(R.id.emoji_iv);
        List<EmojiBean> emojiBeens = mConfiguredEmoji.getmEmojiBeans().get(groupPosition);
        EmojiBean emojiBean = emojiBeens.get(subPosition);
        final String emojiPath = "file://" + mConfiguredEmoji.getGroupPath() + File.separator + emojiBean.getFullName();
        ImageLoader.getInstance().displayImage(emojiPath, emojiIv);
        headTv.setText(groupPosition + "_" + subPosition + "");
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SFBus.send(SFChatMessageId.ADD_MSG, emojiPath);
            }
        });
        return convertView;
    }
}
