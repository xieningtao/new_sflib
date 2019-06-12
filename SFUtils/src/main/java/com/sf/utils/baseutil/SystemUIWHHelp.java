package com.sf.utils.baseutil;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Build;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.KeyCharacterMap;
import android.view.KeyEvent;
import android.view.ViewConfiguration;
import android.view.WindowManager;

import com.sf.loglib.L;

import java.lang.reflect.Method;

public class SystemUIWHHelp {

	    public static final int NavigationBarUnknown = 0;
	    public static final int NavigationBarRight = 1;
	    public static final int NavigationBarBottom = 2;

	    @SuppressLint("NewApi")
		public static boolean hasNavigationBar(Context context) {
	        if (null == context) {
	            return true;
	        }
	        if(Build.VERSION.SDK_INT<14)return false;
	        boolean hasMenuKey = ViewConfiguration.get(context).hasPermanentMenuKey();
	        boolean hasBackKey = KeyCharacterMap.deviceHasKey(KeyEvent.KEYCODE_BACK);

	        return (!hasMenuKey && !hasBackKey);
	    }

	    public static int getStatusBarHeight(Context context) {
	        Resources res = context.getResources();
	        int resourceId = res.getIdentifier("status_bar_height", "dimen", "android");
	        return (resourceId > 0 ? res.getDimensionPixelSize(resourceId) : -1);
	    }

	    public static int getNavigationBarHeight(Context context) {
	        Resources res = context.getResources();
	        int resourceId = res.getIdentifier("navigation_bar_height", "dimen", "android");
	        return (resourceId > 0 ? res.getDimensionPixelSize(resourceId) : -1);
	    }

	    public static int getNavigationBarLandscapeHeight(Context context) {
	        Resources res = context.getResources();
	        int resourceId = res.getIdentifier("navigation_bar_height_landscape", "dimen", "android");
	        return (resourceId > 0 ? res.getDimensionPixelSize(resourceId) : -1);
	    }

	    public static int getNavigationBarLandscapeWidth(Context context) {
	        Resources res = context.getResources();
	        int resourceId = res.getIdentifier("navigation_bar_width", "dimen", "android");
	        return (resourceId > 0 ? res.getDimensionPixelSize(resourceId) : -1);
	    }

	    //warn this function does not work when keyboard is visible
	    public static int getNavigationBarLocal(Activity activity) {
	        if (null == activity) {
	            return NavigationBarUnknown;
	        }

	        Rect visibleFrame = new Rect();
	        activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(visibleFrame);

	        int screenW = getScreenRealWidth(activity);
	        int screenH = getScreenRealHeight(activity);

	        if(-1 == screenH || -1 == screenW) {
	            return  NavigationBarUnknown;
	        }

	        if (screenH == visibleFrame.bottom) {
	            return NavigationBarRight;
	        } else if (screenW == visibleFrame.right) {
	            return NavigationBarBottom;
	        }

	        return NavigationBarUnknown;
	    }


	    public static int getScreenRealHeight(Activity activity) {
	        if (null == activity) {
	            return -1;
	        }

	        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
	            Point sz = getScreenRealSizeAPI17(activity);
	            return sz.y;
	        }

	        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH
	                && Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR1) {
	            return getRawSize(activity, "getRawHeight", "getScreenRealHeight");
	        }

	        return getScreenSizeAPILower(activity, true);
	    }

	    public static int getScreenRealWidth(Activity activity) {
	        if (null == activity) {
	            return -1;
	        }

	        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
	            Point sz = getScreenRealSizeAPI17(activity);
	            return sz.x;
	        }

	        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH
	                && Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR1) {
	            return getRawSize(activity, "getRawWidth", "getScreenRealWidth");
	        }

	        return getScreenSizeAPILower(activity, false);
	    }

	    public static boolean hideNavigationWithLowProfile() {
	        return apiLevelLowerThan16();
	    }

	    @SuppressLint("NewApi")
		private static Point getScreenRealSizeAPI17(Activity activity) {
	        Point sz = new Point();
	        WindowManager wm = activity.getWindowManager();
	        Display display = wm.getDefaultDisplay();
	        display.getRealSize(sz);
	        return sz;
	    }

	    private static int getRawSize(Activity activity, String name, String exceptionTag) {
	        try {
	            Method mGetRawH = Display.class.getMethod(name);
	            WindowManager wm = activity.getWindowManager();
	            Display display = wm.getDefaultDisplay();
	            return (Integer) mGetRawH.invoke(display);
	        } catch (Exception e) {
	            L.error("SystemUIWHHelp", "SystemUIWHHelp " + exceptionTag + " Exception " + e.toString());
	            return -1;
	        }
	    }

	    private static int getScreenSizeAPILower(Activity activity, boolean heightOrWidth) {
	        WindowManager wm = activity.getWindowManager();
	        Display display = wm.getDefaultDisplay();
	        DisplayMetrics dm = new DisplayMetrics();
	        display.getMetrics(dm);
	        return (heightOrWidth ? dm.heightPixels : dm.widthPixels);
	    }

	    private static boolean apiLevelLowerThan16() {
	        return Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN;
	    }
}

