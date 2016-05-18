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
 * Created by xieningtao on 15-9-10.
 */
public class CategoryCardView extends BaseCardView {

    private ViewGroup mMainRl;

    private ImageView mImageIv;

    private TextView mContentTv;

    public CategoryCardView(Context context) {
        this(context, null);
    }

    public CategoryCardView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CategoryCardView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        LayoutInflater inflater = LayoutInflater.from(context);
        View v = inflater.inflate(R.layout.custom_carview_tv, this);
        mMainRl = (ViewGroup) v.findViewById(R.id.main_rl);
        mImageIv = (ImageView) v.findViewById(R.id.main_image);
        mContentTv = (TextView) v.findViewById(R.id.main_content_tv);
    }

    public ImageView getImageIv() {
        return mImageIv;
    }

    public TextView getContentTv() {
        return mContentTv;
    }

    public ViewGroup getRootView() {
        return mMainRl;
    }

}
