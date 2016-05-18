package com.sflib.CustomView.listview;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;


/**
 * Created by xieningtao on 16-3-25.
 */
public class XPinListView extends ListView {
    private OnScrollListener mOnScrollListener;
    private TextView mPinedView;
    private final String TAG = getClass().getName();

    public XPinListView(Context context) {
        this(context, null);
    }

    public XPinListView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public XPinListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public void setXPinOnScrollListener(OnScrollListener onScrollListener) {
        mOnScrollListener = onScrollListener;
    }

    public void setPinedView(TextView pinedView) {
        mPinedView = pinedView;
    }

    private void init() {
        setOnScrollListener(new OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                if (mOnScrollListener != null) {
                    mOnScrollListener.onScrollStateChanged(view, scrollState);
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                Log.i(TAG, "method->onScroll,firstVisibleItem: " + firstVisibleItem + " visibleItemCount: " + visibleItemCount + " totalItemCount: " + totalItemCount);
                pinSpecialView(firstVisibleItem, visibleItemCount);
                if (mOnScrollListener != null) {
                    mOnScrollListener.onScroll(view, firstVisibleItem, visibleItemCount, totalItemCount);
                }
            }
        });
    }

    private boolean mMovedPinedView = false;
    private float mStartY = 0.0f;
    private float mCurY = 0.0f;

    private void pinSpecialView(int firstVisibleItem, int visibleItemCount) {
        int childCount = getChildCount();
        if (childCount > 0) {

            View nextHeadView = getNextHeadView(firstVisibleItem, visibleItemCount);
            if (nextHeadView != null) {
                float firstLeft = nextHeadView.getX();
                float firstTop = nextHeadView.getY();

                float pinedLeft = mPinedView.getX();
                float pinedTop = mPinedView.getY();
                float pinedHeight = mPinedView.getHeight();
                float pinedBottom = pinedTop + pinedHeight;

                float ty = mPinedView.getTranslationY();
                if (mMovedPinedView) {
                    int height = mPinedView.getHeight();
                    float dty = Math.abs(ty);
                    float dy = firstTop - mStartY;
                    if (dty >= height || dy > 0) {
                        mMovedPinedView = false;
                        mPinedView.setTranslationY(0.0f);
                    } else if (dy < 0) {
                        mPinedView.setTranslationY(dy);
                    }
                    return;
                }

                //update title to the next new title when head view is above at pined view
//                int first_pined_dTop = (int) (firstTop - pinedTop)+10;
                if (firstTop <= pinedBottom) {
                    XPinAdapter adapter = (XPinAdapter) getAdapter();
                    String nextTitle = adapter.getHeadTitle(mNextHeadPos);
                    String curTitle = mPinedView.getText().toString();
                    if (!nextTitle.equals(curTitle)) {
                        mPinedView.setText(nextTitle);
                        if (!TextUtils.isEmpty(curTitle)) {//exclusive the first time
                            mMovedPinedView = true;
                            mStartY = firstTop;
                        }
                    }
                } else {//update title to the previous title when head view is below at pined view
                    XPinAdapter adapter = (XPinAdapter) getAdapter();
                    String preTitle = adapter.getHeadTitle(firstVisibleItem);
                    String curTitle = mPinedView.getText().toString();
                    if (!preTitle.equals(curTitle)) {
                        mPinedView.setText(preTitle);
                    }
                }
                Log.i(TAG, "method->pinSpecialView pinedLeft: " + pinedLeft + " pinedBottom: " + pinedBottom);
                Log.i(TAG, "method->pinSpecialView, firstLeft: " + firstLeft + " firstTop: " + firstTop);
            }
        }

    }

    private View mCurHeadView;
    private View mNextHeadView;
    private View mPreHeadView;
    private int mNextHeadPos;

    private String mPreTitle;

    private View getCurHeadView(int firstVisibleItem, int visibleItemCount) {
        return null;
    }

    //scroll dow
    private View getNextHeadView(int firstVisibleItem, int visibleItemCount) {
        ListAdapter adapter = getAdapter();
        for (int i = firstVisibleItem; i < firstVisibleItem + visibleItemCount; i++) {
            int viewType = adapter.getItemViewType(i);
            if (viewType == 0) {//head view
                mNextHeadPos = i;
                return getChildAt(i - firstVisibleItem);
            }
        }
        return null;

    }

    //sdcroll up
    private View getPreHeadView() {

        return null;
    }

    private int getCurHeadPosition() {
        return 0;
    }

    private int getNextHeadPosition() {
        return 0;
    }

    private int getPreHeadPosition() {
        return 0;
    }
}
