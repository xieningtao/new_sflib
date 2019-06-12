package com.sf.SFSample.emoji;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.TextView;

import com.basesmartframe.baseui.BaseFragment;
import com.sf.SFSample.R;

/**
 * Created by NetEase on 2016/7/11 0011.
 */
public class EmojiFragment extends BaseFragment {

    private GridView mEmojiGv;
    private int mIndex;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.emoji_view, null);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            mIndex = bundle.getInt("index", 0);
        }
        init(view);
    }

    private void init(View view) {
        mEmojiGv = (GridView) view.findViewById(R.id.emoji_gv);
        mEmojiGv.setAdapter(new EmojiAdapter());
    }

    class EmojiAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return 20;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = LayoutInflater.from(getActivity()).inflate(R.layout.emoji_item_view, null);
            }
            TextView headTv = (TextView) convertView.findViewById(R.id.item_view_tv);
            headTv.setText(mIndex+"_"+position + "");
            return convertView;
        }
    }
}
