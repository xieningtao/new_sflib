package com.basesmartframe.gesture;

import com.sf.loglib.L;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.HorizontalScrollView;

public class CustomHorizontalScrollView extends HorizontalScrollView {

	public String TAG = "CustomHorizontalScrollView";

	public CustomHorizontalScrollView(Context context) {
		super(context);
	}

	public CustomHorizontalScrollView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		L.debug(TAG, "measure width: " + getMeasuredWidth());
		L.debug(TAG, "child measure width: " + getChildAt(0).getMeasuredWidth());
	}

}
