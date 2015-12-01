package com.basesmartframe.basecircle;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

public class CommentListView extends ListView {

	public CommentListView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		heightMeasureSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
				MeasureSpec.AT_MOST);
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
	}

}
