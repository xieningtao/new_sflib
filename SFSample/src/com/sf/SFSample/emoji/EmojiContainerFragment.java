package com.sf.SFSample.emoji;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.sf.SFSample.R;
import com.sflib.emoji.core.BaseSFEmojiContainerFragment;
import com.sflib.emoji.core.EmojiHelp;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by NetEase on 2016/7/11 0011.
 */
public class EmojiContainerFragment extends BaseSFEmojiContainerFragment {

    private List<String> mEmojiKeys = new ArrayList<>();

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Set<String> keys = EmojiHelp.getEmojiMaps().keySet();
        mEmojiKeys.addAll(keys);
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
        int size = EmojiHelp.getEmojiMaps().size();
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
