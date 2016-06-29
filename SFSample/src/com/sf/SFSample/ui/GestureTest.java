package com.sf.SFSample.ui;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.ScrollView;

import com.basesmartframe.baseui.BaseActivity;
import com.sf.utils.baseutil.SystemUIWHHelp;
import com.basesmartframe.gesture.HeadViewInteraction;
import com.sf.SFSample.R;

public class GestureTest extends BaseActivity {
	private HeadViewInteraction interactionListener;

	private View first_fl;
	private View second_fl;
	private View root_rl;
	ScrollView view;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.gesturetest);

		root_rl = findViewById(R.id.root_rl);
		first_fl = findViewById(R.id.first_fl);
		second_fl = findViewById(R.id.second_fl);
		View view = findViewById(R.id.first_ll);
		LayoutParams params = (LayoutParams) view.getLayoutParams();
		params.width = SystemUIWHHelp.getScreenRealWidth(this);
		view.setLayoutParams(params);
		interactionListener = new HeadViewInteraction(this, root_rl, second_fl,
				first_fl);

	}
}
