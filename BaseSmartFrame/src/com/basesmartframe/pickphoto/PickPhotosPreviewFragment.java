/**
 * @(#)ViewPhotosFragment.java, 2015年1月16日.
 * <p/>
 * Copyright 2015 netease, Inc. All rights reserved.
 * Netease PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.basesmartframe.pickphoto;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.basesmartframe.R;
import com.basesmartframe.baseui.BaseFragment;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.sf.utils.baseutil.SFFileHelp;
import com.sf.utils.baseutil.SFToast;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * @author xltu
 */
public class PickPhotosPreviewFragment extends BaseFragment {

    protected TextView mImageIndexText;

    protected Button mComplete;

    protected CheckBox mSelectCheckBox;

    protected View mBottomBar;

    protected ViewPager mViewPager;

    protected ViewPagerAdapter mAdapter;

    protected static ArrayList<ImageBean> mImageGroupData;

    protected ArrayList<ImageBean> mChoosedData = new ArrayList<ImageBean>();

    public static final String CHOOSE_DATA_LIST = "choose_data_list";

    public static final String INDEX = "index";

    public static final String CAN_CHOOSE_IMAGE = "can_choose_data";

    private int mMaxImageNum = 4;
    private boolean mCanChooseImage = true;
    private int mCurPhotoIndex = 0;

    private View mSelectLayout;

    public static void setImageListData(ArrayList<ImageBean> imageGroup) {
        mImageGroupData = imageGroup;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.view_pick_photo_fragment_layout, container, false);
        initViews(view);
        return view;
    }

    public void initViews(View view) {
        Bundle bundle = getArguments();
        if (bundle != null) {
            mMaxImageNum = bundle.getInt(PickPhotosFragment.MAX_IMAGE_NUM, 4);
            mCanChooseImage = bundle.getBoolean(CAN_CHOOSE_IMAGE, true);
            mChoosedData = getPhotoListFromArg(bundle, CHOOSE_DATA_LIST);
            mCurPhotoIndex = bundle.getInt(INDEX, 0);
        }
        mImageIndexText = (TextView) view.findViewById(R.id.image_index_tv);
        mSelectLayout = view.findViewById(R.id.select_layout);
        mViewPager = (ViewPager) view.findViewById(R.id.viewpager);
        mSelectCheckBox = (CheckBox) view.findViewById(R.id.select_checkbox);
        mSelectCheckBox.setClickable(false);
        mBottomBar = view.findViewById(R.id.bottom_bar);
        mComplete = (Button) view.findViewById(R.id.pick_complete);
        mViewPager.setPageTransformer(true,new XTranslateTransform());
        mComplete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundles = new Bundle();
                bundles.putSerializable(CHOOSE_DATA_LIST, mChoosedData);
                Intent data = new Intent();
                data.putExtras(bundles);
                getActivity().setResult(PickPhotosFragment.RESULT_COMPLETE, data);
                getActivity().finish();
            }
        });
        view.findViewById(R.id.pick_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });
        if (!mCanChooseImage) {
            mSelectLayout.setVisibility(View.GONE);
            mComplete.setVisibility(View.GONE);
            mBottomBar.setVisibility(View.GONE);
        }
        mAdapter = new ViewPagerAdapter(getActivity());
        mViewPager.setAdapter(mAdapter);
        mViewPager.setBackgroundColor(0xFF000000);
        mViewPager.setOnPageChangeListener(new OnPageChangeListener() {

            @Override
            public void onPageSelected(int arg0) {
                updateCheckbox(arg0);
                updatePhotoIndex(arg0);
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {
            }

            @Override
            public void onPageScrollStateChanged(int arg0) {
            }
        });
        mViewPager.setCurrentItem(mCurPhotoIndex);
        updateCheckbox(mCurPhotoIndex);
        updatePhotoIndex(mCurPhotoIndex);
        updateCompleteBtn();
        mSelectLayout.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (mImageGroupData == null)
                    return;
                int current = mViewPager.getCurrentItem();
                ImageBean bean = mImageGroupData.get(current);
                if (mSelectCheckBox.isChecked()) {
                    mSelectCheckBox.setChecked(false);
                    mChoosedData.remove(bean);
                } else {
                    if (mChoosedData.size() >= mMaxImageNum) {
                        mSelectCheckBox.setChecked(false);
                        SFToast.showToast(getString(R.string.four_pics_most, mMaxImageNum));
                        return;
                    }
                    File file = new File(bean.getPath());
                    if (!file.exists()) {
                        mSelectCheckBox.setChecked(false);
                        SFToast.showToast(R.string.photo_donot_exist);
                        return;
                    }
                    mChoosedData.add(bean);
                    mSelectCheckBox.setChecked(true);
                }
                updateCompleteBtn();
            }
        });
        mAdapter.notifyDataSetChanged();
    }

    private void updateCheckbox(int position) {
        if (mImageGroupData == null || position >= mImageGroupData.size())
            return;
        ImageBean bean = mImageGroupData.get(position);
        if (mChoosedData.indexOf(bean) >= 0) {
            mSelectCheckBox.setChecked(true);
        } else {
            mSelectCheckBox.setChecked(false);
        }
    }

    private void updatePhotoIndex(int position) {
        if (mImageGroupData == null || position >= mImageGroupData.size())
            return;
        mImageIndexText.setText((position + 1) + "/" + mImageGroupData.size());
    }

    private void updateCompleteBtn() {
        if (mChoosedData.size() == 0) {
            mComplete.setEnabled(false);
        } else {
            mComplete.setEnabled(true);
        }
        mComplete.setText(getString(R.string.complete_pick, mChoosedData.size(), mMaxImageNum));
    }


    protected ArrayList<ImageBean> getPhotoListFromArg(Bundle bundle, String key) {
        ArrayList<? extends Serializable> photos = (ArrayList<? extends Serializable>) bundle.getSerializable(key);
        if (photos == null || photos.isEmpty()) {
            return new ArrayList<ImageBean>();
        }
        return (ArrayList<ImageBean>) photos;
    }

    private class ViewPagerAdapter extends PagerAdapter {

        private LayoutInflater mInflater;

        private Context mContext;

        public ViewPagerAdapter(Context context) {
            mContext = context;
            mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public int getCount() {
            if (mImageGroupData == null)
                return 0;
            return mImageGroupData.size();
        }

        @Override
        public void destroyItem(View collection, int position, Object view) {
            ((ViewPager) collection).removeView((View) view);
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == ((View) object);
        }

        @Override
        public Object instantiateItem(View container, int position) {
            View view = mInflater.inflate(R.layout.image_viewer_layout, null);
            final ImageView imageView = (ImageView) view.findViewById(R.id.photo_view);
            ImageBean bean = mImageGroupData.get(position);
            if (!TextUtils.isEmpty(bean.getPath()) && bean.getPath().startsWith("http")) {
                ImageLoader.getInstance().displayImage(bean.getPath(), imageView);
            } else {
                ImageLoader.getInstance().displayImage(SFFileHelp.pathToFilePath(bean.getPath()), imageView);
            }
            ((ViewPager) container).addView(view, 0);
            return view;
        }
    }


    @Override
    public void onDestroy() {
        setImageListData(null);
        mAdapter.notifyDataSetChanged();
        super.onDestroy();

    }

}
