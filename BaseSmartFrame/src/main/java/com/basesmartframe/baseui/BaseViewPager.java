package com.basesmartframe.baseui;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import androidx.legacy.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.basesmartframe.R;
import com.sflib.CustomView.baseview.RoundRecPagerSlidingTabStrip;

import java.util.ArrayList;
import java.util.List;
/**
 * multi-kinds of slidingTabStrip
 * @author xieningtao
 *
 * @param <T>
 */
public abstract class BaseViewPager<T extends BaseViewPager.ViewPagerTitle>
		extends BaseActivity implements ViewPager.OnPageChangeListener {
	public interface ViewPagerTitle {
		String composeTitle();
	}

	protected RoundRecPagerSlidingTabStrip tabStrip;
	protected ViewPager mViewPager;
	protected VideoShowListAdapter mAdapter;

	private final List<T> data = new ArrayList<T>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.slidingstrip_viewpager);
		init();
	}

	private void init() {
		tabStrip = findViewById(R.id.tabs);
		mViewPager = findViewById(R.id.pager);
		mAdapter = new VideoShowListAdapter(getFragmentManager());
		mViewPager.setAdapter(mAdapter);
	}

	protected void notifyDatasetChange() {
		mAdapter.notifyDataSetChanged();
	}

	/**
	 * add all  title data,subclass should invoke this method to update title
	 * @param titleData
	 */
	protected void updateStrip(List<T> titleData) {
		if(null==titleData)return;
		data.clear();
		data.addAll(titleData);
		tabStrip.setViewPager(mViewPager);
		tabStrip.setOnPageChangeListener(this);
		mAdapter.notifyDataSetChanged();
	}

	protected abstract Fragment getFragment(int i);

	protected Bundle getBundle(int i) {
		return null;
	}

	class VideoShowListAdapter extends FragmentPagerAdapter {

		public VideoShowListAdapter(FragmentManager fm) {
			super(fm);

		}

		@Override
		public Fragment getItem(int i) {
			Fragment fragment = getFragment(i);
			Bundle bundle = getBundle(i);
			if (null != fragment && null != bundle) {
				fragment.setArguments(bundle);
			}
			return fragment;
		}

		@Override
		public int getCount() {
			return data.size();
		}

		@Override
		public CharSequence getPageTitle(int position) {
			return data.get(position).composeTitle();
		}

	}
	
	@Override
	public void onPageScrollStateChanged(int arg0) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void onPageSelected(int arg0) {
		// TODO Auto-generated method stub
		
	}
}
