package com.sf.SFSample.popwindow;

import android.app.Activity;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.PopupWindow;

import com.sf.utils.baseutil.SpUtil;
import com.basesmartframe.dialoglib.pop.BaseCornerPopWindow;
import com.sf.loglib.L;
import com.sf.SFSample.R;

/**
 * Created by NetEase on 2016/6/13 0013.
 */
public class MessageSettingPopWindow extends BaseCornerPopWindow implements View.OnClickListener{
    private View mRealPhoto_ll, mHigherCredit_ll, mFriendsCondition_ll;
    private CheckBox mRealPhoto_cb, mHigherCredit_cb, mFriendsCondition_cb;

    private final String MESSAGE_SETTING_SP = "message_setting_sp";
    private final String REAL_PHOTO_SP = "real_photo_sp";
    private final String HIGHER_CREDIT_SP = "higher_credit_sp";
    private final String FRIENDS_CONDITION_SP = "friends_condition_sp";

    private Activity mActivity;
    public MessageSettingPopWindow(Activity activity) {
        super(activity);
        mActivity=activity;
    }

    public void initViews(View view) {
        view.findViewById(R.id.view_container_ll).setOnClickListener(this);
        mRealPhoto_ll = view.findViewById(R.id.real_photo_ll);
        mHigherCredit_ll = view.findViewById(R.id.higher_credit_ll);
        mFriendsCondition_ll = view.findViewById(R.id.friends_condition_ll);

        mRealPhoto_cb = view.findViewById(R.id.real_photo_cb);
        mHigherCredit_cb = view.findViewById(R.id.higher_credit_cb);
        mFriendsCondition_cb = view.findViewById(R.id.friends_condition_cb);

        mRealPhoto_ll.setOnClickListener(this);
        mHigherCredit_ll.setOnClickListener(this);
        mFriendsCondition_ll.setOnClickListener(this);

        resetValue();
    }

    private void resetValue() {
        if (mActivity != null) {
            SharedPreferences preferences = SpUtil.getSharedPreferences(mActivity, MESSAGE_SETTING_SP);
            boolean realPhoto = preferences.getBoolean(REAL_PHOTO_SP, false);
            boolean higherCredit = preferences.getBoolean(HIGHER_CREDIT_SP, false);
            boolean friendsCondition = preferences.getBoolean(FRIENDS_CONDITION_SP, false);
            mRealPhoto_cb.setChecked(realPhoto);
            mHigherCredit_cb.setChecked(higherCredit);
            mFriendsCondition_cb.setChecked(friendsCondition);
            L.debug(TAG, "method->resetValue,realPhoto: " + realPhoto + " higherCredit: " + higherCredit + " friendsCondition: " + friendsCondition);
        }
    }

    private void toggleUI(CheckBox checkBox, String key) {
        if (mActivity != null) {
            SharedPreferences preferences = SpUtil.getSharedPreferences(mActivity, MESSAGE_SETTING_SP);
            boolean originValue = preferences.getBoolean(key, false);
            boolean curValue = !originValue;
            checkBox.setChecked(curValue);
            preferences.edit().putBoolean(key, curValue).commit();
            L.debug(TAG, "method->toggleUI,key: " + key + " originValue: " + originValue + " curValue: " + curValue);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.real_photo_ll:
                toggleUI(mRealPhoto_cb, REAL_PHOTO_SP);
                break;
            case R.id.higher_credit_ll:
                toggleUI(mHigherCredit_cb, HIGHER_CREDIT_SP);
                break;
            case R.id.friends_condition_ll:
                toggleUI(mFriendsCondition_cb, FRIENDS_CONDITION_SP);
                break;
            case R.id.view_container_ll:
                if(isShowing()) {
                    hideView();
                }
                break;
            default:
        }
    }

    @Override
    protected PopupWindow onCreatePopWindow(LayoutInflater layoutInflater) {
        View view=layoutInflater.inflate(R.layout.fragment_message_setting,null);
        initViews(view);
        return new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
    }
}
