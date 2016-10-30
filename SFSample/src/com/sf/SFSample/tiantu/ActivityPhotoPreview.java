package com.sf.SFSample.tiantu;

import android.app.Fragment;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.basesmartframe.baseui.BaseActivity;
import com.basesmartframe.basevideo.util.TimeUtil;
import com.basesmartframe.pickphoto.ImageBean;
import com.basesmartframe.pickphoto.PickPhotosPreviewFragment;
import com.google.gson.Gson;
import com.maxleap.FindCallback;
import com.maxleap.MLDataManager;
import com.maxleap.MLObject;
import com.maxleap.MLQuery;
import com.maxleap.MLQueryManager;
import com.maxleap.SaveCallback;
import com.maxleap.exception.MLException;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.sf.SFSample.R;
import com.sf.SFSample.nybao.bean.NYCommentBean;
import com.sf.SFSample.nybao.bean.NYNewsBean;
import com.sf.SFSample.ui.ActivityLivePopView;
import com.sf.loglib.L;
import com.sf.utils.baseutil.GsonUtil;
import com.sf.utils.baseutil.SFToast;
import com.sflib.CustomView.viewgroup.BaseLivePopAdapter;
import com.sflib.CustomView.viewgroup.LivePopView;
import com.sflib.reflection.core.ThreadHelp;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by NetEase on 2016/10/13 0013.
 */
public class ActivityPhotoPreview extends BaseActivity implements PickPhotosPreviewFragment.OnPicSelectedListener{
    public static final String IMAGE_BEAN_LIST = "image_bean_list";
    public static final String IMAGE_MD5="image_md5";
    private String imageUrl[] = {
            "http://g.hiphotos.baidu.com/image/w%3D310/sign=40484034b71c8701d6b6b4e7177e9e6e/21a4462309f79052f619b9ee08f3d7ca7acbd5d8.jpg",
            "http://a.hiphotos.baidu.com/image/w%3D310/sign=b0fccc9b8518367aad8979dc1e728b68/3c6d55fbb2fb43166d8f7bc823a4462308f7d3eb.jpg",
            "http://d.hiphotos.baidu.com/image/w%3D310/sign=af0348abeff81a4c2632eac8e72b6029/caef76094b36acaf8ded6c2378d98d1000e99ce4.jpg"
    };
    private String content[] = {
            "aajfsdkjfksjk",
            "bbjflksjfks",
            "ccjskfjsdklfj"
    };
    private LivePopView mLivePopView;
    private List<String> mImageMd5Vaules;
    private int mNumber=-1;
    private int mCurPageIndex=0;
    private String curImageMd5Value;

    private List<NYCommentBean> commentBeenList=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        init();
        setContentView(R.layout.activity_photo_preview);
        Intent intent = getIntent();
        if (intent != null) {
            int position = intent.getIntExtra(PickPhotosPreviewFragment.INDEX, 0);
            ArrayList<ImageBean> imageBeanArrayList = (ArrayList<ImageBean>) intent.getSerializableExtra(IMAGE_BEAN_LIST);
            mImageMd5Vaules=intent.getStringArrayListExtra(IMAGE_MD5);
            curImageMd5Value=mImageMd5Vaules.get(mCurPageIndex);
            PickPhotosPreviewFragment fragment = new PickPhotosPreviewFragment();
            Bundle bundle = new Bundle();
            bundle.putBoolean(PickPhotosPreviewFragment.CAN_CHOOSE_IMAGE, false);
            bundle.putInt(PickPhotosPreviewFragment.INDEX, position);
            fragment.setArguments(bundle);
            fragment.setOnPicSelectedListener(this);
            PickPhotosPreviewFragment.setImageListData(imageBeanArrayList);
            getFragmentManager().beginTransaction().replace(R.id.photo_preview_fl, fragment).commitAllowingStateLoss();
        }
        mLivePopView= (LivePopView) findViewById(R.id.live_popview);
        mLivePopView.setAdapter(new ImageCommentAdapter());


        initView();
        getComment(curImageMd5Value);

    }

    private void sendComment(String imageUrlMd5,String comment){
        MLObject myComment = new MLObject("NYPicComment");
        myComment.put("imageMd5Value", imageUrlMd5);
        NYCommentBean commentBean=new NYCommentBean();
        commentBean.setComment(comment);
        Random random=new Random();
        int index=random.nextInt(3)<0?0:random.nextInt(3);
        commentBean.setPhotoUrl(imageUrl[index]);
        Gson contentGson=new Gson();
        myComment.put("content",  contentGson.toJson(commentBean));
        MLDataManager.saveInBackground(myComment, new SaveCallback() {
            @Override
            public void done(MLException e) {
                if(e==null) {
                    SFToast.showToast("评论成功");
                }else {
                    SFToast.showToast("评论失败");
                }
            }
        });
    }

    private void getComment(String imageUrlMd5){
        MLQuery<MLObject> newsQuery = MLQuery.getQuery("NYPicComment").setLimit(30);
        newsQuery.whereEqualTo("imageMd5Value",imageUrlMd5);
        MLQueryManager.findAllInBackground(newsQuery, new FindCallback<MLObject>() {
            @Override
            public void done(List<MLObject> list, MLException e) {
                L.debug(TAG, "comments: " + list);
                 commentBeenList.clear();
                if (list != null && !list.isEmpty()) {
                    for (MLObject mlObject : list) {
                        NYCommentBean commentBean = GsonUtil.parse(mlObject.getString("content"), NYCommentBean.class);
                        commentBeenList.add(commentBean);
                    }
                    mLivePopView.clear();
                    mLivePopView.doPush();
                }
            }
        });

    }

    private void initView(){
        View sendView=findViewById(R.id.comment_send_tv);
        final EditText commentEt= (EditText) findViewById(R.id.comment_et);
        sendView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              if(!TextUtils.isEmpty(commentEt.getText())) {
                  String commentContent=commentEt.getText().toString();
                  sendComment(curImageMd5Value,commentContent);
              }
            }
        });
    }

    @Override
    public void onPicSelected(int index) {
        if(index!=mCurPageIndex){
            mCurPageIndex=index;
            curImageMd5Value=mImageMd5Vaules.get(index);
            getComment(curImageMd5Value);

        }
    }

    class ImageCommentAdapter extends BaseLivePopAdapter {

        @Override
        public View getView(View rootView,int position) {
            if (rootView == null) {
                rootView = LayoutInflater.from(ActivityPhotoPreview.this).inflate(R.layout.item_pop_view, null);
            }
            NYCommentBean commentBean=commentBeenList.get(position);
            ImageView mPhotoIv = (ImageView) rootView.findViewById(R.id.photo_iv);
            ImageLoader.getInstance().displayImage(commentBean.getPhotoUrl(), mPhotoIv);
            TextView contentTv = (TextView) rootView.findViewById(R.id.comment_tv);
            contentTv.setText(commentBean.getComment());
            return rootView;
        }

        @Override
        public int getCount() {
            return commentBeenList.size();
        }
    }

    private void init() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//透明状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }

    private void initWindow() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            showSystemUI();
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
            View decorView = getWindow().getDecorView();
            int uiOptions = View.SYSTEM_UI_FLAG_LOW_PROFILE;
            decorView.setSystemUiVisibility(uiOptions);
        }
    }

    // This snippet hides the system bars.
    private void hideSystemUI() {
        // Set the IMMERSIVE flag.
        // Set the content to appear under the system bars so that the content
        // doesn't resize when the system bars hide and show.
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION // hide nav bar
                        | View.SYSTEM_UI_FLAG_FULLSCREEN // hide status bar
                        | View.SYSTEM_UI_FLAG_IMMERSIVE);
    }

    // This snippet shows the system bars. It does this by removing all the flags
// except for the ones that make the content appear under the system bars.
    private void showSystemUI() {
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            View decorView = getWindow().getDecorView();
            decorView.setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);}
    }
}
