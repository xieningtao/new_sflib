package com.sf.SFSample.ui;

import android.app.Fragment;
import android.os.Bundle;

import com.basesmartframe.baseui.BaseViewPager;

import java.util.ArrayList;
import java.util.List;

public class ViewPagerTest extends BaseViewPager<ViewPagerTest.MyViewPagerTitle>{
	
	public static class MyViewPagerTitle implements BaseViewPager.ViewPagerTitle{

		private final String name;
		
		public MyViewPagerTitle(String name) {
			super();
			this.name = name;
		}

		@Override
		public String composeTitle() {
			return name;
		}
		
	}
	
	private List<MyViewPagerTitle> titles=new ArrayList<MyViewPagerTitle>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		for(int i=0;i<8;i++){
			titles.add(new MyViewPagerTitle("title"+i));
		}
		updateStrip(titles);
	}

	@Override
	protected Fragment getFragment(int i) {
		return Fragment.instantiate(this, PullListFragment.class.getName());
	}

}
