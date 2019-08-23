package com.sf.SFSample.emoji;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.sfchat.event.SFChatEvent;
import com.example.sfchat.event.SFChatItemEvent;
import com.sf.SFSample.R;

import com.sf.loglib.file.SFFileHelp;
import com.sflib.emoji.core.BaseSFEmojiPagerFragment;
import com.sflib.emoji.core.ConfiguredEmojiGroup;
import com.sflib.emoji.core.EmojiBean;
import com.sflib.emoji.core.EmojiFileBean;
import com.sflib.emoji.core.EmojiGroup;
import com.sflib.emoji.core.EmojiLoadManager;

import org.greenrobot.eventbus.EventBus;

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
        final ProgressBar downloadEmojiPb = rootView.findViewById(com.sflib.emoji.R.id.download_emoji_pb);
        final Button downloadEmojiBt = rootView.findViewById(com.sflib.emoji.R.id.download_emoji_bt);
        EmojiFileBean fileBean = EmojiLoadManager.getInstance().getEmojiFileBean(mEmojiKey);
        if (fileBean == null) {
            return;
        }
        String url = "https://raw.githubusercontent.com/xieningtao/documents/master/emoji/" + fileBean.getFileName() + ".zip";

        File file = SFFileHelp.createFileOnSD("sf_emoji_download", "sf-emoji-sample-hh_hh.zip");
        if (file != null) {

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
        TextView headTv = convertView.findViewById(R.id.item_view_tv);
        ImageView emojiIv = convertView.findViewById(R.id.emoji_iv);
        List<EmojiBean> emojiBeens = mConfiguredEmoji.getmEmojiBeans().get(groupPosition);
        EmojiBean emojiBean = emojiBeens.get(subPosition);
        final String emojiPath = "file://" + mConfiguredEmoji.getGroupPath() + File.separator + emojiBean.getFullName();
        headTv.setText(groupPosition + "_" + subPosition + "");
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventBus.getDefault().post(new SFChatItemEvent(emojiPath));
            }
        });
        return convertView;
    }
}
