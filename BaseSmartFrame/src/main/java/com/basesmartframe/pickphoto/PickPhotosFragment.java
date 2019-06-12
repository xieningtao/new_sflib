/**
 * @(#)PickPhotosFragment.java, 2015年1月9日.
 * <p/>
 * Copyright 2015 netease, Inc. All rights reserved.
 * Netease PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.basesmartframe.pickphoto;

import android.app.Activity;
import android.app.LoaderManager;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.TranslateAnimation;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.basesmartframe.R;
import com.basesmartframe.baseui.BaseFragment;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.sf.loglib.L;
import com.sf.loglib.file.SFFileHelp;
import com.sf.utils.baseutil.SFToast;
import com.sf.utils.baseutil.SystemUIWHHelp;
import com.sf.utils.baseutil.UnitHelp;

import java.io.File;
import java.util.ArrayList;

/**
 * @author xltu
 */
public class PickPhotosFragment extends BaseFragment implements LoaderManager.LoaderCallbacks<Cursor> {

    private Context mContext;

    private GridView mGridView;

    public static final String CHOOSE_PIC="choose_pic";

    private ArrayList<ImageBean> mAllImageData = new ArrayList<ImageBean>();

    private ArrayList<ImageGroup> mGroupList = new ArrayList<ImageGroup>();

    private ImageGroup mCurrentGroup;

    private GridAdapter mImageAdapter;

    private ImageGroupAdapter mGroupAdapter;

    private static final int CURSOR_LOADER = 1;

    private ArrayList<ImageBean> mChoosedData = new ArrayList<ImageBean>();

    private ListView mImageGroupList;

    private View mAlbums;

    private TextView mPreview;

    private View mImageGroupBackground;

    private View mImageGroupLayout;

    public static final int REQUEST_FOR_PREVIEW = 1001;

    public static final int REQUEST_PICK = 1002;

    protected static final int REQUEST_ADD_IMAGE = 1003;

    public static final int RESULT_BACK = 101;

    public static final int RESULT_COMPLETE = 102;

    public static final String ALL_PICTURE_PATH = "huatian_all_pictures";

    protected Uri mPhotoUri;

    private static final int ACTION_COMPLETE = 1001;

    private Button mComplete;
    /**
     * 标识相册列表的展示状态，0：未展示；1：动画播放中；2：展示中；
     */
    private int mImageGroupShowState = 0;

    public static final String MAX_IMAGE_NUM = "max_image_num";

    private int mMaxImageNum = 4;

    private int mImageSize;

    private static final String[] STORE_IMAGES = {
            MediaStore.Images.Media.DISPLAY_NAME, MediaStore.Images.Media._ID, MediaStore.Images.Media.DATA
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.pick_photos_fragment, null);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews(view);
    }

    public void initViews(View view) {
        mContext = getActivity();
        Bundle bundle = getArguments();
        if (bundle != null) {
            ArrayList<ImageBean> photos = (ArrayList<ImageBean>) bundle.getSerializable(PickPhotosPreviewFragment.CHOOSE_DATA_LIST);
            if (photos != null) {
                mChoosedData = photos;
            }
            mMaxImageNum = bundle.getInt(MAX_IMAGE_NUM, 4);
        }
        mGridView = (GridView) view.findViewById(R.id.grid_view);
        mImageAdapter = new GridAdapter(getActivity());
        mGridView.setAdapter(mImageAdapter);
        mGridView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                if (scrollState == SCROLL_STATE_IDLE) {
                    ImageLoader.getInstance().resume();
                } else {
                    ImageLoader.getInstance().pause();
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

            }
        });
        mImageGroupList = (ListView) view.findViewById(R.id.image_group_list);
        mAlbums = view.findViewById(R.id.albums_layout);
        mPreview = (TextView) view.findViewById(R.id.preview);
        mImageGroupBackground = view.findViewById(R.id.image_group_background);
        mImageGroupLayout = view.findViewById(R.id.image_group_layout);
        mComplete = (Button) view.findViewById(R.id.pick_complete);
        mComplete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent();
                intent.putExtra(CHOOSE_PIC,mChoosedData);
                getActivity().setResult(Activity.RESULT_OK,intent);
                getActivity().finish();
            }
        });
        view.findViewById(R.id.pick_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });
        mAlbums.setEnabled(false);
        mPreview.setEnabled(false);
        updatePreviewState();
        mGroupAdapter = new ImageGroupAdapter(getActivity(), mGroupList);
        mImageGroupList.setAdapter(mGroupAdapter);
        mAlbums.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                L.info(PickPhotosFragment.this, "mImageGroupShowState" + mImageGroupShowState);
                if (mImageGroupShowState == 0) {
                    showImageGroupList();
                } else if (mImageGroupShowState == 2) {
                    hideImageGroupList();
                }
            }
        });
        mImageGroupBackground.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (mImageGroupShowState == 2)
                    hideImageGroupList();
            }
        });
        mPreview.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putInt(PickPhotosPreviewFragment.INDEX, 0);
                bundle.putInt(MAX_IMAGE_NUM, mMaxImageNum);
                bundle.putSerializable(PickPhotosPreviewFragment.CHOOSE_DATA_LIST, mChoosedData);
                PickPhotosPreviewFragment.setImageListData(mChoosedData);
                Intent intent = getIntent(bundle);
                startActivityForResult(intent, REQUEST_FOR_PREVIEW);
            }
        });
        mImageGroupList.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mCurrentGroup = mGroupList.get(position);
                mGroupAdapter.setCurrentGroup(mCurrentGroup);
                mGroupAdapter.notifyDataSetChanged();
                mGridView.smoothScrollToPosition(0);
                mImageAdapter.notifyDataSetChanged();
                hideImageGroupList();
                updateAlbumName();
            }
        });
        Loader<Cursor> loader = getLoaderManager().initLoader(CURSOR_LOADER, null, this);
        loader.forceLoad();
    }

    private Intent getIntent(Bundle bundle) {
        Intent intent = new Intent(getActivity(), ActivityFragmentContainer.class);
        intent.putExtra(ActivityFragmentContainer.BUNDLE_CONTAINER, bundle);
        intent.putExtra(ActivityFragmentContainer.FRAGMENT_CLASS_NAME, PickPhotosPreviewFragment.class.getName());
        return intent;
    }


    @Override
    public void onResume() {
        super.onResume();
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        getLoaderManager().destroyLoader(CURSOR_LOADER);
    }

    private void updateCompleteBtn() {
        if (mChoosedData.size() > 0) {
            mComplete.setEnabled(true);
        } else {
            mComplete.setEnabled(false);
        }
        mComplete.setText(getString(R.string.complete_pick, mChoosedData.size(), mMaxImageNum));
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        L.info(PickPhotosFragment.this, "onActivityResult");
        if (requestCode == REQUEST_FOR_PREVIEW) {
            switch (resultCode) {
                case RESULT_BACK:
                    Bundle arg = data.getExtras();
                    if (arg.getSerializable(PickPhotosPreviewFragment.CHOOSE_DATA_LIST) != null) {
                        ArrayList<ImageBean> choosedData = (ArrayList<ImageBean>) arg.getSerializable(PickPhotosPreviewFragment.CHOOSE_DATA_LIST);
                        boolean isDataChanegd = false;
                        if (mChoosedData.size() != choosedData.size()) {
                            isDataChanegd = true;
                        } else {
                            for (int i = 0; i < mChoosedData.size(); i++) {
                                ImageBean bean = choosedData.get(i);
                                ImageBean oldbean = mChoosedData.get(i);
                                if (!bean.equals(oldbean)) {
                                    isDataChanegd = true;
                                    break;
                                }
                            }
                        }
                        if (isDataChanegd) {
                            mChoosedData = choosedData;
                            mImageAdapter.notifyDataSetChanged();
                        }
                        updatePreviewState();
                        updateCompleteBtn();
                    }
                    break;
                case RESULT_COMPLETE:
                    L.info(PickPhotosFragment.this, "RESULT_COMPLETE");
                    if (getActivity() == null)
                        return;
                    getActivity().setResult(PickPhotosFragment.RESULT_COMPLETE, data);
                    getActivity().finish();
                    break;
                default:
                    break;
            }
        } else if (requestCode == REQUEST_ADD_IMAGE) {
            if (resultCode == Activity.RESULT_CANCELED)
                return;
            Uri uri = null;
            if (mPhotoUri != null) {
                uri = mPhotoUri;
                mPhotoUri = null;
            } else if (data != null && data.getData() != null) {
                uri = data.getData();
            }
            if (uri == null) {
                SFToast.showToast(R.string.pic_error);
                return;
            }
            String imagepath = uri.getPath();
            Intent scanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
            scanIntent.setData(uri);
            getActivity().sendBroadcast(scanIntent);
            ImageBean bean = new ImageBean();
            bean.setPath(imagepath);
            mChoosedData.add(bean);
            Bundle bundle = new Bundle();
            bundle.putInt(PickPhotosPreviewFragment.INDEX, mChoosedData.size() - 1);
            bundle.putInt(MAX_IMAGE_NUM, mMaxImageNum);
            bundle.putSerializable(PickPhotosPreviewFragment.CHOOSE_DATA_LIST, mChoosedData);
            PickPhotosPreviewFragment.setImageListData(mChoosedData);
            Intent intent = getIntent(bundle);
            startActivityForResult(intent, REQUEST_FOR_PREVIEW);
        }
    }

    private void showImageGroupList() {
        mImageGroupLayout.setVisibility(View.VISIBLE);
        AlphaAnimation alphaAnim = new AlphaAnimation(0.0f, 1.0f);
        alphaAnim.setDuration(300);
        alphaAnim.setAnimationListener(new AnimationListener() {

            @Override
            public void onAnimationStart(Animation animation) {
                mImageGroupBackground.setVisibility(View.VISIBLE);
                mImageGroupShowState = 1;
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                mImageGroupBackground.clearAnimation();
                mImageGroupShowState = 2;
            }
        });
        int height = mImageGroupList.getHeight();
        TranslateAnimation transAnim = new TranslateAnimation(0, 0, height == 0 ? UnitHelp.dip2px(getActivity(), 300) : height, 0);
        transAnim.setDuration(300);
        transAnim.setAnimationListener(new AnimationListener() {

            @Override
            public void onAnimationStart(Animation animation) {
                mImageGroupList.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                mImageGroupList.clearAnimation();
            }
        });
        mImageGroupList.startAnimation(transAnim);
        mImageGroupBackground.startAnimation(alphaAnim);
    }

    private void hideImageGroupList() {
        AlphaAnimation alphaAnim = new AlphaAnimation(1.0f, 0.0f);
        alphaAnim.setDuration(300);
        alphaAnim.setAnimationListener(new AnimationListener() {

            @Override
            public void onAnimationStart(Animation animation) {
                mImageGroupShowState = 1;
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                mImageGroupBackground.setVisibility(View.GONE);
                mImageGroupLayout.setVisibility(View.GONE);
                mImageGroupBackground.clearAnimation();
                mImageGroupShowState = 0;
            }
        });
        TranslateAnimation transAnim = new TranslateAnimation(0, 0, 0, mImageGroupList.getHeight());
        transAnim.setDuration(300);
        transAnim.setAnimationListener(new AnimationListener() {

            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                mImageGroupLayout.setVisibility(View.GONE);
                mImageGroupList.setVisibility(View.GONE);
                mImageGroupList.clearAnimation();
            }
        });
        mImageGroupList.startAnimation(transAnim);
        mImageGroupBackground.startAnimation(alphaAnim);
    }


    /**
     *
     */
    private void updatePreviewState() {
        if (mChoosedData.size() > 0) {
            mPreview.setEnabled(true);
            mPreview.setTextColor(Color.parseColor("#424142"));
        } else {
            mPreview.setEnabled(false);
            mPreview.setTextColor(Color.parseColor("#7b7d81"));
        }
    }

    private int getPhotoSize() {
        if (mImageSize != 0) {
            return mImageSize;
        }
        if (mGridView == null) {
            return -1;
        }
        Context context = mGridView.getContext();
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) mGridView.getLayoutParams();
        int contentWidth = SystemUIWHHelp.getScreenRealWidth(getActivity()) - params.leftMargin - params.rightMargin - mGridView.getPaddingLeft()
                - mGridView.getPaddingRight();
        int numCol = 3;
        int hSpace = UnitHelp.dip2px(context, 8);
        mImageSize = (contentWidth - (numCol - 1) * hSpace) / numCol;
        return mImageSize;
    }

    private void updateAlbumName() {
        TextView text = (TextView) mAlbums.findViewById(R.id.albums);
        text.setText(mCurrentGroup.getDirName());
    }


    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        String order = MediaStore.Images.Media.DATE_ADDED + " desc";
        CursorLoader cursorLoader = new CursorLoader(getActivity(), MediaStore.Images.Media.EXTERNAL_CONTENT_URI, STORE_IMAGES, null, null,
                order);
        return cursorLoader;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        if (cursor == null) {
            return;
        }
        L.info(PickPhotosFragment.this, "Cursorloader get data");
        for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
            ImageBean bean = new ImageBean();
            bean.setPath(cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA)));
            File file = new File(bean.getPath());
            if (!file.exists())
                continue;
            mAllImageData.add(bean);
            String parentName = "";
            String parentPath = "";
            if (file.getParentFile() != null) {
                parentName = file.getParentFile().getName();
                parentPath = file.getParent();
            } else {
                parentName = file.getName();
                parentPath = file.getPath();
            }
            ImageGroup item = new ImageGroup();
            item.setDirName(parentName);
            item.setDirPath(parentPath);
            int searchIdx = mGroupList.indexOf(item);
            if (searchIdx >= 0) {
                ImageGroup imageGroup = mGroupList.get(searchIdx);
                imageGroup.addImage(bean);
            } else {
                item.addImage(bean);
                mGroupList.add(item);
            }
        }
        cursor.close();
        ImageGroup item = new ImageGroup();
        if (getActivity() != null) {
            L.info(PickPhotosFragment.this, "Cursorloader add all data");
            item.setDirName(getActivity().getString(R.string.all_pictures));
            item.setDirPath(ALL_PICTURE_PATH);
        }
        item.addImage(mAllImageData);
        mCurrentGroup = item;
        updateAlbumName();
        mGroupList.add(0, item);
        int maxHeight = SystemUIWHHelp.getScreenRealHeight(getActivity()) - UnitHelp.dip2px(getActivity(), 150);
        int realHeight = UnitHelp.dip2px(getActivity(), 100) * mGroupList.size();
        if (realHeight < maxHeight) {
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, realHeight);
            params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
            mImageGroupList.setLayoutParams(params);
        }
        mGroupAdapter.setCurrentGroup(mCurrentGroup);
        mImageAdapter.notifyDataSetChanged();
        mGroupAdapter.notifyDataSetChanged();
        if (mGroupList.size() > 1) {
            mAlbums.setEnabled(true);
        }
        getLoaderManager().destroyLoader(CURSOR_LOADER);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }

    private class GridAdapter extends BaseAdapter {

        private Context mContext;

        public GridAdapter(Context context) {
            mContext = context;
        }

        @Override
        public int getCount() {
            if (mCurrentGroup != null) {
                if (TextUtils.equals(ALL_PICTURE_PATH, mCurrentGroup.getDirPath())) {
                    return mCurrentGroup.getImageCount() + 1;
                }
                return mCurrentGroup.getImageCount();
            }
            return 0;
        }

        @Override
        public Object getItem(int position) {
            if (mCurrentGroup != null)
                return mCurrentGroup.getImages().get(position);
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public int getItemViewType(int position) {
            if (mCurrentGroup != null && TextUtils.equals(ALL_PICTURE_PATH, mCurrentGroup.getDirPath()) && position == 0) {
                return 1;
            }
            return 0;
        }

        @Override
        public int getViewTypeCount() {
            return 2;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder = null;
            if (getItemViewType(position) == 1) {
                if (convertView == null) {
                    convertView = LayoutInflater.from(getActivity()).inflate(R.layout.pick_photo_item_layout, null);
                    holder = new ViewHolder();
                    holder.image = (ImageView) convertView.findViewById(R.id.image);
                    holder.checkBox = (CheckBox) convertView.findViewById(R.id.check_box);
                    convertView.setTag(holder);
                } else {
                    holder = (ViewHolder) convertView.getTag();
                }
                holder.image.setLayoutParams(new RelativeLayout.LayoutParams(getPhotoSize(), getPhotoSize()));
                holder.image.setBackgroundResource(R.drawable.pickphotos_to_camera_bg);
//                holder.image.setOnClickListener(new View.OnClickListener() {
//
//                    @Override
//                    public void onClick(View v) {
//                        if (mChoosedData.size() >= mMaxImageNum) {
//                            SFToast.showToast(getString(R.string.four_pics_most, mMaxImageNum));
//                            return;
//                        }
//                        mPhotoUri = PhotoHelper.startCamera(PickPhotosFragment.this, REQUEST_ADD_IMAGE);
//                    }
//                });
                holder.checkBox.setVisibility(View.GONE);
                return convertView;
            }

            if (convertView == null) {
                convertView = LayoutInflater.from(getActivity()).inflate(R.layout.pick_photo_item_layout, null);
                holder = new ViewHolder();
                holder.image = (ImageView) convertView.findViewById(R.id.image);
                holder.checkBox = (CheckBox) convertView.findViewById(R.id.check_box);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            if (mCurrentGroup != null && TextUtils.equals(ALL_PICTURE_PATH, mCurrentGroup.getDirPath()))
                position = position - 1;
            final int pos = position;
            final ImageBean bean = mCurrentGroup.getImages().get(pos);
            String path = bean.getPath();
            if (mChoosedData.indexOf(bean) >= 0) {
                holder.checkBox.setChecked(true);
            } else {
                holder.checkBox.setChecked(false);
            }
            holder.checkBox.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    if (v instanceof CheckBox) {
                        CheckBox checkBox = (CheckBox) v;
                        if (checkBox.isChecked()) {
                            L.info(PickPhotosFragment.this, bean.getPath());
                            int index = mChoosedData.indexOf(bean);
                            if (index >= 0) {
                                return;
                            }
                            if (mChoosedData.size() >= mMaxImageNum) {
                                checkBox.setChecked(false);
                                SFToast.showToast(getString(R.string.four_pics_most, mMaxImageNum));
                                return;
                            }
                            File file = new File(bean.getPath());
                            if (!file.exists()) {
                                checkBox.setChecked(false);
                                SFToast.showToast(R.string.photo_donot_exist);
                                return;
                            }
                            mChoosedData.add(bean);
                            L.info(PickPhotosFragment.this, mChoosedData.indexOf(bean) + "");
                            checkBox.setChecked(true);
                        } else {
                            checkBox.setChecked(false);
                            mChoosedData.remove(bean);
                        }
                        updateCompleteBtn();
                        updatePreviewState();
                    }
                }

            });
            holder.image.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    Bundle bundle = new Bundle();
                    bundle.putInt(PickPhotosPreviewFragment.INDEX, pos);
                    bundle.putInt(MAX_IMAGE_NUM, mMaxImageNum);
                    bundle.putSerializable(PickPhotosPreviewFragment.CHOOSE_DATA_LIST, mChoosedData);
                    PickPhotosPreviewFragment.setImageListData(mCurrentGroup.getImages());
                    Intent intent = getIntent(bundle);
                    startActivityForResult(intent, REQUEST_FOR_PREVIEW);
                }
            });
            ImageLoader.getInstance().displayImage(SFFileHelp.pathToFilePath(path), holder.image);
            return convertView;
        }

    }

    private class ViewHolder {
        public ImageView image;

        public CheckBox checkBox;
    }

    public boolean onBackPressed() {
        if (mImageGroupShowState == 2) {
            hideImageGroupList();
            return true;
        }
        return false;
    }

}
