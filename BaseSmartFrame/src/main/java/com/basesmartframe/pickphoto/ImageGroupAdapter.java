/**
 * @(#)ImageGroupAdapter.java, 2015年1月15日. 
 * 
 * Copyright 2015 netease, Inc. All rights reserved.
 * Netease PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.basesmartframe.pickphoto;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.basesmartframe.R;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.sf.loglib.file.SFFileHelp;

import java.util.ArrayList;

/**
 *
 * @author xltu
 *
 */
public class ImageGroupAdapter extends BaseAdapter {
    
    private Context mContext;
    
    private ArrayList<ImageGroup> mData;
    

    private ImageGroup mCurrentGroup;

    public ImageGroupAdapter(Context context,ArrayList<ImageGroup> data){
        mContext = context;
        mData = data;
    }
    
    public void setCurrentGroup(ImageGroup group){
        this.mCurrentGroup = group;
    }
    
    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Object getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if(convertView == null){
            holder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(mContext);
            convertView = inflater.inflate(R.layout.image_group_item, null);
            holder.albumCover = (ImageView) convertView.findViewById(R.id.album_cover);
            holder.albumName = (TextView) convertView.findViewById(R.id.album_name);
            holder.albumSize = (TextView) convertView.findViewById(R.id.album_size);
            holder.choosedIcon = (ImageView) convertView.findViewById(R.id.choosed_icon);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }
        ImageGroup item = mData.get(position);
        ImageLoader.getInstance().displayImage(SFFileHelp.pathToFilePath(item.getFirstImgPath().getPath()),holder.albumCover);
        holder.albumName.setText(item.getDirName());
        holder.albumSize.setText(mContext.getString(R.string.count_of_pictures,item.getImageCount()));
        if(mCurrentGroup !=null && mCurrentGroup.equals(item)){
            holder.choosedIcon.setVisibility(View.VISIBLE);
        }else{
            holder.choosedIcon.setVisibility(View.INVISIBLE);
        }
        return convertView;
    }
    
    public class ViewHolder{
        public TextView albumName;
        
        public TextView albumSize;
        
        public ImageView albumCover;
        
        public ImageView choosedIcon;
    }

}
