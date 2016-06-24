package com.example.androidtv;

import android.os.Bundle;
import android.support.v13.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.basesmartframe.baseui.BaseActivity;
import com.sf.loglib.L;

/**
 * Created by xieningtao on 15-9-11.
 */
public class TVBaseViewPageActivity extends BaseActivity {
    protected final String TAG = getClass().getName();

    protected ViewPager mViewPager;
    protected FragmentPagerAdapter mHomePageAdapter;
    private TextView mIndex_tv;
    private ImageView mLeftArrow_iv;
    private ImageView mRightArrow_iv;
    private ImageView mLog_iv;

    protected int mVerticalExtraSpace;
    protected int mHorizontalExtraSpace;

    public final static String VERTICAL_EXTRA_SPACE = "vertical_extra_space";
    public final static String HORIZONTAL_EXTRA_SPACE = "horizontal_extra_space";
    public final static String PAGE_INDEX = "page_index";
    public final static String GAME_ID = "game_id";

    protected int mPageIndex = -1;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tvgridviewhome_activity);
        initView();
        measureChildren();
        mVerticalExtraSpace = getVerticalExtraSpace();
        mHorizontalExtraSpace = getHorizontalExtraSpace();
        L.info(TAG, "verticalSpace: " + mVerticalExtraSpace + " horizontalSpace: " + mHorizontalExtraSpace);
        initListener();
    }

    private void initView() {
        mViewPager = (ViewPager) findViewById(R.id.home_vp);
        mIndex_tv = (TextView) findViewById(R.id.pageIndex_tv);
        mLeftArrow_iv = (ImageView) findViewById(R.id.left_arrow_iv);
        mRightArrow_iv = (ImageView) findViewById(R.id.right_arrow_iv);
        mLog_iv = (ImageView) findViewById(R.id.logo_iv);
    }

    private void initListener() {
        mHomePageAdapter = createPagerAdapter();
        if (mHomePageAdapter == null) {
            throw new NullPointerException("createPagerAdapter return null");
        }
        mViewPager.setAdapter(mHomePageAdapter);
        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                updateView(i);
                mPageIndex = i;
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
        //default
        updateView(0);
    }

    private void updateView(int index) {
        updateArrow(index);
        updatePageIndex(index);
//        Fragment fragment = mFragmentMap.get(index);
//        if (null != fragment) {
//            TVCategoryFragment categoryFragment = (TVCategoryFragment) fragment;
//            categoryFragment.notifyDatasetChange();
//        }
    }

    protected FragmentPagerAdapter createPagerAdapter() {
        return null;
    }


    private void updateArrow(int index) {
        int count = mHomePageAdapter.getCount();
        if (index == 0) {
            showArrow(mLeftArrow_iv, false);
            showArrow(mRightArrow_iv, true);
        } else if (index == count - 1) {
            showArrow(mLeftArrow_iv, true);
            showArrow(mRightArrow_iv, false);
        } else {
            showArrow(mLeftArrow_iv, true);
            showArrow(mRightArrow_iv, true);
        }

    }

    private void updatePageIndex(int i) {
        final int count = mHomePageAdapter.getCount();
        String curPage = (i + 1) + "";
        String content = curPage + "/" + count;
        int curPageStr_length = curPage.length();
        SpannableString indexSpan = new SpannableString(content);
        indexSpan.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.actionbar_blue)), 0, curPageStr_length, Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
        mIndex_tv.setText(indexSpan);
    }

    private void showArrow(View view, boolean show) {
        if (show) {
            if (view.getVisibility() == View.VISIBLE) {
                return;
            }
            view.setVisibility(View.VISIBLE);
        } else {
            if (view.getVisibility() != View.VISIBLE) {
                return;
            }
            view.setVisibility(View.INVISIBLE);
        }
    }

    private void measureChildren() {
        int widthSpec = View.MeasureSpec.makeMeasureSpec(ViewGroup.LayoutParams.WRAP_CONTENT, View.MeasureSpec.UNSPECIFIED);
        int heightSpec = widthSpec;
        mLog_iv.measure(widthSpec, heightSpec);
        mIndex_tv.measure(widthSpec, heightSpec);
        mLeftArrow_iv.measure(widthSpec, heightSpec);
        mRightArrow_iv.measure(widthSpec, heightSpec);
    }

    private int getVerticalExtraSpace() {
        int viewSpace = mIndex_tv.getMeasuredHeight() + mLog_iv.getMeasuredHeight();
        ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) mViewPager.getLayoutParams();
        ViewGroup.MarginLayoutParams indexMarginLayoutParams = (ViewGroup.MarginLayoutParams) mIndex_tv.getLayoutParams();
        return viewSpace + marginLayoutParams.topMargin + marginLayoutParams.bottomMargin + indexMarginLayoutParams.bottomMargin;
    }

    private int getHorizontalExtraSpace() {
        int viewSpace = mLeftArrow_iv.getMeasuredWidth() + mRightArrow_iv.getMeasuredWidth();
        ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) mViewPager.getLayoutParams();
        return viewSpace + marginLayoutParams.leftMargin + marginLayoutParams.rightMargin;
    }

    protected void notifyDataSetChange() {
        if (mHomePageAdapter != null) {
            mHomePageAdapter.notifyDataSetChanged();
        }
        updateView(0);
    }


}
