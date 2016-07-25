package com.basesmartframe.baseadapter.newmultiadapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by NetEase on 2016/7/25 0025.
 */
public abstract class BaseMultiChoiceAdapter extends BaseAdapter {
    private BaseAdapter mBaseAdapter;
    protected Map<Integer, Boolean> mChoiceState = new HashMap<>();
    private int mCheckedId;
    public BaseMultiChoiceAdapter(BaseAdapter baseAdapter,int checkId) {
        this.mBaseAdapter = baseAdapter;
        mCheckedId=checkId;
        init();
    }

    protected abstract void updateCheckViews(int position, View rootView,View checkedView, boolean state);

    private void init(){
        int count=mBaseAdapter.getCount();
        for(int i=0;i<count;i++){
            mChoiceState.put(i,false);
        }
    }
    public BaseAdapter getBaseAdapter(){
        return mBaseAdapter;
    }

    public int getCheckId(){
        return mCheckedId;
    }
    public List<Integer> getCheckedItemPositions() {
        Set<Map.Entry<Integer, Boolean>> choiceStates = mChoiceState.entrySet();
        List<Integer> positions = new ArrayList<>();
        for (Map.Entry<Integer, Boolean> choiceState : choiceStates) {
            Boolean value = choiceState.getValue();
            if (value) {
                positions.add(choiceState.getKey());
            }
        }
        return positions;
    }

    public int getCheckedCount() {
        List<Integer> positions = getCheckedItemPositions();
        return positions == null ? 0 : positions.size();
    }

    public void reset() {
       init();
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mBaseAdapter.getCount();
    }

    @Override
    public Object getItem(int position) {
        return mBaseAdapter.getItem(position);
    }

    @Override
    public long getItemId(int position) {
        return mBaseAdapter.getItemId(position);
    }


    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = mBaseAdapter.getView(position, convertView, parent);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Boolean choiceState = mChoiceState.get(position);
                mChoiceState.put(position, !choiceState);
                notifyDataSetChanged();
            }
        });
        Boolean choiceState = mChoiceState.get(position);
        View checkedView=view.findViewById(mCheckedId);
        updateCheckViews(position, view,checkedView, choiceState);
        return view;
    }

    @Override
    public int getViewTypeCount() {
        return mBaseAdapter.getViewTypeCount();
    }

    @Override
    public int getItemViewType(int position) {
        return mBaseAdapter.getItemViewType(position);
    }
}
