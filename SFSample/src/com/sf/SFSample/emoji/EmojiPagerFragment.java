package com.sf.SFSample.emoji;

import android.app.Fragment;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.sf.SFSample.R;
import com.sflib.emoji.core.BaseSFEmojiPagerFragment;
import com.sflib.emoji.core.ConfiguredEmojiGroup;
import com.sflib.emoji.core.EmojiGroup;
import com.sflib.emoji.core.EmojiHelp;
import com.sflib.emoji.core.NewEmojiBean;

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
        Bundle bundle = getArguments();
        if (bundle != null) {
            mEmojiKey = bundle.getString("emojiGroupKey");
            EmojiGroup emojiGroup = EmojiHelp.getEmojiMaps().get(mEmojiKey);
            if (emojiGroup != null) {
                mConfiguredEmoji = new ConfiguredEmojiGroup(2, 4, emojiGroup);
                mConfiguredEmoji.doConfigure();
            }

        }
        super.onViewCreated(view, savedInstanceState);

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
    protected View getEmojiItemView(int groupPosition,int subPosition, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getActivity()).inflate(R.layout.emoji_item_view, null);
        }
        TextView headTv = (TextView) convertView.findViewById(R.id.item_view_tv);
        ImageView emojiIv = (ImageView) convertView.findViewById(R.id.emoji_iv);
        List<NewEmojiBean> emojiBeens = mConfiguredEmoji.getmEmojiBeans().get(groupPosition);
        NewEmojiBean emojiBean = emojiBeens.get(subPosition);
        String emojiPath = "file://" + mConfiguredEmoji.getGroupPath() + File.separator + emojiBean.getFullName();
        ImageLoader.getInstance().displayImage(emojiPath, emojiIv);
        headTv.setText(groupPosition + "_" + subPosition + "");
        return convertView;
    }
}
