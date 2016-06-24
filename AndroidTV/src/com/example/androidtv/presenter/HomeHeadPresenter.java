package com.example.androidtv.presenter;

import android.app.Activity;
import android.support.v17.leanback.widget.Presenter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.basesmartframe.baseutil.SystemUIWHHelp;
import com.sf.loglib.L;
import com.example.androidtv.R;
import com.example.androidtv.focus.FocusHelper;

/**
 * Created by xieningtao on 15-12-28.
 */
public class HomeHeadPresenter extends Presenter {
    public static class CategoryViewHold extends Presenter.ViewHolder {

        public CategoryViewHold(View itemView) {
            super(itemView);
        }
    }

    private final String TAG = getClass().getName();

    private int mItemWidth;
    private int mItemHeight;

    private Activity mActivity;

    public HomeHeadPresenter(Activity activity, int verticalExtraSpace, int horizontalExtraSpace) {
//        int itemWidth = GridViewItemHelp.getItemWidth(activity, 6, horizontalExtraSpace);
//        int itemHeight = GridViewItemHelp.getItemHeight(activity, 2, verticalExtraSpace);

        int itemWidth = SystemUIWHHelp.getScreenRealWidth(activity);
        int itemHeight = 300;
        L.info(TAG, "itemWidth: " + itemWidth + " itemHeight: " + itemHeight);
        mItemHeight = itemHeight;
        mItemWidth = itemWidth;
        this.mActivity = activity;
    }

    @Override
    public Presenter.ViewHolder onCreateViewHolder(final ViewGroup viewGroup) {
        View rootView = LayoutInflater.from(mActivity).inflate(R.layout.present_home_head, null);
        ViewGroup head_container = (ViewGroup) rootView.findViewById(R.id.tv_head_container);
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(mItemWidth / 2,
                ViewGroup.LayoutParams.MATCH_PARENT);
        head_container.setLayoutParams(params);

        View first_bt = rootView.findViewById(R.id.first_bt);
        View second_bt = rootView.findViewById(R.id.second_bt);
        View third_bt = rootView.findViewById(R.id.third_bt);

        FocusHelper.registerFocus(first_bt);
        FocusHelper.registerFocus(second_bt);
        FocusHelper.registerFocus(third_bt);

        return new CategoryViewHold(rootView);
    }

    @Override
    public void onBindViewHolder(Presenter.ViewHolder viewHolder, Object o) {


    }

    @Override
    public void onUnbindViewHolder(Presenter.ViewHolder viewHolder) {

    }

    private void resetCustomCardViewLayoutParams(ViewGroup customCardView, int width, int height) {
        ViewGroup.LayoutParams params = customCardView.getLayoutParams();
        params.width = width;
        params.height = height;
        customCardView.setLayoutParams(params);
    }

}
