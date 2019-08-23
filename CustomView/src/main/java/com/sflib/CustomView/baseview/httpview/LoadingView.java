package com.sflib.CustomView.baseview.httpview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.sflib.CustomView.R;

public class LoadingView extends FrameLayout{

	private ImageView mLoadingView;
	private TextView mLoadingText;
	
	public LoadingView(Context context){
		super(context);
		addAndInitView();
	}
	
	public LoadingView(Context context, AttributeSet attrs) {
		super(context, attrs);
		addAndInitView();
		TypedArray a = context.obtainStyledAttributes(attrs,
				R.styleable.LoaddingView);
		boolean visible=a.getBoolean(R.styleable.LoaddingView_loading_text_visible, true);
		String text = a.getString(R.styleable.LoaddingView_loading_text);
		int text_color = a.getColor(R.styleable.LoaddingView_loading_txt_color,
				Color.WHITE);
		Drawable drawable = a.getDrawable(R.styleable.LoaddingView_loading_icon);
		
		mLoadingText.setText(text);
		mLoadingText.setTextColor(text_color);
		int visibility=visible?View.VISIBLE:View.GONE;
		mLoadingText.setVisibility(visibility);
		mLoadingView.setImageDrawable(drawable);
		a.recycle();
	}
	
	private void addAndInitView(){
		LayoutInflater.from(getContext()).inflate(R.layout.commanloadingview_layout, this);
		mLoadingText= findViewById(R.id.loading_text_tv);
		mLoadingView= findViewById(R.id.loading_logo_iv);
		mLoadingView.startAnimation(getLoadingAnimation());
	}
	
	
	protected Animation getLoadingAnimation(){
		return AnimationUtils.loadAnimation(getContext(), R.anim.loadingview_anim);
	}
	
	public void showLoadingView(){
		setVisibility(View.VISIBLE);
		Animation animation=getLoadingAnimation();
		if(null!=animation){
			mLoadingView.clearAnimation();
			mLoadingView.startAnimation(animation);
		}
	}
	
	public void hideLoadingView(){
		setVisibility(View.GONE);
		mLoadingView.clearAnimation();
	}

}
