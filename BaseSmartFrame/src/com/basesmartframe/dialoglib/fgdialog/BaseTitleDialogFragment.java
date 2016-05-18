package com.basesmartframe.dialoglib.fgdialog;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.basesmartframe.R;
import com.basesmartframe.baseui.BaseFragment;

public abstract class BaseTitleDialogFragment extends BaseFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_dialog_fragment, null);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        FrameLayout title_fl = (FrameLayout) view
                .findViewById(R.id.title_container);
        FrameLayout content_fl = (FrameLayout) view.findViewById(R.id.content_container);
        LayoutInflater inflater = LayoutInflater.from(getActivity());
        View title_view = onTitleViewContainerCreated(inflater, title_fl);
        if (title_view != null && title_view.getParent() == null) {
            title_fl.addView(title_fl);
        }
        View content_view = onContentViewContainerCreated(inflater, content_fl);
        if (content_view != null && content_view.getParent() == null) {
            content_fl.addView(content_view);
        }
    }

    /**
     * titleContainer.getId() if you want to add fragment into this FrameLayout
     *
     * @param inflater
     * @param titleContainer
     */
    abstract View onTitleViewContainerCreated(LayoutInflater inflater, FrameLayout titleContainer);

    /**
     * contentContainer.getId() if you want to add fragment into this FrameLayout
     *
     * @param inflater
     * @param contentContainer
     */
    abstract View onContentViewContainerCreated(LayoutInflater inflater, FrameLayout contentContainer);

}
