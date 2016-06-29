package com.sflib.umenglib.share.sharecore;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ListAdapter;

import com.sf.loglib.L;
import com.sflib.umenglib.share.shareitem.XBaseShareItem;

import java.util.ArrayList;

/**
 * Created by xieningtao on 15-8-5.
 */
public abstract class XBaseShareView extends GridView {

    private String TAG = XBaseShareView.class.getName();


    public XBaseShareView(Context context) {
        super(context);
    }

    public XBaseShareView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    public void setShareAdapter(BaseShareAdapter shareAdapter) {
        if (null == shareAdapter) {
            throw new IllegalArgumentException("shareAdapter is null");
        }
        setAdapter(new ShareAdapter(shareAdapter));
        setOnItemClickListener(null);
    }

    @Override
    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        super.setOnItemClickListener(mOnItemClickListener);
        mCustomItemClickLister = onItemClickListener;
    }

    public void setOnXBaseShareViewItemClickListener(OnXBaseShareViewItemClickListener listener) {
        this.mOnXBaseShareViewItemClickListener = listener;
    }

    public void setOnXShareListener(OnXShareListener mOnXShareListener) {
        this.mOnXShareListener = mOnXShareListener;
    }

    abstract public ArrayList<XBaseShareItem> getShareItems();

    private OnXBaseShareViewItemClickListener mOnXBaseShareViewItemClickListener;
    private OnItemClickListener mCustomItemClickLister;
    private OnXShareListener mOnXShareListener;

    private OnItemClickListener mOnItemClickListener = new OnItemClickListener() {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            if (null != mCustomItemClickLister) {
                mCustomItemClickLister.onItemClick(parent, view, position, id);
            }

            ListAdapter listAdapter = getAdapter();
            if (listAdapter != null && listAdapter instanceof ShareAdapter) {
                ShareAdapter shareAdapter = (ShareAdapter) listAdapter;
                XBaseShareItem xShareItem = (XBaseShareItem) shareAdapter.getItem(position);
                if (xShareItem != null) {
                    if (null != mOnXBaseShareViewItemClickListener) {
                        mOnXBaseShareViewItemClickListener.onXBaseShareViewItemClickListener(xShareItem, parent, view, position, id);
                    }
                    XShareAction xShareAction = xShareItem.createShareAction();
                    if (xShareAction != null) {
                        xShareAction.doShareAction(xShareItem,mOnXShareListener);
                    } else {
                        L.error(TAG, "xShareAction is null");
                    }
                } else {
                    L.error(TAG, "xShareItem is null");
                }
            } else {
                L.error(TAG, "listAdapter is null or listAdapter is not instance of ShareAdapter");
            }

        }
    };


    class ShareAdapter extends BaseAdapter {
        private ArrayList<XBaseShareItem> mXShareItems = new ArrayList<>();
        private BaseShareAdapter mBaseShareAdapter;

        public ShareAdapter(BaseShareAdapter shareAdapter) {
            ArrayList<XBaseShareItem> shareItems = getShareItems();
            mXShareItems.clear();
            if (null != shareItems) {
                mXShareItems.addAll(shareItems);
            }
            this.mBaseShareAdapter = shareAdapter;
        }

        @Override
        public int getCount() {
            return mXShareItems.size();
        }

        @Override
        public Object getItem(int position) {
            return mXShareItems.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            XBaseShareItem shareItem = mXShareItems.get(position);
            convertView = mBaseShareAdapter.getContentView(position, LayoutInflater.from(getContext()),convertView, parent);
            mBaseShareAdapter.bindView(position, convertView, shareItem);
            return convertView;
        }
    }
}
