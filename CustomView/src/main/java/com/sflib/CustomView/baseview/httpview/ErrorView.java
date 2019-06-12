package com.sflib.CustomView.baseview.httpview;

import com.sflib.CustomView.R;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by xieningtao on 15-5-8.
 */
public class ErrorView extends FrameLayout {

	public static interface ErrorViewEvent {
		void onErrorClickEvent(View rootView);
	}

	public static interface ErrorViewLayoutEvent {
		void onErrorLayoutClickEvent(View rootView);
	}

	private LinearLayout error_ll;
	private TextView error_tv;
	private Button error_bt;
	private ImageView error_iv;

	private View mView;
	private boolean isShowing = false;

	public ErrorView(Context context) {
		super(context);
		makeAndInitView();
	}

	public ErrorView(Context context, AttributeSet attrs) {
		super(context, attrs);
		makeAndInitView();
		TypedArray a = context.obtainStyledAttributes(attrs,
				R.styleable.ErrorView);
		boolean visible = a.getBoolean(R.styleable.ErrorView_error_bt_visible,
				false);
		String bt_text = a.getString(R.styleable.ErrorView_error_bt_text);
		Drawable bt_text_bk = a.getDrawable(R.styleable.ErrorView_error_bt_bk);
		int bt_text_color = a.getColor(
				R.styleable.ErrorView_error_bt_txt_color, Color.WHITE);
		String text = a.getString(R.styleable.ErrorView_error_text);
		int text_color = a.getColor(R.styleable.ErrorView_error_text_color,
				Color.WHITE);
		Drawable drawable = a.getDrawable(R.styleable.ErrorView_error_icon);
		error_tv.setText(text);
		error_tv.setTextColor(text_color);
		int visibility = visible ? View.VISIBLE : View.GONE;
		error_bt.setVisibility(visibility);
		if (visible) {
			error_bt.setText(bt_text);
			error_bt.setTextColor(bt_text_color);
			error_bt.setBackgroundDrawable(bt_text_bk);
		}
		error_iv.setImageDrawable(drawable);
		a.recycle();
	}

	protected void makeAndInitView() {
		LayoutInflater.from(getContext()).inflate(
				R.layout.commanerrorview_layout, this);
		error_ll = (LinearLayout) findViewById(R.id.content_ll);
		error_tv = (TextView) findViewById(R.id.error_tips);
		error_bt = (Button) findViewById(R.id.error_bt);
		error_iv = (ImageView) findViewById(R.id.error_logo);
	}

	public void setErrorClick(final ErrorViewEvent event) {
		if (null != event) {
			error_bt.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					event.onErrorClickEvent(mView);
				}
			});
		}
	}

	public void setErrorLayoutEvent(final ErrorViewLayoutEvent event) {
		if (null != event) {
			error_ll.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					event.onErrorLayoutClickEvent(mView);
				}
			});
		}
	}

	public void setContentView(View view) {
		this.mView = view;
	}

	public void showError(int visibility) {
		setVisibility(visibility);
	}
}
