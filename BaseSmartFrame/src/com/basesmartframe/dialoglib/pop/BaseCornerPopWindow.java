package com.basesmartframe.dialoglib.pop;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;

import com.basesmartframe.baseutil.SpUtil;
import com.basesmartframe.log.L;

public abstract class BaseCornerPopWindow {
    protected final String TAG=getClass().getName();
    private PopupWindow.OnDismissListener mDismissListener;
    private PopupWindow mPopupWindow;
    protected Context mContext;

    public BaseCornerPopWindow(Context context) {
        this.mContext = context;
    }

    public void setmDismissListener(PopupWindow.OnDismissListener listener) {
        this.mDismissListener = listener;
    }


    protected abstract PopupWindow onCreatePopWindow(LayoutInflater layoutInflater);

    public void showView(View arch, int xoff, int yoff) {
        if (null == mPopupWindow) {
            LayoutInflater layoutInflater = LayoutInflater.from(mContext);
            mPopupWindow = onCreatePopWindow(layoutInflater);
//						= new PopupWindow(rootView,
//						ViewGroup.LayoutParams.WRAP_CONTENT,
//						ViewGroup.LayoutParams.WRAP_CONTENT);
            if (null != mDismissListener) {
                mPopupWindow.setOnDismissListener(mDismissListener);
            }
        }

        if (null != arch) {
            if (mPopupWindow.isShowing()) {
                mPopupWindow.dismiss();
            }
            mPopupWindow.showAsDropDown(arch, xoff, yoff);
        } else {
            L.error(this, "arch is null");
        }
    }

    public boolean isShowing() {
        if (null != mPopupWindow) {
            return mPopupWindow.isShowing();
        }
        return false;
    }

    public void hideView() {
        if (null != mPopupWindow) {
            mPopupWindow.dismiss();
        }
    }

}
