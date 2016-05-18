package com.example.androidtv.presenter;

import android.app.Activity;
import android.support.v17.leanback.widget.Presenter;
import android.view.View;
import android.view.ViewGroup;

import com.basesmartframe.baseutil.UnitHelp;
import com.basesmartframe.log.L;
import com.example.androidtv.cardview.CategoryCardView;
import com.example.androidtv.module.bean.TVHomeDataModel;
import com.nostra13.universalimageloader.core.ImageLoader;

/**
 * Created by xieningtao on 15-9-11.
 */
public class WrapContentPresenter extends Presenter {
    public static class CategoryViewHold extends Presenter.ViewHolder {

        public CategoryViewHold(View itemView) {
            super(itemView);
        }
    }

    private final String TAG = getClass().getName();

    private int mItemWidth;
    private int mItemHeight;

    public WrapContentPresenter(Activity activity, int verticalExtraSpace, int horizontalExtraSpace) {
        int itemWidth = GridViewItemHelp.getItemWidth(activity, 6, horizontalExtraSpace);
        int itemHeight = GridViewItemHelp.getItemHeight(activity, 2, verticalExtraSpace);
        L.info(TAG, "itemWidth: " + itemWidth + " itemHeight: " + itemHeight);
        mItemHeight = itemHeight;
        mItemWidth = itemWidth;
    }

    @Override
    public ViewHolder onCreateViewHolder(final ViewGroup viewGroup) {
        final CategoryCardView customCardView = new CategoryCardView(viewGroup.getContext());
        customCardView.setFocusable(true);
        customCardView.setFocusableInTouchMode(true);
        resetCustomCardViewLayoutParams(customCardView.getRootView(), mItemWidth, mItemHeight);
        int padding = UnitHelp.dip2px(viewGroup.getContext(), 8);
        customCardView.getRootView().setPadding(padding, padding, padding, padding);
        return new CategoryViewHold(customCardView);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, Object o) {
        final TVHomeDataModel.TVCategoryBean categoryBean = (TVHomeDataModel.TVCategoryBean) o;
        final CategoryCardView customCardView = (CategoryCardView) viewHolder.view;
        customCardView.getContentTv().setText(categoryBean.getItemName());
        ImageLoader.getInstance().displayImage(categoryBean.getImgUrl(), customCardView.getImageIv());

        customCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


    }

    @Override
    public void onUnbindViewHolder(ViewHolder viewHolder) {
        CategoryCardView customCardView = (CategoryCardView) viewHolder.view;
        ImageLoader.getInstance().cancelDisplayTask(customCardView.getImageIv());
        customCardView.getImageIv().setImageBitmap(null);
        customCardView.getContentTv().setText(null);
    }

    private void resetCustomCardViewLayoutParams(ViewGroup customCardView, int width, int height) {
        ViewGroup.LayoutParams params = customCardView.getLayoutParams();
        params.width = width;
        params.height = height;
        customCardView.setLayoutParams(params);
    }

}
