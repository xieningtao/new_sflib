package com.sf.SFSample.nybao;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;

import com.basesmartframe.baseadapter.BaseAdapterHelper;
import com.basesmartframe.pickphoto.ImageBean;
import com.basesmartframe.pickphoto.PickPhotosPreviewFragment;
import com.maxleap.FindCallback;
import com.maxleap.MLObject;
import com.maxleap.MLQuery;
import com.maxleap.MLQueryManager;
import com.maxleap.exception.MLException;
import com.sf.SFSample.R;
import com.sf.SFSample.nybao.bean.NYPairPicBean;
import com.sf.SFSample.nybao.bean.NYPicBean;
import com.sf.SFSample.nybao.bean.NYPicCoverBean;
import com.sf.SFSample.nybao.bean.NYPicListBean;
import com.sf.SFSample.tiantu.ActivityPhotoPreview;
import com.sf.loglib.L;
import com.sf.utils.baseutil.GsonUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by NetEase on 2016/10/9 0009.
 */
public class NYFragmentPic extends NYBasePullListFragment<NYPairPicBean> {
    @Override
    protected boolean onRefresh() {
        MLQuery<MLObject> newsQuery = MLQuery.getQuery("NYPic");
        MLQueryManager.findAllInBackground(newsQuery, new FindCallback<MLObject>() {
            @Override
            public void done(List<MLObject> list, MLException e) {
                L.debug(TAG, "news: " + list);
                List<NYPicBean> nyPicBeanList = new ArrayList<>();
                if (list != null && !list.isEmpty()) {
                    for (MLObject mlObject : list) {
                        NYPicBean picBean = GsonUtil.parse(mlObject.getString("picContent"), NYPicBean.class);

                        nyPicBeanList.add(picBean);
                    }
                }
                finishRefreshOrLoading(single2Pair(nyPicBeanList), false);
            }
        });
        return true;
    }

    private List<NYPairPicBean> single2Pair(List<NYPicBean> picBeanList){
        List<NYPairPicBean> pairPicBeanList=new ArrayList<>();
        if(picBeanList==null||picBeanList.isEmpty()){
            return pairPicBeanList;
        }
        int index=0;
        int size=picBeanList.size();
        while (index<size){
            NYPairPicBean pairPicBean=new NYPairPicBean();
            NYPicBean leftPicBean=picBeanList.get(index);
            pairPicBean.setmLeftBean(leftPicBean);
            index++;
            if(index<size) {
                NYPicBean rightPicBean = picBeanList.get(index);
                pairPicBean.setmRightBean(rightPicBean);
                index++;
            }
            pairPicBeanList.add(pairPicBean);
        }
        return pairPicBeanList;
    }

    @Override
    protected boolean onLoadMore() {
        return false;
    }

    @Override
    protected int[] getLayoutIds() {
        return new int[]{R.layout.ny_topic_item};
    }

    @Override
    protected void bindView(BaseAdapterHelper help, int position, final NYPairPicBean bean) {
        final NYPicBean leftBean=bean.getmLeftBean();
        if(leftBean!=null) {
            NYPicCoverBean coverBean=leftBean.getPicCoverBean();
            help.setVisible(R.id.left_rl,View.VISIBLE);
            if(coverBean!=null) {
                help.setImageBuilder(R.id.pic_first_iv, coverBean.getThumberUrl());
                help.setText(R.id.pic_number_first_tv, coverBean.getNumber() + "");
                help.setText(R.id.pic_label_first_tv, coverBean.getLable());
                help.getView(R.id.left_rl).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getActivity(), ActivityPhotoPreview.class);
                        intent.putExtra(PickPhotosPreviewFragment.INDEX, 0);
                        intent.putStringArrayListExtra(ActivityPhotoPreview.IMAGE_MD5,getImageMd5Values(leftBean.getPicListBean()));
                        intent.putExtra(ActivityPhotoPreview.IMAGE_BEAN_LIST, tianTuImageList2ImageBean(leftBean.getPicListBean()));
                        startActivity(intent);
                    }
                });
            }else {
                help.setVisible(R.id.left_rl,View.INVISIBLE);
            }
        }else {
            help.setVisible(R.id.left_rl,View.INVISIBLE);
        }

        final NYPicBean rightBean=bean.getmRightBean();
        if(rightBean!=null) {
            help.setVisible(R.id.right_rl,View.VISIBLE);
            NYPicCoverBean coverBean=rightBean.getPicCoverBean();
            if(coverBean!=null) {
                help.setImageBuilder(R.id.pic_second_iv, coverBean.getThumberUrl());
                help.setText(R.id.pic_number_second_tv, coverBean.getNumber() + "");
                help.setText(R.id.pic_label_second_tv, coverBean.getLable());
                help.getView(R.id.right_rl).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getActivity(), ActivityPhotoPreview.class);
                        intent.putExtra(PickPhotosPreviewFragment.INDEX, 0);
                        intent.putStringArrayListExtra(ActivityPhotoPreview.IMAGE_MD5,getImageMd5Values(rightBean.getPicListBean()));
                        intent.putExtra(ActivityPhotoPreview.IMAGE_BEAN_LIST, tianTuImageList2ImageBean(rightBean.getPicListBean()));
                        startActivity(intent);
                    }
                });
            }else {
                help.setVisible(R.id.right_rl,View.INVISIBLE);
            }
        }else {
            help.setVisible(R.id.right_rl,View.INVISIBLE);
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


    }

    private ArrayList<String> getImageMd5Values(List<NYPicListBean> picListBeen){
        ArrayList<String> imageMd5Values=new ArrayList<>();
        if(picListBeen==null||picListBeen.isEmpty()){
            return imageMd5Values;
        }
        for (NYPicListBean imageBean : picListBeen) {
            imageMd5Values.add(imageBean.getImageUrlMd5());
        }
        return imageMd5Values;

    }
    private ArrayList<ImageBean> tianTuImageList2ImageBean(List<NYPicListBean> picListBeen) {
        ArrayList<ImageBean> imageBeanList = new ArrayList<>();
        if(picListBeen==null||picListBeen.isEmpty()){
            return imageBeanList;
        }
        for (NYPicListBean imageBean : picListBeen) {
            ImageBean newImageBean = new ImageBean();
            newImageBean.setPath(imageBean.getImageUrl());
            imageBeanList.add(newImageBean);
        }
        return imageBeanList;
    }
}
