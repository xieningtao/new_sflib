package com.sf.circlelib;

import android.graphics.Color;
import android.view.View;
import android.widget.AdapterView;

import com.basesmartframe.baseadapter.BaseAdapterHelper;
import com.basesmartframe.basecircle.BaseCircleFragment;
import com.sf.circlelib.abscircle.ISFComment;
import com.sf.circlelib.abscircle.ISFContent;
import com.sf.circlelib.abscircle.ISFImage;

import java.util.List;

/**
 * Created by xieningtao on 16/9/4.
 */
abstract public class SFCircleFragment<T extends ISFContent, N extends ISFImage, C extends ISFComment> extends BaseCircleFragment<T, N, C> {

    @Override
    protected void bindNineGridView(BaseAdapterHelper help, int GroupPosition, int childPosition, N bean) {
        String url = bean.getImageUrl();
        help.setImageBuilder(R.id.photo_iv, url);
    }

    @Override
    protected void bindCommentListView(BaseAdapterHelper help, int GroupPosition, int childPosition, C bean) {
        String leftContent = SpannableFactory.spannableTextColor(bean.getFromName(), Color.BLUE)
                + "回复" + SpannableFactory.spannableTextColor(bean.getToName(), Color.BLUE);
        help.setText(R.id.name_tv, leftContent);
        help.setText(R.id.content_tv, bean.getComment());
    }

    @Override
    protected void bindMainView(BaseAdapterHelper help, int GroupPosition, T bean) {
        help.setImageBuilder(R.id.photo_iv, bean.getPhotoUrl());
        help.setText(R.id.circle_title, bean.getContent());
    }

    @Override
    protected int getNineGridViewCount(int groupPos, T bean) {
        List<ISFImage> imageList = bean.getImages();
        return imageList == null ? 0 : imageList.size();
    }

    @Override
    protected int getCommentListViewCount(int groupPos, T bean) {
        List<ISFComment> commentList = bean.getComments();
        return commentList == null ? 0 : commentList.size();
    }

    @Override
    protected int[] getMainViewLayoutIds() {
        return new int[]{
                R.layout.sf_circle_main_item
        };
    }

    @Override
    protected int[] getNineGridViewLayoutIds() {
        return new int[]{
                R.layout.sf_circle_image_item
        };
    }

    @Override
    protected int[] getCommentListviewLayoutIds() {
        return new int[]{
                R.layout.sf_circle_comment_item
        };
    }

    @Override
    protected int getNineGrideViewId() {
        return R.id.nine_gv;
    }

    @Override
    protected int getCommentListViewId() {
        return R.id.comment_lv;
    }


}
