/**
 * @(#)ViewPhotosFragment.java, 2015年1月16日. 
 * 
 * Copyright 2015 netease, Inc. All rights reserved.
 * Netease PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.basesmartframe.pickphoto;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.netease.huatian.R;
import com.netease.huatian.base.activity.BaseFragmentActivity;
import com.netease.huatian.base.fragment.BaseFragment;
import com.netease.huatian.base.image.NetworkImageFetcher;
import com.netease.huatian.module.msgsender.OnBackPressedListener;
import com.netease.huatian.utils.BundleUtils;
import com.netease.huatian.utils.ImgUtils;
import com.netease.huatian.view.CustomToast;
import com.netease.huatian.view.ImageViewPager;
import com.netease.huatian.view.ImageViewPager.OnScrollChangedListener;
import com.netease.huatian.view.ImageViewTouch;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 * @author xltu
 *
 */
public class PickPhotosPreviewFragment extends BaseFragment implements OnBackPressedListener{
    
    protected TextView mImageIndexText;
    
    protected TextView mComplete;
    
    protected CheckBox mSelectCheckBox;
    
    protected View mBottomBar;
    
    protected ImageViewPager mViewPager;
    
    protected ViewPagerAdapter mAdapter;
    
    protected static ArrayList<ImageBean> mImageGroupData;
    
    protected ArrayList<ImageBean> mChoosedData = new ArrayList<ImageBean>();
    
    public static final String CHOOSE_DATA_LIST = "choose_data_list";
    
    public static final String INDEX = "index";
    
    public static final String CAN_CHOOSE_IMAGE = "can_choose_data";
    
    private int mMaxImageNum = 4;
    
    private View mSelectLayout;
    
    public static void setImageListData(ArrayList<ImageBean> imageGroup){
        mImageGroupData = imageGroup;
    }
 
    @Override
    public View onCreateViewNR(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.view_pick_photo_fragment_layout,container, false);
        initViews(view);
        return view;
    }

    @Override
    public void initViews(View view) {
        if(getActivity() instanceof BaseFragmentActivity){
            ((BaseFragmentActivity)getActivity()).setOnBackPressedListener(this);
        }
        Bundle bundle = getArguments();
        if(bundle != null){
            mMaxImageNum = BundleUtils.getInt(bundle, PickPhotosFragment.MAX_IMAGE_NUM, 4);
        }
        mSelectLayout = view.findViewById(R.id.select_layout);
        mChoosedData = getPhotoListFromArg(getArguments(), CHOOSE_DATA_LIST);
        mViewPager = (ImageViewPager) view.findViewById(R.id.viewpager);
        mSelectCheckBox = (CheckBox) view.findViewById(R.id.select_checkbox);
        mSelectCheckBox.setClickable(false);
        mBottomBar = view.findViewById(R.id.bottom_bar);
        if(!BundleUtils.getBoolean(bundle, CAN_CHOOSE_IMAGE, true)){
            mSelectLayout.setVisibility(View.GONE);
            mComplete.setVisibility(View.GONE);
            mBottomBar.setVisibility(View.GONE);
        }
        mAdapter = new ViewPagerAdapter(getActivity());
        mViewPager.setAdapter(mAdapter);
        mViewPager.setBackgroundColor(0xFF000000);
        mViewPager.setOnScrollChangedListener(mListener);
        mViewPager.setOnPageChangeListener(new OnPageChangeListener() {
            
            @Override
            public void onPageSelected(int arg0) {
                updateCheckbox(arg0);
                updatePhotoIndex(arg0);
            }
            
            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {}
            
            @Override
            public void onPageScrollStateChanged(int arg0) {}
        });
        mViewPager.setClickListener(new ImageViewPager.OnClickListener() {

            @Override
            public void onSingleClick() {
                if(BundleUtils.getBoolean(getArguments(), CAN_CHOOSE_IMAGE, true))
                    showOrHidenContentText();
                showOrHideActionBar(mActionBarView.getVisibility() == View.GONE);
            }

            @Override
            public void onLongClick() {}
        });
        int index = BundleUtils.getInt(getArguments(), INDEX, 0);
        mViewPager.setCurrentItem(index);
        updateCheckbox(index);
        updatePhotoIndex(index);
        updateCompleteBtn();
        mSelectLayout.setOnClickListener(new View.OnClickListener() {
                
                @Override
                public void onClick(View v) {
                        if(mImageGroupData == null)
                            return;
                        int current = mViewPager.getCurrentItem();
                        ImageBean bean = mImageGroupData.get(current);
                        if (mSelectCheckBox.isChecked()) {
                            mSelectCheckBox.setChecked(false);
                            mChoosedData.remove(bean);
                        } else {
                            if(mChoosedData.size() >= mMaxImageNum){
                                mSelectCheckBox.setChecked(false);
                                CustomToast.showToast(getActivity(), getString(R.string.four_pics_most,mMaxImageNum));
                                return;
                            }
                            File file = new File(bean.getPath());
                            if(!file.exists()){
                                mSelectCheckBox.setChecked(false);
                                CustomToast.showToast(getActivity(), R.string.photo_donot_exist);
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
    
    private void updateCheckbox(int position){
        if(mImageGroupData == null || position >= mImageGroupData.size())
            return;
        ImageBean bean = mImageGroupData.get(position);
        if(mChoosedData.indexOf(bean) >= 0){
            mSelectCheckBox.setChecked(true);
        }else{
            mSelectCheckBox.setChecked(false);
        }
    }
    
    private void updatePhotoIndex(int position){
        if(mImageGroupData == null || position >= mImageGroupData.size())
            return;
        mImageIndexText.setText((position + 1) + "/" + mImageGroupData.size());
    }
    
    private void updateCompleteBtn(){
        if(mChoosedData.size() == 0){
            mComplete.setEnabled(false);
        } else{
            mComplete.setEnabled(true);
        }
        mComplete.setText(getString(R.string.complete_pick, mChoosedData.size(),mMaxImageNum));
    }
    
    private void showOrHidenContentText() {
        Animation anim = null;
        int visible = mBottomBar.getVisibility();
        Context context = getActivity();
        if (visible == View.GONE) {
            mBottomBar.setVisibility(View.VISIBLE);
            if (context != null) {
                anim = AnimationUtils.loadAnimation(context, R.anim.base_slide_bottom_in);
                mBottomBar.startAnimation(anim);
            }
        } else {
            if (context != null) {
                anim = AnimationUtils.loadAnimation(context, R.anim.base_slide_bottom_out);
                anim.setAnimationListener(new AnimationListener() {

                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        mBottomBar.setVisibility(View.GONE);
                    }
                });
                mBottomBar.startAnimation(anim);
            } else {
                mBottomBar.setVisibility(View.GONE);
            }
        }
    }

    
    protected ArrayList<ImageBean> getPhotoListFromArg(Bundle bundle, String key) {
        ArrayList<? extends Serializable> photos = (ArrayList<? extends Serializable>) bundle.getSerializable(key);
        if (photos == null || photos.isEmpty()) {
            return new ArrayList<ImageBean>();
        }
        return (ArrayList<ImageBean>) photos;
    }

    @Override
    public View onCreateActionBarView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View actionBar = inflater.inflate(R.layout.view_pick_photo_actionbar, container, false);
        mImageIndexText = (TextView) actionBar.findViewById(R.id.image_index_text);
        mComplete = (TextView) actionBar.findViewById(R.id.complete);
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
        return actionBar;
    }
    
    @Override
    protected void addContentView(LayoutInflater inflater, RelativeLayout root, Bundle savedInstanceState) {
        View content = onCreateViewNR(inflater, root, savedInstanceState);
        if (content != null) {
            RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,
                    RelativeLayout.LayoutParams.MATCH_PARENT);
            root.addView(content, lp);
        }
    }
    
    private Bitmap getOnLoadingBitmap(int width, int height) {
        try {
            Bitmap newbmp = ImgUtils.createBitmap(width, height, Config.ARGB_4444);
            Bitmap bitmap = ImgUtils.decodeResource(getResources(), R.drawable.pick_photos_preview_image);
            Canvas cv = new Canvas(newbmp);
            cv.drawBitmap(bitmap, width / 2 - bitmap.getWidth() / 2, height / 2 - bitmap.getHeight() / 2, null);
            cv.save(Canvas.ALL_SAVE_FLAG);
            cv.restore();
            return newbmp;
        } catch (Exception e) {}
        return null;
    }
    
    private class ViewPagerAdapter extends PagerAdapter {

        private NetworkImageFetcher mImageFetcher;
        
        private LayoutInflater mInflater;
        
        private Context mContext;
        
        public ViewPagerAdapter(Context context){
            mContext = context;
            int size[] = ImgUtils.getBestSize(context);
            Bitmap bitmap = getOnLoadingBitmap(size[0], size[1]);
            mImageFetcher = new NetworkImageFetcher(context, size[0], size[1]);
            if(bitmap != null){
                mImageFetcher.setLoadingImage(bitmap);
            }
            mImageFetcher.setImageFadeIn(false);
            mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }
        
        @Override
        public int getCount() {
            if(mImageGroupData == null)
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
            final ImageViewTouch imageView = (ImageViewTouch) view.findViewById(R.id.imageview);
            imageView.setIsLocked(false);
            final ImageViewPager viewPager = (ImageViewPager) container;
            imageView.setViewPager(viewPager);
            imageView.setGestureDetector(new GestureDetector(mContext, new GestureDetector.SimpleOnGestureListener() {
                public boolean onDoubleTap(MotionEvent e) {
                    if (imageView.getScale() > 1.0f) {
                        imageView.zoomTo(1.0f);
                    } else {
                        imageView.zoomTo(2.0f);
                    }
                    return true;
                }

                public boolean onSingleTapConfirmed(MotionEvent e) {
                    viewPager.onSingleClick();
                    return true;
                }

                @Override
                public void onLongPress(MotionEvent e) {
                }

            }));
            ImageBean bean = mImageGroupData.get(position);
            mImageFetcher.loadImage(bean.getPath(), imageView);
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

    @Override
    public void onBackClick() {
        onBackPressed();
    }

    @Override
    public boolean onBackPressed() {
        Bundle bundles = new Bundle();
        bundles.putSerializable(CHOOSE_DATA_LIST, mChoosedData);
        Intent data = new Intent();
        data.putExtras(bundles);
        getActivity().setResult(PickPhotosFragment.RESULT_BACK, data);
        getActivity().finish();
        return true;
    }
    
    protected OnScrollChangedListener mListener = new OnScrollChangedListener() {

        @Override
        public void onScrollChanged(ViewPager viewPager, int l, int t, int oldl, int oldt) {
            int resetItem = -1;
            for (int i = viewPager.getChildCount() - 1; i >= 0; i--) {
                View child = viewPager.getChildAt(i);
                if (child.getLeft() == l) {
                    resetItem = i;
                    break;
                }
            }
            if (resetItem >= 0) {
                for (int i = viewPager.getChildCount() - 1; i >= 0; i--) {
                    if (resetItem != i) {
                        ImageViewTouch view = (ImageViewTouch) viewPager.getChildAt(i).findViewById(R.id.imageview);
                        Bitmap bitmap = view.getDisplayBitmap();
                        if (bitmap != null) {
                            view.setImageBitmapResetBase(view.getDisplayBitmap(), true);
                        }
                    }
                }
            }
        }
    };
    

}
