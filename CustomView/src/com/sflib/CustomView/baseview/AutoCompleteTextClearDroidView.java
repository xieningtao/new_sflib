package com.sflib.CustomView.baseview;

import android.content.Context;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.sflib.CustomView.R;

public class AutoCompleteTextClearDroidView extends LinearLayout {
	public static interface EditTextClearDroidEvent {
		void onEditTextClearEvent(View v);
	}

	private AutoCompleteTextView mAutoEditText;
	private ImageView mClearView;
	private EditTextClearDroidEvent mEvent;
	private TextWatcher mTextWatcher;

	public AutoCompleteTextClearDroidView(Context context) {
		super(context);
	}

	public AutoCompleteTextClearDroidView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context, attrs, android.R.attr.editTextStyle);
	}

	public AutoCompleteTextClearDroidView(Context context, AttributeSet attrs,
			int defStyle) {
		super(context, attrs, defStyle);
		init(context, attrs, defStyle);
	}

	private void init(Context context, AttributeSet attrs, int defStyle) {
		mAutoEditText = new AutoCompleteTextView(context, attrs, defStyle);
		mAutoEditText.setBackgroundColor(getResources().getColor(
				R.color.transparent));
		mClearView = new ImageView(context);
		mClearView.setImageResource(R.drawable.cancel);
		hideClearDroid();
		mClearView.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(final View v) {
				mAutoEditText.setText("");
				if (isClearDroidShow()) {
					mEvent.onEditTextClearEvent(v);
				}
			}
		});
		setOrientation(LinearLayout.HORIZONTAL);
		addView(mAutoEditText, new LinearLayout.LayoutParams(0,
				LayoutParams.MATCH_PARENT, 1));
		addView(mClearView, new LinearLayout.LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT));

//		mAutoEditText.addTextChangedListener(new TextWatcher() {
//
//			@Override
//			public void onTextChanged(CharSequence s, int doRefresh, int before,
//					int count) {
//				mTextWatcher.onTextChanged(s, doRefresh, before, count);
//			}
//
//			@Override
//			public void beforeTextChanged(CharSequence s, int doRefresh, int count,
//					int after) {
//				mTextWatcher.beforeTextChanged(s, doRefresh, count, after);
//			}
//
//			@Override
//			public void afterTextChanged(Editable s) {
//				mTextWatcher.afterTextChanged(s);
//				if (null != s) {
//					String content = s.toString();
//					if (null != content && !TextUtils.isEmpty(content.trim())) {
//						showClearDroid();
//						return;
//					}
//				}
//				hideClearDroid();
//				
//				return;
//			}
//		});
	}

	public void addTextChangedListener(TextWatcher textWatch) {
		this.mTextWatcher = textWatch;
	}

	public AutoCompleteTextView getAutoCompleteTextView() {
		return mAutoEditText;
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

}
