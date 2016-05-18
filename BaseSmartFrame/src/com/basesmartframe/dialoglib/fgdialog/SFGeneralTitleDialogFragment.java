package com.basesmartframe.dialoglib.fgdialog;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import com.basesmartframe.R;

public class SFGeneralTitleDialogFragment extends BaseTitleDialogFragment {


    @Override
    View onTitleViewContainerCreated(LayoutInflater inflater, FrameLayout titleContainer) {
        View rootView = inflater.inflate(R.layout.three_items_title, null);
        return rootView;
    }

    @Override
    View onContentViewContainerCreated(LayoutInflater inflater, FrameLayout contentContainer) {
        return null;
    }
}
