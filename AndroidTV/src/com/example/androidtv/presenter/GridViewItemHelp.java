package com.example.androidtv.presenter;

import android.app.Activity;
import android.content.res.Resources;
import android.text.TextUtils;

import com.basesmartframe.baseutil.SystemUIWHHelp;
import com.example.androidtv.R;

/**
 * Created by xieningtao on 15-9-14.
 */
public class GridViewItemHelp {
    public static int getItemWidth(Activity activity, int columNum, int horizontalExtraSpace) {
        Resources resources = activity.getResources();
        int screenWidth = SystemUIWHHelp.getScreenRealWidth(activity);
        int horizontal_item_side_space = resources.getDimensionPixelSize(R.dimen.tv_grid_item_start) + resources.getDimensionPixelSize(R.dimen.tv_grid_item_end);
        int gridviewWidth = screenWidth - horizontalExtraSpace - horizontal_item_side_space;
        if (columNum <= 1) return gridviewWidth;
        int horizontal_item_margin = resources.getDimensionPixelOffset(R.dimen.tv_grid_item_horizontal_margin);
        int gridItemWidth = (gridviewWidth - horizontal_item_margin * (columNum - 1)) / columNum;
        gridItemWidth = gridItemWidth - getExtraScaleSpace(activity);
        return gridItemWidth;
    }

    public static int getItemHeight(Activity activity, int rowNum, int verticalExtraSpace) {
        Resources resources = activity.getResources();
        int screenRealHeight = SystemUIWHHelp.getScreenRealHeight(activity);
        int vertical_item_side_space = resources.getDimensionPixelSize(R.dimen.tv_grid_item_bottom) + resources.getDimensionPixelSize(R.dimen.tv_grid_item_top);

        int gridviewHeight = screenRealHeight - vertical_item_side_space - verticalExtraSpace;
        if (rowNum <= 1) return gridviewHeight;
        int vertical_item_margin = resources.getDimensionPixelOffset(R.dimen.tv_grid_item_vertical_margin);
        int gridItemHeight = (gridviewHeight - vertical_item_margin * (rowNum - 1)) / rowNum;
        gridItemHeight = gridItemHeight - getExtraScaleSpace(activity);
        return gridItemHeight;
    }

    private static int getExtraScaleSpace(Activity activity) {
        Resources resources = activity.getResources();
//        return resources.getDimensionPixelSize(R.dimen.tv_grid_item_scale_extra_space);
        return 0;
    }

    public static String rebuildImageUrl(final String url, int size) {
        String imageUrl = url;
        if (!TextUtils.isEmpty(imageUrl)) {
            int lastIndex = imageUrl.lastIndexOf("/");
            String subImageUrl = imageUrl.substring(0, lastIndex + 1);
            imageUrl = subImageUrl + size;
        }
        return imageUrl;
    }
}
