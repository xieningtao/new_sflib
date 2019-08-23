package com.sflib.CustomView.baseview;

import com.sflib.CustomView.R;

import android.content.Context;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class EditTextClearDroidView extends LinearLayout {

	public interface EditTextClearDroidEvent {
		void onEditTextClearEvent(View v);
	}

	private EditText mEditText;
	private ImageView mClearView;
	private EditTextClearDroidEvent mEvent;

	public EditTextClearDroidView(Context context) {
		super(context);
	}

	public EditTextClearDroidView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context, attrs, android.R.attr.editTextStyle);
	}

	public EditTextClearDroidView(Context context, AttributeSet attrs,
			int defStyle) {
		super(context, attrs, defStyle);
		init(context, attrs, defStyle);
	}

	private void init(Context context, AttributeSet attrs, int defStyle) {
		mEditText = new EditText(context, attrs, defStyle);
		mEditText.setBackgroundColor(getResources().getColor(
				R.color.transparent));
		mClearView = new ImageView(context);
		mClearView.setImageResource(R.drawable.cancel);
		hideClearDroid();
		mClearView.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(final View v) {
				mEditText.setText("");
				if (isClearDroidShow()) {
					mEvent.onEditTextClearEvent(v);
				}
			}
		});
		setOrientation(LinearLayout.HORIZONTAL);
		addView(mEditText, new LinearLayout.LayoutParams(0,
				LayoutParams.MATCH_PARENT, 1));
		addView(mClearView, new LinearLayout.LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT));

		mEditText.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				// TODO Auto-generated method stub

			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub

			}

			@Override
			public void afterTextChanged(Editable s) {
				if (null != s) {
					String content = s.toString();
					if (null != content && !TextUtils.isEmpty(content.trim())) {
						showClearDroid();
						return;
					}
				}
				hideClearDroid();
				return;
			}
		});
	}

	public void showClearDroid() {
		mClearView.setVisibility(View.VISIBLE);
	}

	public void hideClearDroid() {
		mClearView.setVisibility(View.INVISIBLE);
	}

	public boolean isClearDroidShow() {
		return mClearView.getVisibility() == View.VISIBLE;
	}

	public EditText getEditText() {
		return mEditText;
	}
}
