package com.basesmartframe.dialoglib.pop;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;

import com.basesmartframe.log.L;

public abstract class BaseCornerPopWindow {

	private PopupWindow.OnDismissListener mDismissListener;
	private PopupWindow mPopupWindow;
	protected Context mContext;

	public BaseCornerPopWindow(Context context) {
		this.mContext = context;
	}

	public void setmDismissListener(PopupWindow.OnDismissListener listener) {
		this.mDismissListener = listener;
	}

	protected abstract View getContentView();

	protected void onSortWindowCreate(PopupWindow popupWindow) {

	}

	public void showView(View arch, int xoff, int yoff) {
		if (null == mPopupWindow) {
			View rootView = getContentView();
			if (null != rootView) {
				mPopupWindow = new PopupWindow(rootView,
						ViewGroup.LayoutParams.WRAP_CONTENT,
						ViewGroup.LayoutParams.WRAP_CONTENT);
				onSortWindowCreate(mPopupWindow);
				if (null != mDismissListener) {
					mPopupWindow.setOnDismissListener(mDismissListener);
				}
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
