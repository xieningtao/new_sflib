package com.basesmartframe.baseview;

import com.basesmartframe.log.L;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

/**
 * it has problem
 * 
 * @author xieningtao
 *
 */
public class ScrollViewPager extends ViewPager {

	private BannerPagerAdapter mWrappAdapter;

	public ScrollViewPager(Context context) {
		super(context);
	}

	public ScrollViewPager(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public void setAdapter(BannerPagerAdapter adapter) {
		mWrappAdapter = adapter;
		super.setAdapter(mWrappAdapter);
	}

	public void setCurrentItem(int cur, boolean smooth) {
		super.setCurrentItem(cur + 1, smooth);
	}

	public void setCurrentItem(int cur) {
		super.setCurrentItem(cur + 1);
	}

	@Override
	public void setOnPageChangeListener(final OnPageChangeListener listener) {
		super.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int position) {
				int pos = getCorrectPosition(position);
				listener.onPageSelected(pos);
				L.info(this, "pagenum: " + pos);
			}

			@Override
			public void onPageScrolled(int position, float arg1, int arg2) {
				int pos = getCorrectPosition(position);
				listener.onPageScrolled(pos, arg1, arg2);
				// L.info(this, "pagenum: " + (arg0 - 1));
			}

			@Override
			public void onPageScrollStateChanged(int state) {
				listener.onPageScrollStateChanged(state);
				L.info(this, "getCurrentItem: " + getCurrentItem());
				L.info(this, "state: " + state);
				// L.info(this, "count: " + (mWrappAdapter.getCount() - 1));
				if (state == SCROLL_STATE_IDLE && getAdapter().getCount() > 1) {
					if (getCurrentItem() == 0) {
						setCurrentItem(getAdapter().getCount() - 1, false);
						L.info(this, "go to last page");
					} else if (getCurrentItem() == mWrappAdapter.getCount() - 1) {
						setCurrentItem(0, false);
						L.info(this, "go to first page");
					}
				} else if (state == SCROLL_STATE_SETTLING
						&& getCurrentItem() == mWrappAdapter.getCount() - 1) {
					L.info(this, "go to first page");
					setCurrentItem(0, false);
				}
			}
		});
	}

	private int getCorrectPosition(int position) {
		if (position == 0) {
			return mWrappAdapter.getCount() - 1;
		} else if (position == mWrappAdapter.getCount() - 1) {
			return 0;
		}
		return position - 1;
	}

	@Override
	public PagerAdapter getAdapter() {
		return mWrappAdapter.getWrappedAdapter();
	}

	public static class BannerPagerAdapter extends PagerAdapter {

		private PagerAdapter mWrappedAdapter;

		public BannerPagerAdapter(PagerAdapter adapter) {
			this.mWrappedAdapter = adapter;
		}

		public PagerAdapter getWrappedAdapter() {
			return mWrappedAdapter;
		}

		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			if (position == 0) {
				return mWrappedAdapter.instantiateItem(container,
						mWrappedAdapter.getCount() - 1);
			} else if (position == mWrappedAdapter.getCount() + 1) {
				return mWrappedAdapter.instantiateItem(container, 0);
			} else {
				return mWrappedAdapter.instantiateItem(container, position - 1);
			}

		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			mWrappedAdapter.destroyItem(container, position, object);
		}

		@Override
		public int getCount() {
			if (mWrappedAdapter.getCount() > 1) {
				return mWrappedAdapter.getCount() + 2;
			}
			return 0;
		}

		@Override
		public boolean isViewFromObject(View view, Object object) {
			return mWrappedAdapter.isViewFromObject(view, object);
		}

		// @Override
		// public int getItemPosition(Object object) {
		// return POSITION_NONE;
		// }

	}

}
