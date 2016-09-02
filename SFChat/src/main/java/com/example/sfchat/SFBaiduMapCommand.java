package com.example.sfchat;

import android.app.Fragment;
import android.content.Intent;

import com.basesmartframe.pickphoto.ActivityFragmentContainer;

import com.sf.baidulib.SFBaiduMapLocationFragment;

/**
 * Created by NetEase on 2016/9/2 0002.
 */
public class SFBaiduMapCommand implements SFBaseChatCommand {
    private Fragment mFragment;
    private int mActivityResultCode = SFBaseChatCommand.LOCATION_REQUEST;

    public SFBaiduMapCommand(Fragment activity, int resultCode) {
        this.mFragment = activity;
        this.mActivityResultCode = resultCode;
    }
    public SFBaiduMapCommand(Fragment activity) {
        this.mFragment = activity;
    }


    @Override
    public void executeCommand() {
        Intent intent = new Intent(mFragment.getActivity(), ActivityFragmentContainer.class);
        intent.putExtra(ActivityFragmentContainer.FRAGMENT_CLASS_NAME, SFBaiduMapLocationFragment.class.getName());
        mFragment.startActivityForResult(intent, mActivityResultCode);
    }
}
