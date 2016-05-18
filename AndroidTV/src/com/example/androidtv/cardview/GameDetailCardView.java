package com.example.androidtv.cardview;

import android.content.Context;
import android.support.v17.leanback.widget.BaseCardView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.androidtv.R;


/**
 * Created by xieningtao on 15-9-11.
 */
public class GameDetailCardView extends BaseCardView {
    private ViewGroup mMainRl;

    private ImageView mImageIv;

    private TextView mGameTitle;

    private TextView mGameWatcher;

    public GameDetailCardView(Context context) {
        this(context, null);
    }

    public GameDetailCardView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public GameDetailCardView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        LayoutInflater inflater = LayoutInflater.from(context);
        View v = inflater.inflate(R.layout.gamedetail_cardview_tv, this);
        mMainRl = (ViewGroup) v.findViewById(R.id.game_main_rl);
        mImageIv = (ImageView) v.findViewById(R.id.game_main_image);
        mGameTitle = (TextView) v.findViewById(R.id.game_item_title);
        mGameWatcher = (TextView) v.findViewById(R.id.game_item_watcher);
    }

    public ImageView getImageIv() {
        return mImageIv;
    }

    public TextView getGameTitle() {
        return mGameTitle;
    }

    public TextView getGameWatcher() {
        return mGameWatcher;
    }

    public ViewGroup getRootView() {
        return mMainRl;
    }
}
