package com.sf.utils.baseutil;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

/**
 * @author xieningtao
 *
 */
 class AdapterHelp {
	public static int getSingleItemHeight(View convertView,int count){
		if(convertView==null)return 0;
		if(count<=0)return 0;
		int height=0;
		View view = null;
		for(int i=0;i<count;i++){
			convertView.measure(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
			height+=view.getMeasuredHeight();
		}
		return height;
	}
	
	
	public static int getItemHeights(ListView listView,View convertView,int count){
		if(convertView==null)return 0;
		if(count<=0)return 0;
		int height=0;
		View view = null;
		for(int i=0;i<count;i++){
			convertView.measure(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
			height+=view.getMeasuredHeight()+listView.getDividerHeight();
		}
		return height;
	}
	
}
