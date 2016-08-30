package com.sflib.CustomView;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;

/**
 * Created by NetEase on 2016/8/29 0029.
 */
public class KeyBoardFrameLayout extends FrameLayout {
    public interface onKeyBoardListener {
        void onKeyboardVisible(boolean visible);
    }

    private int mOriginTop;
    private int mOriginBottom;
    private final int keyboardGap = 100;

    private onKeyBoardListener mOnKeyBoardListener;

    private boolean isKeyBoardVisible = false;

    public KeyBoardFrameLayout(Context context) {
        super(context);
    }

    public KeyBoardFrameLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public KeyBoardFrameLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setOnKeyBoardListener(onKeyBoardListener onKeyBoardListener) {
        mOnKeyBoardListener = onKeyBoardListener;
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        if (mOriginTop == 0 && mOriginBottom == 0) {
            mOriginTop = top;
            mOriginBottom = bottom;
        }

        int originDy = mOriginBottom - mOriginTop;
        int curDy = bottom - top;
        if (originDy - curDy > 100) {
            if (mOnKeyBoardListener != null) {
                mOnKeyBoardListener.onKeyboardVisible(true);
            }
            isKeyBoardVisible = true;
        } else {
            if (mOnKeyBoardListener != null) {
                mOnKeyBoardListener.onKeyboardVisible(false);
            }
            isKeyBoardVisible = false;
        }

    }

    public boolean isKeyBoardVisible() {
        return isKeyBoardVisible;
    }
}
