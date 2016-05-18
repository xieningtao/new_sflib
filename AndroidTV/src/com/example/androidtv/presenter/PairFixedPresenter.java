package com.example.androidtv.presenter;

import android.app.Activity;
import android.support.v17.leanback.widget.Presenter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.basesmartframe.baseutil.UnitHelp;
import com.basesmartframe.log.L;
import com.example.androidtv.cardview.CategoryCardView;
import com.example.androidtv.focus.FocusHelper;
import com.example.androidtv.module.bean.TVHomeDataModel;
import com.nostra13.universalimageloader.core.ImageLoader;

/**
 * Created by xieningtao on 15-12-28.
 */
public class PairFixedPresenter extends Presenter {
    public static class CategoryViewHold extends Presenter.ViewHolder {

        public CategoryViewHold(View itemView) {
            super(itemView);
        }
    }

    private final String TAG = getClass().getName();

    private int mItemWidth;
    private int mItemHeight;

    public PairFixedPresenter(Activity activity, int verticalExtraSpace, int horizontalExtraSpace) {
//        int itemWidth = GridViewItemHelp.getItemWidth(activity, 6, horizontalExtraSpace);
//        int itemHeight = GridViewItemHelp.getItemHeight(activity, 2, verticalExtraSpace);

        int itemWidth = 500;
        int itemHeight = 300;
        L.info(TAG, "itemWidth: " + itemWidth + " itemHeight: " + itemHeight);
        mItemHeight = itemHeight;
        mItemWidth = itemWidth;
    }

    @Override
    public ViewHolder onCreateViewHolder(final ViewGroup viewGroup) {
        final LinearLayout linearLayout = new LinearLayout(viewGroup.getContext());
        linearLayout.setOrientation(LinearLayout.VERTICAL);

        final CategoryCardView item1 = new CategoryCardView(viewGroup.getContext());
        item1.setFocusable(true);
        item1.setFocusableInTouchMode(true);
        resetCustomCardViewLayoutParams(item1.getRootView(), mItemWidth, mItemHeight);
        int padding = UnitHelp.dip2px(viewGroup.getContext(), 8);
        item1.getRootView().setPadding(padding, padding, padding, padding);

        final CategoryCardView item2 = new CategoryCardView(viewGroup.getContext());
        item2.setFocusable(true);
        item2.setFocusableInTouchMode(true);
        resetCustomCardViewLayoutParams(item2.getRootView(), mItemWidth, mItemHeight);
        item2.getRootView().setPadding(padding, padding, padding, padding);

        linearLayout.addView(item1);
        linearLayout.addView(item2);

        FocusHelper.registerFocus(item1);
        FocusHelper.registerFocus(item2);

        return new CategoryViewHold(linearLayout);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, Object o) {
        final TVHomeDataModel.TVCategoryBean categoryBean = (TVHomeDataModel.TVCategoryBean) o;
        final LinearLayout linearLayout = (LinearLayout) viewHolder.view;
        int count = linearLayout.getChildCount();
        for (int i = 0; i < count; i++) {
            final CategoryCardView customCardView = (CategoryCardView) linearLayout.getChildAt(i);
            customCardView.getContentTv().setText(categoryBean.getItemName());
            ImageLoader.getInstance().displayImage(categoryBean.getImgUrl(), customCardView.getImageIv());

            customCardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
        }


    }

    @Override
    public void onUnbindViewHolder(ViewHolder viewHolder) {
        final LinearLayout linearLayout = (LinearLayout) viewHolder.view;
        int count = linearLayout.getChildCount();
        for (int i = 0; i < count; i++) {
            final CategoryCardView customCardView = (CategoryCardView) linearLayout.getChildAt(i);
            ImageLoader.getInstance().cancelDisplayTask(customCardView.getImageIv());
            customCardView.getImageIv().setImageBitmap(null);
            customCardView.getContentTv().setText(null);
        }
    }

    private void resetCustomCardViewLayoutParams(ViewGroup customCardView, int width, int height) {
        ViewGroup.LayoutParams params = customCardView.getLayoutParams();
        params.width = width;
        params.height = height;
        customCardView.setLayoutParams(params);
    }
}

