package com.sf.utils.baseutil;


import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

/**
 * two functions:
 * 1.sdk version after 19,hide status and navigation bar is ok use immersive mode
 * 2.sdk version before,only hide status
 * 
 * @author xieningtao
 *
 */
public class SystemUIHelp {
	
	public interface SystemUIWHHelpEvent{
		/**
		 * 
		 * @param hideStatu true if statu is hidden otherwise show
		 * @param hideNavigationBar the same as hideStatu
		 */
        void onSystemUIWHHelpEvent(boolean hideStatu, boolean hideNavigationBar);
	}
	private Activity activity;
	private SystemUIWHHelpEvent event;
	
	public static final String TAG="SystemUIWHHelp";
	
	
	/**
	 * sdk version 14 or more has navigation bar
	 * @author xieningtao
	 *
	 */
	public static class NavigationSystemUIWHHelpHelp{
	/**
	 * need sdk version 19,it is immersive mode
	 * @param activity
	 */
	public static void hideSystemUIWHHelp(Activity activity){
		View decorView=activity.getWindow().getDecorView();
		decorView.setSystemUiVisibility(
	              View.SYSTEM_UI_FLAG_LAYOUT_STABLE//layout in the system space without reLayout
	              | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION//can layout navigation bar
	              | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN//can layout status
	             | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION // hide nav bar
	             | View.SYSTEM_UI_FLAG_FULLSCREEN // hide status bar
	            | View.SYSTEM_UI_FLAG_IMMERSIVE);//need sdk version 19
	}
	
	public static void hideStatusSystemUIWHHelp(Activity activity){
		View decorView=activity.getWindow().getDecorView();
		decorView.setSystemUiVisibility(
	              View.SYSTEM_UI_FLAG_LAYOUT_STABLE//layout in the system space without reLayout
	              | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN//can layout status
	             | View.SYSTEM_UI_FLAG_FULLSCREEN // hide status bar
	            );
	}
	
	
	/**
	 * need sdk 16
	 * @param activity
	 */
	public static void showStatuSystemUIWHHelp(Activity activity){
		View decorView=activity.getWindow().getDecorView();
		decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE
				|View.SYSTEM_UI_FLAG_HIDE_NAVIGATION//hide navigation
				|View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION);
	}
	
	public static void showNavigationSystemUIWHHelp(Activity activity){
		View decorView=activity.getWindow().getDecorView();
		decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE
				|View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION//layout navigation space
				|View.SYSTEM_UI_FLAG_FULLSCREEN	//hide status
				|View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
	}
}
	
	
	public SystemUIHelp(Activity activity){
		this.activity=activity;
		init();
	}
	public void setSystemUIWHHelpEvent(SystemUIWHHelpEvent event){
		this.event=event;
	}
	
	/**
	 * 
	 */
	public void init(){
		 View decorView = activity.getWindow().getDecorView();
	        if(Build.VERSION.SDK_INT > 16) {
	            if(Build.VERSION.SDK_INT>=19){//layout navigation bar and statu bar
	                decorView.setSystemUiVisibility(
	                        View.SYSTEM_UI_FLAG_LAYOUT_STABLE
	                                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
	                                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
	            }else {//only layout status bar
	                int uiOptions = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
	                decorView.setSystemUiVisibility(uiOptions);
	            }
	        }else{//only layout status bar
	        	activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN);
	        	activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
	        }
	}
	
	
	public  void showVideoSystemUIWHHelp(){
		 if (Build.VERSION.SDK_INT < 16)
         {
             WindowManager.LayoutParams attrs = activity.getWindow().getAttributes();
             attrs.flags &= ~WindowManager.LayoutParams.FLAG_FULLSCREEN;
             activity.getWindow().setAttributes(attrs);
             if(event!=null)event.onSystemUIWHHelpEvent(false, true);
         }else{
             View decorView = activity.getWindow().getDecorView();
             if(Build.VERSION.SDK_INT>=19){
                 decorView.setSystemUiVisibility(
                         View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                                 | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                                 | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
                 if(event!=null)event.onSystemUIWHHelpEvent(false, false);

             }else {
                 int uiOptions = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
                 decorView.setSystemUiVisibility(uiOptions);
                 if(event!=null)event.onSystemUIWHHelpEvent(false, true);
             }
         }
	}
	
	public  void hideVideoSystemUIWHHelp(){
		if (Build.VERSION.SDK_INT < 16)
        {
            WindowManager.LayoutParams attrs = activity.getWindow().getAttributes();
            attrs.flags |= WindowManager.LayoutParams.FLAG_FULLSCREEN;
            activity.getWindow().setAttributes(attrs);
            if(event!=null)event.onSystemUIWHHelpEvent(true, false);
        }else{
            View decorView = activity.getWindow().getDecorView();
            if(Build.VERSION.SDK_INT>=19){
                decorView.setSystemUiVisibility(
                        View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION // hide nav bar
                                | View.SYSTEM_UI_FLAG_FULLSCREEN // hide status bar
                                | View.SYSTEM_UI_FLAG_IMMERSIVE);
                if(event!=null)event.onSystemUIWHHelpEvent(true, true);
            }else {
                int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
                decorView.setSystemUiVisibility(uiOptions);
                if(event!=null)event.onSystemUIWHHelpEvent(true, false);
            }
        }
	}

	public static void showSoftKeyboard(Context context,View view){
		InputMethodManager imm = (InputMethodManager)context.getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.showSoftInput(view, InputMethodManager.SHOW_FORCED);
	}
	public static void hideSoftKeyboard(Context context,View view){
		InputMethodManager imm = (InputMethodManager)context.getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.hideSoftInputFromWindow(view.getWindowToken(), 0); //强制隐藏键盘
	}

}
	
	
