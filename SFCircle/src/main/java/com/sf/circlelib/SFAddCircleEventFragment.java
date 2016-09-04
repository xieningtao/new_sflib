package com.sf.circlelib;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;

import com.basesmartframe.baseui.BaseFragment;
import com.basesmartframe.pickphoto.ActivityFragmentContainer;
import com.basesmartframe.pickphoto.ImageBean;
import com.basesmartframe.pickphoto.PickPhotosFragment;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.sf.utils.baseutil.SFFileCreationUtil;
import com.sf.utils.baseutil.SFFileHelp;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xieningtao on 16/9/4.
 */
public class SFAddCircleEventFragment extends BaseFragment {

    private EditText mContentEt;
    private RecyclerView mPhotoRlv;
    private final int CUSTOM_ALBUM_COMMAND_RESULT = 1;
    private List<ImageBean> mImageBeanList = new ArrayList<>();
    private PicAdapter mPicAdapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.sf_circle_add_event, null);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init(view);
    }

    private void init(View view) {
        mContentEt = (EditText) view.findViewById(R.id.content_et);
        mPhotoRlv = (RecyclerView) view.findViewById(R.id.pic_rlv);
        mPhotoRlv.setLayoutManager(new GridLayoutManager(getActivity(), 4));
        mPicAdapter=new PicAdapter(getActivity());
        mPhotoRlv.setAdapter(mPicAdapter);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CUSTOM_ALBUM_COMMAND_RESULT) {
            if (data != null) {
                List<ImageBean> imageBeenList = (List<ImageBean>) data.getSerializableExtra(PickPhotosFragment.CHOOSE_PIC);
                if(imageBeenList!=null&&!imageBeenList.isEmpty()){
                    imageBeenList.clear();
                    mImageBeanList.addAll(imageBeenList);
                    mPicAdapter.notifyDataSetChanged();
                }
            }
        }
    }

    private class PicAdapter extends RecyclerView.Adapter<PicViewHolder> {
        private LayoutInflater mLayoutInflater;

        public PicAdapter(Context context) {
            mLayoutInflater = LayoutInflater.from(context);
        }


        @Override
        public PicViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View convertView = mLayoutInflater.inflate(R.layout.sf_circle_image_item, null);
            return new PicViewHolder(convertView);
        }

        @Override
        public void onBindViewHolder(PicViewHolder holder, int position) {
                if(getItemViewType(position)==1){
                    holder.mPicIv.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Bundle bundle = new Bundle();
                            bundle.putInt(PickPhotosFragment.MAX_IMAGE_NUM, 8);
                            Intent intent = new Intent(getActivity(), ActivityFragmentContainer.class);
                            intent.putExtra(ActivityFragmentContainer.BUNDLE_CONTAINER, bundle);
                            intent.putExtra(ActivityFragmentContainer.FRAGMENT_CLASS_NAME, PickPhotosFragment.class.getName());
                            SFAddCircleEventFragment.this.startActivityForResult(intent, CUSTOM_ALBUM_COMMAND_RESULT);
                        }
                    });
                    holder.mPicIv.setImageResource(R.drawable.ic_launcher);
                }else {
                    ImageBean imageBean = mImageBeanList.get(position - 1);
                    String localPath = SFFileHelp.pathToFilePath(imageBean.getPath());
                    ImageLoader.getInstance().displayImage(localPath, holder.mPicIv);
                }
        }



        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public int getItemViewType(int position) {
            if(position==0){
                return 1;
            }else {
                return 0;
            }
        }

        @Override
        public int getItemCount() {
            return mImageBeanList.size()+1;
        }

    }

    private class PicViewHolder extends RecyclerView.ViewHolder{
        public final ImageView mPicIv;
        public PicViewHolder(View itemView) {
            super(itemView);
            mPicIv = (ImageView) itemView.findViewById(R.id.photo_iv);
        }
    }

}
