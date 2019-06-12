package com.basesmartframe.baseadapter.newmultiadapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

/**
 * Created by NetEase on 2016/7/25 0025.
 */
public abstract class BaseSingleChoicesAdapter extends BaseMultiChoiceAdapter {

    private int mCurCheckedPosition=-1;

    public BaseSingleChoicesAdapter(BaseAdapter baseAdapter, int checkId) {
        super(baseAdapter, checkId);
    }

    /**
     * 在setadapter之前调用这个函数才有效
     * @param checkedPosition
     */
    public void setDefaultCheckedPosition(int checkedPosition){
        this.mCurCheckedPosition=checkedPosition;
        mChoiceState.put(mCurCheckedPosition,true);
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view=getBaseAdapter().getView(position,convertView,parent);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               boolean state= mChoiceState.get(position);
                if(!state) {
                    mChoiceState.put(mCurCheckedPosition,false);
                    mChoiceState.put(position, !state);
                    notifyDataSetChanged();
                    mCurCheckedPosition=position;
                }
            }
        });
        int checkedId=getCheckId();
        View checkedView=view.findViewById(checkedId);
        Boolean state=mChoiceState.get(position);
        updateCheckViews(position,view,checkedView,state);
        return view;
    }
}
