package com.example.sfchat.item.view;

import android.content.Context;
import android.view.View;

import com.example.sfchat.R;
import com.example.sfchat.item.holder.BaseChatHolder;
import com.example.sfchat.item.holder.UserAndIndicatorViewHolder;

/**
 * Created by NetEase on 2016/11/25 0025.
 */

abstract public class BaseChatItemView<T> {
    protected String TAG = getClass().getName();
    private Context mContext;
    private BaseChatItemView<T> mBaseChatItemView;

    public BaseChatItemView(Context context) {
        this(context, null);
    }

    public BaseChatItemView(Context context, BaseChatItemView<T> baseChatItemView) {
        mContext = context;
        mBaseChatItemView = baseChatItemView;
    }

    protected Context getContext() {
        return mContext;
    }

    public BaseChatItemView getChatItemView() {
        return mBaseChatItemView;
    }

    public void updateItemView(T data, BaseChatHolder baseChatHolder, int position) {
        if (mBaseChatItemView != null) {
            mBaseChatItemView.updateItemView(data, baseChatHolder, position);
        }
        hideAllIndicator(baseChatHolder);
        updateContentView(data, baseChatHolder, position);
    }

    abstract protected void updateContentView(T data, BaseChatHolder baseChatHolder, int position);

    protected void setUserBgBy(View view, boolean fromMe) {
        if (fromMe) {
            view.setBackgroundResource(R.drawable.chat_right_new_bg);
        } else {
            view.setBackgroundResource(R.drawable.chat_left_new_bg);
        }
    }

    protected void hideAllIndicator(BaseChatHolder baseChatHolder) {
        if (baseChatHolder instanceof UserAndIndicatorViewHolder) {
            UserAndIndicatorViewHolder viewHolder = (UserAndIndicatorViewHolder) baseChatHolder;
            viewHolder.mIndicatorViewHolder.mErrorView.setVisibility(View.GONE);
            viewHolder.mIndicatorViewHolder.mUnreadView.setVisibility(View.GONE);
            viewHolder.mIndicatorViewHolder.mTime.setVisibility(View.GONE);
            viewHolder.mIndicatorViewHolder.mProgressBar.setVisibility(View.GONE);
        }
    }

    protected void showLoading(BaseChatHolder baseChatHolder) {
        if (baseChatHolder instanceof UserAndIndicatorViewHolder) {
            UserAndIndicatorViewHolder viewHolder = (UserAndIndicatorViewHolder) baseChatHolder;
            viewHolder.mIndicatorViewHolder.mErrorView.setVisibility(View.GONE);
            viewHolder.mIndicatorViewHolder.mUnreadView.setVisibility(View.GONE);
            viewHolder.mIndicatorViewHolder.mTime.setVisibility(View.GONE);
            viewHolder.mIndicatorViewHolder.mProgressBar.setVisibility(View.VISIBLE);
        }
    }

    protected void showSuccess(BaseChatHolder baseChatHolder) {
        if (baseChatHolder instanceof UserAndIndicatorViewHolder) {
            UserAndIndicatorViewHolder viewHolder = (UserAndIndicatorViewHolder) baseChatHolder;
            viewHolder.mIndicatorViewHolder.mErrorView.setVisibility(View.GONE);
            viewHolder.mIndicatorViewHolder.mUnreadView.setVisibility(View.GONE);
            viewHolder.mIndicatorViewHolder.mTime.setVisibility(View.GONE);
            viewHolder.mIndicatorViewHolder.mProgressBar.setVisibility(View.GONE);
        }
    }

    protected void showFailed(BaseChatHolder baseChatHolder) {
        if (baseChatHolder instanceof UserAndIndicatorViewHolder) {
            UserAndIndicatorViewHolder viewHolder = (UserAndIndicatorViewHolder) baseChatHolder;
            viewHolder.mIndicatorViewHolder.mErrorView.setVisibility(View.VISIBLE);
            viewHolder.mIndicatorViewHolder.mUnreadView.setVisibility(View.GONE);
            viewHolder.mIndicatorViewHolder.mTime.setVisibility(View.GONE);
            viewHolder.mIndicatorViewHolder.mProgressBar.setVisibility(View.GONE);
        }
    }
}
